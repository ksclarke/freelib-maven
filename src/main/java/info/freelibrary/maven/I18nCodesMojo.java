
package info.freelibrary.maven; // NOPMD - ExcessiveImports

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

/**
 * I18nCodesMojo is a Maven mojo that can generate a <code>MessageCodes</code> class from which I18N message codes can
 * be referenced. The codes are then used to retrieve textual messages from resource bundles. The benefit of this is the
 * code can be generic, but the actual text from the pre-configured message file will be displayed in the IDE.
 */
@Mojo(name = "generate-codes", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class I18nCodesMojo extends AbstractMojo {

    /**
     * Constant for the transcoding needed property.
     */
    static final String IS_TRANSCODING_NEEDED = "createPropertiesFile";

    /**
     * Constant for the generated sources directory property.
     */
    static final String GEN_SRC_DIR = "generatedSourcesDirectory";

    /**
     * Constant for the message files property.
     */
    static final String MESSAGE_FILES = "messageFiles";

    /**
     * The name of the message class.
     */
    private static final String MESSAGE_CLASS_NAME = "message-class-name";

    /**
     * The logger for I18nCodesMojo.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nCodesMojo.class, MessageCodes.BUNDLE);

    /**
     * The resources directory where the message file should be found.
     */
    private static final File RESOURCES_DIR = new File("src/main/resources");

    /**
     * A regex pattern to find the expected message file.
     */
    private static final RegexFileFilter DEFAULT_MESSAGE_FILTER = new RegexFileFilter(".*_messages.xml");

    /**
     * A delimiter to use in the bundle name.
     */
    private static final String BUNDLE_DELIM = "_";

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * A configuration option for the message file(s).
     */
    @Parameter(alias = MESSAGE_FILES, property = MESSAGE_FILES)
    protected List<String> myPropertyFiles;

    /**
     * A configuration option for the generated sources directory.
     */
    @Parameter(alias = GEN_SRC_DIR, property = GEN_SRC_DIR, defaultValue = "${project.basedir}/src/main/generated")
    protected File myGeneratedSrcDir;

    /**
     * A configuration option for generating a standard properties file in addition to the codes class.
     */
    @Parameter(alias = IS_TRANSCODING_NEEDED, property = IS_TRANSCODING_NEEDED, defaultValue = "false")
    protected boolean isTranscodingNeeded;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (myPropertyFiles != null && !myPropertyFiles.isEmpty()) {
            generateMessageCodes(myPropertyFiles);

            if (isTranscodingNeeded) {
                writePropertiesFiles(myPropertyFiles.toArray(new String[] {}));
            }
        } else {
            try {
                final List<String> filePathList = new ArrayList<>();

                for (final File file : FileUtils.listFiles(RESOURCES_DIR, DEFAULT_MESSAGE_FILTER)) {
                    LOGGER.debug(MessageCodes.MVN_123, file.getAbsolutePath());
                    filePathList.add(file.getAbsolutePath());

                    generateMessageCodes(filePathList);

                    if (isTranscodingNeeded) {
                        LOGGER.debug(MessageCodes.MVN_124, file);
                        writePropertiesFiles(file.getAbsolutePath());
                    }
                }
            } catch (final FileNotFoundException details) {
                LOGGER.warn(MessageCodes.MVN_001);
            }
        }
    }

    /**
     * Writes corresponding properties files from the supplied XML files.
     *
     * @param aFilesList A list of XML resource files
     * @throws MojoExecutionException If there is trouble reading or writing the resource files
     */
    private void writePropertiesFiles(final String... aFilesList) throws MojoExecutionException {
        for (final String xmlFilePath : aFilesList) {
            final Path fileName = Path.of(xmlFilePath.replace(".xml", ".properties")).getFileName();
            final Path filePath = Path.of("target/classes", fileName.toString());
            final Properties properties = new Properties();

            LOGGER.warn(MessageCodes.MVN_125, xmlFilePath, filePath);

            try (InputStream xmlFileStream = Files.newInputStream(Path.of(xmlFilePath));
                    BufferedWriter fileWriter = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
                properties.loadFromXML(xmlFileStream);
                properties.store(fileWriter, MessageCodes.MVN_126);
            } catch (final IOException details) {
                throw new MojoExecutionException(details.getMessage(), details);
            }
        }
    }

    /**
     * Generates the message codes class.
     *
     * @param aFilesList A list of message files
     * @throws MojoExecutionException If the mojo failed to run successfully
     */
    @SuppressWarnings({ "PMD.AvoidFileStream", "PMD.CyclomaticComplexity", "PMD.CognitiveComplexity" })
    private void generateMessageCodes(final List<String> aFilesList) throws MojoExecutionException {
        final Iterator<?> iterator = aFilesList.iterator();
        final Properties properties = new Properties();

        while (iterator.hasNext()) {
            final String fileName = (String) iterator.next();

            try (FileInputStream inStream = new FileInputStream(fileName)) {
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
                    final File packageDir =
                            new File(srcFolderName + File.separatorChar + packageName.replace('.', File.separatorChar));

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
                        if (MESSAGE_CLASS_NAME.equals(key)) {
                            final FieldSource<JavaClassSource> field = source.addField();
                            final String bundleName = FileUtils.stripExt(new File(fileName).getName());

                            field.setName("BUNDLE").setStringInitializer(bundleName);
                            field.setType(String.class.getSimpleName()).setPublic().setStatic(true).setFinal(true);
                            field.getJavaDoc().setFullText("Message bundle name.");
                        }

                        // Create a field in our new message codes class for the message
                        if (!MESSAGE_CLASS_NAME.equals(key)) {
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
                    try (FileWriter javaWriter = new FileWriter(new File(packageDir, className + ".java"))) {
                        // Let's tell Checkstyle to ignore the generated code (if it's so configured)
                        source.getJavaDoc().setFullText(LOGGER.getMessage(MessageCodes.MVN_008));

                        // Name our Java file and add a constructor
                        source.setPackage(packageName).setName(className);

                        // Lastly, write our generated Java class out to the file system
                        javaWriter.write(source.toString());
                    }
                } else {
                    LOGGER.warn(MessageCodes.MVN_002, MESSAGE_CLASS_NAME);
                }
            } catch (final IOException details) {
                LOGGER.error(details.getMessage(), details);
            }
        }
    }
}
