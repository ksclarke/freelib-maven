
package info.freelibrary.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.StringUtils;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

import info.freelibrary.util.FileUtils;
import info.freelibrary.util.IOUtils;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * I18nCodesMojo is a Maven mojo that can generate a <code>MessageCodes</code> class from which I18N message codes can
 * be referenced. The codes are then used to retrieve textual messages from resource bundles. The benefit of this is
 * the code can be generic, but the actual text from the pre-configured message file will be displayed in the IDE.
 * <p>
 * To manually run the plugin: `mvn info.freelibrary:freelib-utils:0.8.0:generate-codes
 * -DmessageFiles=src/main/resources/freelib-utils_messages.xml` (supplying whatever version and message file is
 * appropriate). Usually, though, the plugin would just be configured to run with the process-sources Maven lifecycle.
 * </p>
 */
@Mojo(name = "generate-codes", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class I18nCodesMojo extends AbstractMojo {

    private static final String MESSAGE_CLASS_NAME = "message-class-name";

    private static final Logger LOGGER = LoggerFactory.getLogger(I18nCodesMojo.class, Constants.BUNDLE_NAME);

    private static final File RESOURCES_DIR = new File("src/main/resources");

    private static final RegexFileFilter DEFAULT_MESSAGE_FILTER = new RegexFileFilter(".*_messages.xml");

    private static final String BUNDLE_DELIM = "_";

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    @Parameter(alias = "messageFiles", property = "messageFiles")
    private List<String> myPropertyFiles;

    @Parameter(alias = "generatedSourcesDirectory", property = "generatedSourcesDirectory",
            defaultValue = "${project.basedir}/src/main/generated")
    private File myGeneratedSrcDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (myPropertyFiles != null && !myPropertyFiles.isEmpty()) {
            generateMessageCodes(myPropertyFiles);
        } else {
            try {
                final File[] files = FileUtils.listFiles(RESOURCES_DIR, DEFAULT_MESSAGE_FILTER);

                if (files.length > 0) {
                    final List<String> filePathList = new ArrayList<>(files.length);

                    for (final File file : files) {
                        LOGGER.debug("Using: {}", file.getAbsolutePath());
                        filePathList.add(file.getAbsolutePath());
                    }

                    generateMessageCodes(filePathList);
                } else {
                    LOGGER.warn(MessageCodes.MVN_001);
                }
            } catch (final FileNotFoundException details) {
                LOGGER.warn(MessageCodes.MVN_001);
            }
        }
    }

    private void generateMessageCodes(final List<String> aFilesList) throws MojoExecutionException {
        final Iterator<?> iterator = aFilesList.iterator();
        final Properties properties = new Properties();

        while (iterator.hasNext()) {
            FileInputStream inStream = null;

            try {
                final String fileName = (String) iterator.next();

                inStream = new FileInputStream(fileName);
                properties.loadFromXML(inStream);

                final String fullClassName = properties.getProperty(MESSAGE_CLASS_NAME);
                final String srcFolderName = myGeneratedSrcDir == null ? myProject.getBuild().getSourceDirectory()
                        : myGeneratedSrcDir.getAbsolutePath();

                if (fullClassName != null) {
                    final String[] nameParts = fullClassName.split("\\.");
                    final int classNameIndex = nameParts.length - 1;
                    final String className = nameParts[classNameIndex];
                    final String[] packageParts = Arrays.copyOfRange(nameParts, 0, classNameIndex);
                    final String packageName = StringUtils.join(packageParts, ".");
                    final JavaClassSource source = Roaster.create(JavaClassSource.class);
                    final File packageDir = new File(srcFolderName + File.separatorChar + packageName.replace('.',
                            File.separatorChar));

                    source.setFinal(true).setPublic();

                    // Make sure the package directory already exists
                    if (!packageDir.exists() && !packageDir.mkdirs()) {
                        final String message = LOGGER.getMessage(MessageCodes.MVN_003, packageDir, className);
                        throw new MojoExecutionException(message);
                    }

                    final Iterator<String> messageIterator = properties.stringPropertyNames().iterator();

                    // Cycle through all the entries in the supplied messages file, creating fields
                    while (messageIterator.hasNext()) {
                        final String key = messageIterator.next();

                        // Create a field that contains the name of the bundle file
                        if (key.equals(MESSAGE_CLASS_NAME)) {
                            final FieldSource<JavaClassSource> field = source.addField();
                            final String bundleName = FileUtils.stripExt(new File(fileName).getName());

                            field.setName("BUNDLE").setStringInitializer(bundleName);
                            field.setType(String.class.getSimpleName()).setPublic().setStatic(true).setFinal(true);
                            field.getJavaDoc().setFullText("Message bundle name.");
                        }

                        // Create a field in our new message codes class for the message
                        if (!key.equals(MESSAGE_CLASS_NAME)) {
                            final String normalizedKey = key.replaceAll("[\\.-]", BUNDLE_DELIM);
                            final String value = properties.getProperty(key);
                            final FieldSource<JavaClassSource> field = source.addField();

                            field.setName(normalizedKey).setStringInitializer(key);
                            field.setType(String.class.getSimpleName()).setPublic().setStatic(true).setFinal(true);
                            field.getJavaDoc().setFullText("Message: " + value);
                        }
                    }

                    // Add private constructor
                    source.addMethod().setPrivate().setConstructor(true).setBody("super();");

                    // Create our new message codes class in the requested package directory
                    final File javaFile = new File(packageDir, className + ".java");
                    final FileWriter javaWriter = new FileWriter(javaFile);

                    // Let's tell Checkstyle to ignore the generated code (if it's so configured)
                    source.getJavaDoc().setFullText(LOGGER.getMessage(MessageCodes.MVN_008));

                    // Name our Java file and add a constructor
                    source.setPackage(packageName).setName(className);

                    // Lastly, write our generated Java class out to the file system
                    javaWriter.write(source.toString());
                    javaWriter.close();
                } else {
                    LOGGER.warn(MessageCodes.MVN_002, MESSAGE_CLASS_NAME);
                }
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
                IOUtils.closeQuietly(inStream);
            }
        }
    }
}
