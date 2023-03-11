
package info.freelibrary.maven; // NOPMD

import static info.freelibrary.util.Constants.EOL;
import static info.freelibrary.util.Constants.HASH;
import static info.freelibrary.util.Constants.PERIOD;
import static info.freelibrary.util.Constants.SLASH;
import static info.freelibrary.util.Constants.SPACE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.EnumConstantSource;
import org.jboss.forge.roaster.model.source.JavaDocSource;
import org.jboss.forge.roaster.model.source.JavaEnumSource;
import org.jboss.forge.roaster.model.source.MethodSource;

import info.freelibrary.util.FileUtils;
import info.freelibrary.util.I18nRuntimeException;
import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.warnings.Checkstyle;
import info.freelibrary.util.warnings.PMD;

/**
 * A Maven mojo that generates an enum of pre-configured mime-types, adding any addition ones (with extensions) found in
 * the system's <code>/etc/mime.types</code> file.
 */
@SuppressWarnings({ "PMD.ExcessiveImports", PMD.EXCESSIVE_IMPORTS, "MultipleStringLiterals",
    Checkstyle.MULTIPLE_STRING_LITERALS, "PMD.AvoidDuplicateLiterals", PMD.AVOID_DUPLICATE_LITERALS,
    "PMD.ConsecutiveLiteralAppends", PMD.CONSECUTIVE_LITERAL_APPENDS, "PMD.GodClass", PMD.GOD_CLASS })
@Mojo(name = MojoNames.GENERATE_MEDIATYPE, defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class MediaTypeMojo extends AbstractMojo {

    /**
     * A static value for the enumeration's class name.
     */
    private static final String CLASS_NAME = "MediaType";

    /**
     * The mojo's logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaTypeMojo.class, MessageCodes.BUNDLE);

    /**
     * A constant for a quotation mark, used in the construction of strings.
     */
    private static final String QUOTE = "\"";

    /**
     * The default size of StringBuilders used in the mojo.
     */
    private static final int BUILDER_SIZE = 350;

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * A configuration option for the generated sources directory.
     */
    @Parameter(alias = Config.GEN_SRC, property = Config.GEN_SRC,
            defaultValue = "${project.basedir}/src/main/generated")
    protected File myGenSrcDir;

    /**
     * A configuration option for the to be created mime-types package.
     */
    @Parameter(alias = Config.PACKAGE, property = Config.PACKAGE, defaultValue = "${project.groupId}")
    protected String myPackagePath;

    /**
     * The method that runs the MimeTypesMojo.
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final File srcDir = new File(myGenSrcDir, myPackagePath.replace(PERIOD, SLASH));
        final List<MediaTypeEntry> mediaTypes = readDefaultMediaTypes();

        // Create generated sources directory if it doesn't already exist
        if (!myGenSrcDir.exists() && !myGenSrcDir.mkdirs()) {
            throw new MojoExecutionException(LOGGER.getMessage(MessageCodes.MVN_116, myGenSrcDir));
        }

        // Create a directory into which to write the generated java source file
        if (!srcDir.exists() && !srcDir.mkdirs()) {
            throw new MojoExecutionException(LOGGER.getMessage(MessageCodes.MVN_118, srcDir));
        }

        readUserMediaTypes(Paths.get("/etc/mime.types"), mediaTypes);
        readUserMediaTypes(Paths.get(System.getProperty("user.home"), ".mime.types"), mediaTypes);

        writeSource(mediaTypes, srcDir);
    }

    /**
     * Read the default (mojo supplied) media types.
     *
     * @return A list of media type entries
     * @throws MojoExecutionException If there is trouble reading the default media types
     */
    private List<MediaTypeEntry> readDefaultMediaTypes() throws MojoExecutionException {
        try (InputStream resourceStream = getClass().getResourceAsStream("/mime.types")) {
            if (resourceStream != null) {
                return getMediaTypes(resourceStream);
            }

            // If mime.types file can't be found in jar, see if we're running from a Maven project
            try (InputStream fileStream = Files.newInputStream(Paths.get("src/main/resources/mime.types"))) {
                if (fileStream == null) {
                    throw new I18nRuntimeException(MessageCodes.BUNDLE, MessageCodes.MVN_120);
                }

                return getMediaTypes(fileStream);
            }
        } catch (final IOException details) {
            throw new MojoExecutionException(details.getMessage(), details);
        }
    }

    /**
     * Read media types from a file system file.
     *
     * @param aFilePath A file path from which to read some media type definitions
     * @param aMediaTypeList A list of media types
     * @throws MojoExecutionException If there is trouble reading media types from the file
     */
    private void readUserMediaTypes(final Path aFilePath, final List<MediaTypeEntry> aMediaTypeList)
            throws MojoExecutionException {
        try (InputStream inStream = Files.newInputStream(aFilePath)) {
            if (inStream != null) {
                getMediaTypes(inStream, aMediaTypeList);
            }
        } catch (final NoSuchFileException details) {
            // We can ignore this... it's okay if it's not there
            LOGGER.trace(details.getMessage(), details);
        } catch (final IOException details) {
            throw new MojoExecutionException(details.getMessage(), details);
        }
    }

    /**
     * Writes the Java source to a file.
     *
     * @param aMediaTypeList A list of media types
     * @param aSrcDir A source direction
     * @throws MojoExecutionException If there is trouble writing the Java source file
     */
    private void writeSource(final List<MediaTypeEntry> aMediaTypeList, final File aSrcDir)
            throws MojoExecutionException {
        final JavaEnumSource source = Roaster.create(JavaEnumSource.class);

        // Set the package and class name
        source.setPackage(myPackagePath).setName(CLASS_NAME);

        // Add enumerations
        aMediaTypeList.forEach(entry -> {
            final EnumConstantSource enumConstant = source.addEnumConstant();
            final String[] exts = entry.getExts();
            final String pattern = StringUtils.repeat("\"{}\", ", exts.length);
            final String array = StringUtils.format(pattern.substring(0, pattern.length() - 2), exts);
            final String arrayArg = StringUtils.format("new String[] { {} }", array);

            enumConstant.getJavaDoc().setText("Media-type for " + entry.getType());
            enumConstant.setName(entry.getName()).setConstructorArguments(QUOTE + entry.getType() + QUOTE, arrayArg);
        });

        // Add the simple methods that just get MediaType field values
        source.addMethod("public String toString() { return myType; }");

        // Add the more complicated methods that have to do some more work
        addGetExtMethod(source);
        addGetExtsMethod(source);
        addFromStringMethod(source);
        addFromExtMethod(source);
        addFromExtMethodWithHint(source);
        addGetTypesMethod(source);
        addParseStringMethod(source);
        addParseStringMethodWithHint(source);
        addParseUriMethod(source);
        addParseUriMethodWithHint(source);

        // Create our new message codes class in the requested package directory
        try (BufferedWriter javaWriter = Files.newBufferedWriter(Paths.get(aSrcDir.getPath(), CLASS_NAME + ".java"))) {

            // Let's tell Checkstyle to ignore the generated code (if it's so configured)
            source.getJavaDoc().setFullText(LOGGER.getMessage(MessageCodes.MVN_119));

            // Name our Java file and put it in a Java package
            source.setPackage(myPackagePath).setName(CLASS_NAME);

            // Define the enum fields
            source.addField("private String myType;").getJavaDoc().setText("Sets the media type's identifier.");
            source.addField("private String[] myExts;").getJavaDoc().setText("Sets the media type's extensions.");

            // Create a constructor
            addConstructor(source).setParameters(getConstructorParams()).setBody(getConstructorBody());

            // Lastly, write our generated Java class out to the file system
            javaWriter.write(source.toString());
        } catch (final IOException details) {
            throw new MojoExecutionException(details.getMessage(), details);
        }
    }

    /**
     * Adds a parse method to the supplied Java source.
     *
     * @param aSource A Java source object
     */
    private void addParseUriMethodWithHint(final JavaEnumSource aSource) {
        final String methodTemplate = "public static Optional<MediaType> parse(final URI aURI, final String aHint) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append('{') //
                .append("final String fragment = '").append(HASH).append("' + aURI.getFragment();") //
                .append("final String ext; final int index;") //
                .append("String uri = aURI.toString();") //
                .append("if ((index = uri.indexOf(fragment)) != -1) { uri = uri.substring(0, index); }") //
                .append("ext = StringUtils.trimToNull(FileUtils.getExt(uri));") //
                .append("if (ext != null) {") //
                .append("return fromExt(ext, aHint);") //
                .append("} return fromString(uri); }");

        // Add necessary imports
        if (!aSource.hasImport(StringUtils.class)) {
            aSource.addImport(StringUtils.class);
        }

        if (!aSource.hasImport(FileUtils.class)) {
            aSource.addImport(FileUtils.class);
        }

        if (!aSource.hasImport(URI.class)) {
            aSource.addImport(URI.class);
        }

        // Add the fromString method to the source
        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aURI A URI from which to parse the media type");
        javadoc.addTagValue("@param", "aHint A hint as to what class of media type we want");
        javadoc.addTagValue("@return", "The media type that corresponds to the supplied URI");
        javadoc.setText("Gets a media type from the supplied URI's extension." + EOL + "*");
    }

    /**
     * Adds an additional parse method to the supplied Java source.
     *
     * @param aSource A Java source object
     */
    private void addParseUriMethod(final JavaEnumSource aSource) {
        final String methodTemplate = "public static Optional<MediaType> parse(final URI aURI) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append("{ return parse(aURI, null); }");

        // Add the URI class to imports if it hasn't already been added
        if (!aSource.hasImport(URI.class)) {
            aSource.addImport(URI.class);
        }

        // Add the fromString method to the source
        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aURI A URI from which to parse the media type");
        javadoc.addTagValue("@return", "The media type that corresponds to the supplied URI");
        javadoc.setText("Gets a media type from the supplied URI's extension." + EOL + "*");
    }

    /**
     * Adds an additional parse method to the supplied Java source.
     *
     * @param aSource A Java source object
     */
    private void addParseStringMethod(final JavaEnumSource aSource) {
        final String methodTemplate = "public static Optional<MediaType> parse(final String aURI) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append("{ return parse(URI.create(aURI), null); }");

        // Add the URI class to imports if it hasn't already been added
        if (!aSource.hasImport(URI.class)) {
            aSource.addImport(URI.class);
        }

        // Add the fromString method to the source
        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aURI A string URI from which to parse the media type");
        javadoc.addTagValue("@return", "The media type that corresponds to the supplied URI");
        javadoc.setText("Gets a media type from the supplied URI's extension." + EOL + "*");
    }

    /**
     * Adds an additional parse method to the supplied Java source.
     *
     * @param aSource A Java source object
     */
    private void addParseStringMethodWithHint(final JavaEnumSource aSource) {
        final String methodTemplate =
                "public static Optional<MediaType> parse(final String aURI, final String aHint) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append("{ return parse(URI.create(aURI), aHint); }");

        // Add the URI class to imports if it hasn't already been added
        if (!aSource.hasImport(URI.class)) {
            aSource.addImport(URI.class);
        }

        // Add the fromString method to the source
        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aURI A string URI from which to parse the media type");
        javadoc.addTagValue("@param", "aHint A class of type (e.g. 'audio' or 'application')");
        javadoc.addTagValue("@return", "The media type that corresponds to the supplied URI");
        javadoc.setText("Gets a media type from the supplied URI's extension." + EOL + "*");
    }

    /**
     * Adds a fromString method to the supplied Java source.
     *
     * @param aSource A Java source object
     */
    private void addFromStringMethod(final JavaEnumSource aSource) {
        final String methodTemplate = "public static Optional<MediaType> fromString(final String aType) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append('{') //
                .append("for (final MediaType mediaType : values()) {") //
                .append("if (mediaType.myType.equalsIgnoreCase(aType)) {") //
                .append("return Optional.of(mediaType);") //
                .append("}} return Optional.empty(); }");

        // Add an import for Optional since this method returns an Optional
        if (!aSource.hasImport(Optional.class)) {
            aSource.addImport(Optional.class);
        }

        // Add the fromString method to the source
        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aType A type of media type");
        javadoc.addTagValue("@return", "The media type that corresponds to the supplied type");
        javadoc.setText("Gets a media type from the supplied type." + EOL + "*");
    }

    /**
     * Adds a getTypes method that returns media types for a supplied class of type (e.g., "application").
     *
     * @param aSource A Java source object
     */
    private void addGetTypesMethod(final JavaEnumSource aSource) {
        final String methodTemplate = "public static List<MediaType> getTypes(final String aClass) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append('{') //
                .append("final List<MediaType> types = new ArrayList<>();") //
                .append("for (final MediaType mediaType : values()) {") //
                .append("if (mediaType.myType.startsWith(aClass.toLowerCase() + \"/\")) {") //
                .append("types.add(mediaType);") //
                .append("}} return types; }");

        // Add imports for the classes used in this method
        if (!aSource.hasImport(List.class)) {
            aSource.addImport(List.class);
        }

        if (!aSource.hasImport(ArrayList.class)) { // NOPMD - ArrayList has to be used to get its import added
            aSource.addImport(ArrayList.class); // NOPMD
        }

        // Add Javadocs for this method
        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aClass A class of media type (e.g., &quot;application&quot;)");
        javadoc.addTagValue("@return", "The media types that correspond to the supplied type class");
        javadoc.setText("Gets a list of media types that correspond to the supplied class." + EOL + "*");
    }

    /**
     * Adds a fromExt method to the supplied Java source.
     *
     * @param aSource A Java source object
     */
    private void addFromExtMethod(final JavaEnumSource aSource) {
        final String methodTemplate = "public static Optional<MediaType> fromExt(final String aExt) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append("{ return fromExt(aExt, null); }");

        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aExt The extension of the desired media type");
        javadoc.addTagValue("@return", "The media type that corresponds to the supplied extension");
        javadoc.setText("Gets a media type from the supplied extension." + EOL + "*");
    }

    /**
     * Adds the MediaType's <code>getExt()</code> method.
     *
     * @param aSource A reference to the generated Java source code
     */
    private void addGetExtMethod(final JavaEnumSource aSource) {
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        javadoc = aSource.addMethod("public String getExt() { return myExts[0]; }").getJavaDoc();
        javadoc.addTagValue("@return", "The first relevant media-type extension");
        javadoc.setText("Gets the first relevant media-type extension.");
    }

    /**
     * Adds the MediaType's <code>getExts()</code> method.
     *
     * @param aSource A reference to the generated Java source code
     */
    private void addGetExtsMethod(final JavaEnumSource aSource) {
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        javadoc = aSource.addMethod("public String[] getExts() { return myExts; }").getJavaDoc();
        javadoc.addTagValue("@return", "An array of relevant media-type extensions");
        javadoc.setText("Gets an array of relevant media-type extensions.");
    }

    /**
     * Adds a fromExt method to the supplied Java source.
     *
     * @param aSource A Java source object
     */
    private void addFromExtMethodWithHint(final JavaEnumSource aSource) {
        final String methodTemplate =
                "public static Optional<MediaType> fromExt(final String aExt, final String aHint) {}";
        final StringBuilder impl = new StringBuilder(BUILDER_SIZE);
        final JavaDocSource<MethodSource<JavaEnumSource>> javadoc;

        impl.append('{') //
                .append("final String hint = aHint != null ? aHint.toLowerCase() : null;") //
                .append("MediaType chosenMediaType = null;") //
                .append("for (final MediaType mediaType : values()) {") //
                .append(" for (final String ext : mediaType.getExts()) {") //
                .append("  if (ext.equalsIgnoreCase(aExt)) {") //
                .append("   if (hint != null && mediaType.toString().startsWith(hint)) { ") //
                .append("     return Optional.of(mediaType); }") //
                .append("   if (chosenMediaType == null) { chosenMediaType = mediaType; }") //
                .append("}}} return Optional.ofNullable(chosenMediaType); }");

        if (!aSource.hasImport(Optional.class)) {
            aSource.addImport(Optional.class);
        }

        javadoc = aSource.addMethod(StringUtils.format(methodTemplate, impl.toString())).getJavaDoc();
        javadoc.addTagValue("@param", "aExt The extension of the desired media type");
        javadoc.addTagValue("@param", "aHint A class of type (e.g. 'audio' or 'application')");
        javadoc.addTagValue("@return", "The media type that corresponds to the supplied extension");
        javadoc.setText("Gets a media type from the supplied extension." + EOL + "*");
    }

    /**
     * Adds a constructor to the enum class.
     *
     * @param aSource The source on which to add the constructor
     * @return The constructor's method
     */
    private MethodSource<JavaEnumSource> addConstructor(final JavaEnumSource aSource) {
        final MethodSource<JavaEnumSource> constructor = aSource.addMethod().setPackagePrivate().setConstructor(true);

        constructor.getJavaDoc().setText("Creates a new media type.");
        return constructor;
    }

    /**
     * Gets the constructor's parameters.
     *
     * @return The constructor's parameters
     */
    private String getConstructorParams() {
        return "final String aType, final String[] aExts";
    }

    /**
     * Gets the constructor's body.
     *
     * @return The body of the constructor
     */
    private String getConstructorBody() {
        return new StringBuilder().append("myType = aType;").append("myExts = aExts;").toString();
    }

    /**
     * Gets the media types from the supplied stream.
     *
     * @param aInStream An input stream for the media types
     * @return An array of media types to be used as values in the enum
     */
    private List<MediaTypeEntry> getMediaTypes(final InputStream aInStream) {
        return getMediaTypes(aInStream, null);
    }

    /**
     * Gets the media types from the supplied stream and puts them in the supplied list.
     *
     * @param aInStream An input stream for the media types
     * @param aEntryList A list of media type entries
     * @return An array of media types to be used as values in the enum
     */
    private List<MediaTypeEntry> getMediaTypes(final InputStream aInStream, final List<MediaTypeEntry> aEntryList) {
        final LineNumberReader reader = new LineNumberReader(new InputStreamReader(aInStream));
        final List<MediaTypeEntry> entries = aEntryList == null ? new ArrayList<>() : aEntryList;

        reader.lines().map(String::trim).forEach(line -> {
            // We only care about the media types that have extensions and skip those that are commented out
            if (line.contains(SPACE) && line.charAt(0) != '#') {
                final String[] parts = line.split("\\s+");
                final String[] exts = Arrays.copyOfRange(parts, 1, parts.length);
                final MediaTypeEntry entry = new MediaTypeEntry(parts[0], exts);

                if (!entries.contains(entry)) {
                    entries.add(entry);
                }
            } // ignore everything else...
        });

        return entries;
    }

    /**
     * A media type entry that should be converted into an enum value.
     */
    class MediaTypeEntry {

        /**
         * My media type name.
         */
        private final String myType;

        /**
         * The extensions for my media type.
         */
        private final String[] myExts;

        /**
         * Creates a new media type.
         *
         * @param aType A media type name
         * @param aExtArray An array of acceptable extensions, with the preferred one first
         */
        MediaTypeEntry(final String aType, final String... aExtArray) {
            myExts = aExtArray.clone();
            myType = aType;
        }

        /**
         * Gets the media type for this entry.
         *
         * @return The media type
         */
        String getType() {
            return myType;
        }

        /**
         * Gets the enum name of the media type.
         *
         * @return The name of the media type
         */
        String getName() {
            return myType.replaceAll("[\\/\\.\\-]+", "_").toUpperCase(Locale.US).replaceAll("\\+", "_PLUS_");
        }

        /**
         * Gets the known extensions for this media type.
         *
         * @return The known extensions for this media type
         */
        String[] getExts() {
            return myExts.clone();
        }

        @Override
        public boolean equals(final Object aObject) {
            return aObject instanceof MediaTypeEntry && ((MediaTypeEntry) aObject).myType.equalsIgnoreCase(myType);
        }

        @Override
        public int hashCode() {
            return myType.hashCode();
        }
    }

    /**
     * The Mojo's configuration options.
     */
    final class Config {

        /**
         * A property value for the package path.
         */
        static final String PACKAGE = "mediaTypePackage";

        /**
         * A property value for the generated sources directory.
         */
        static final String GEN_SRC = "generatedSourcesDirectory";

        /**
         * A private constructor for a constants class.
         */
        private Config() {
            // This is intentionally left empty.
        }
    }
}
