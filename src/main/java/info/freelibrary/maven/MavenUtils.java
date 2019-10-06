
package info.freelibrary.maven;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import info.freelibrary.util.MessageCodes;

/**
 * Maven related utilities.
 */
public final class MavenUtils {

    public static final int ERROR_LOG_LEVEL = LocationAwareLogger.ERROR_INT;

    public static final int WARN_LOG_LEVEL = LocationAwareLogger.WARN_INT;

    public static final int INFO_LOG_LEVEL = LocationAwareLogger.INFO_INT;

    public static final int DEBUG_LOG_LEVEL = LocationAwareLogger.DEBUG_INT;

    public static final int TRACE_LOG_LEVEL = LocationAwareLogger.TRACE_INT;

    private static final Logger LOGGER = LoggerFactory.getLogger(MavenUtils.class);

    private MavenUtils() {
    }

    /**
     * Set the log level of the supplied loggers to the supplied log level (defined as static ints in the
     * <code>MavenUtils</code> class).
     *
     * @param aLogLevel A log level to set in the supplied loggers
     * @param aLoggerList A list of names of loggers that need their levels adjusted
     */
    public static void setLogLevels(final int aLogLevel, final String... aLoggerList) {
        setLogLevels(aLogLevel, aLoggerList, null, null);
    }

    /**
     * Sets the logging level of the supplied loggers, optionally excluding some and including others.
     *
     * @param aLogLevel A log level to set in the supplied loggers
     * @param aLoggerList A list of names of loggers to have their levels reset
     * @param aExcludesList A list of names of loggers to exclude from the reset
     * @param aIncludesList A list of names of additional loggers to include in the reset
     */
    public static void setLogLevels(final int aLogLevel, final String[] aLoggerList, final String[] aExcludesList,
            final String... aIncludesList) {
        final ArrayList<String> loggerList = new ArrayList<>(Arrays.asList(aLoggerList));
        final Class<? extends Logger> simpleLogger = LoggerFactory.getLogger("org.slf4j.impl.SimpleLogger")
                .getClass();

        if (aIncludesList != null) {
            loggerList.addAll(Arrays.asList(aIncludesList));
        }

        for (final String loggerName : loggerList) {
            if (aExcludesList != null) {
                boolean skip = false;

                for (final String element : aExcludesList) {
                    if (loggerName.equals(element)) {
                        skip = true;
                        break;
                    }
                }

                if (skip) {
                    continue;
                }
            }

            final Logger loggerObject = LoggerFactory.getLogger(loggerName);
            final Class<? extends Logger> loggerClass = loggerObject.getClass();

            if (simpleLogger.equals(loggerClass)) {
                try {
                    final Field field = loggerClass.getDeclaredField("currentLogLevel");

                    field.setAccessible(true);
                    field.setInt(loggerObject, aLogLevel);

                    if (loggerObject.isDebugEnabled()) {
                        LOGGER.debug(MessageCodes.MVN_012, loggerName, getLevelName(aLogLevel));
                    }
                } catch (NoSuchFieldException | IllegalAccessException details) {
                    LOGGER.error(MessageCodes.MVN_011, details);
                }
            } else {
                LOGGER.warn(MessageCodes.MVN_010, loggerName);
            }
        }
    }

    /**
     * Gets a list of names of loggers that Maven uses in a typical build.
     *
     * @return A list of names of loggers used by a standard Maven build
     */
    public static String[] getMavenLoggers() {
        return new String[] { "org.apache.maven.cli.event.ExecutionEventLogger",
            "org.apache.maven.tools.plugin.scanner.DefaultMojoScanner",
            "org.apache.maven.plugin.plugin.DescriptorGeneratorMojo",
            "org.apache.maven.plugin.dependency.fromConfiguration.UnpackMojo",
            "org.apache.maven.shared.filtering.DefaultMavenResourcesFiltering",
            "org.apache.maven.plugin.checkstyle.CheckstyleViolationCheckMojo",
            "org.apache.maven.plugin.clean.CleanMojo",
            "org.apache.maven.tools.plugin.annotations.JavaAnnotationsMojoDescriptorExtractor",
            "org.apache.maven.plugin.failsafe.VerifyMojo", "org.apache.maven.DefaultMaven",
            "org.apache.maven.plugin.compiler.TestCompilerMojo", "org.apache.maven.plugins.enforcer.EnforceMojo",
            "org.apache.maven.plugin.compiler.CompilerMojo", "org.codehaus.plexus.archiver.jar.JarArchiver",
            "org.apache.maven.plugin.surefire.SurefirePlugin",
            "org.apache.maven.plugin.failsafe.IntegrationTestMojo" };
    }

    /**
     * Returns a name for the supplied integer value of a log level.
     *
     * @param aLogLevel The int value of a log level
     * @return The human-friendly name value of a log level
     */
    public static String getLevelName(final int aLogLevel) {
        switch (aLogLevel) {
            case ERROR_LOG_LEVEL:
                return "ERROR";
            case WARN_LOG_LEVEL:
                return "WARN";
            case INFO_LOG_LEVEL:
                return "INFO";
            case DEBUG_LOG_LEVEL:
                return "DEBUG";
            case TRACE_LOG_LEVEL:
                return "TRACE";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Returns the int of the supplied log level or zero if the supplied name doesn't match a known log level.
     *
     * @param aLogLevelName The name (error, warn, info, debug, or trace) of a logging level
     * @return The int code of the supplied level or zero if the supplied name doesn't correspond to a known level
     */
    public static int getLevelIntCode(final String aLogLevelName) {
        final String levelName = aLogLevelName.trim().toLowerCase(Locale.US);

        if ("error".equals(levelName)) {
            return ERROR_LOG_LEVEL;
        } else if ("warn".equals(levelName)) {
            return WARN_LOG_LEVEL;
        } else if ("info".equals(levelName)) {
            return INFO_LOG_LEVEL;
        } else if ("debug".equals(levelName)) {
            return DEBUG_LOG_LEVEL;
        } else if ("trace".equals(levelName)) {
            return TRACE_LOG_LEVEL;
        } else {
            return 0;
        }
    }

}
