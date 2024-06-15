
package info.freelibrary.maven;

import org.apache.maven.plugin.logging.Log;

import info.freelibrary.util.Logger;

/**
 * A Maven log implementation that uses an SLF4J logger underneath.
 */
public class MavenLogger implements Log {

    /**
     * The wrapped SLF4J logger.
     */
    private final Logger myLogger;

    /**
     * Creates a new Maven logger from the supplied SLF4J logger.
     *
     * @param aLogger A SLF4J logger
     */
    public MavenLogger(final Logger aLogger) {
        myLogger = aLogger;
    }

    @Override
    public void debug(final CharSequence aMessage) {
        myLogger.debug(aMessage.toString());
    }

    @Override
    public void debug(final CharSequence aMessage, final Throwable aError) {
        myLogger.debug(aMessage.toString(), aError);
    }

    @Override
    public void debug(final Throwable aError) {
        myLogger.debug(aError.getMessage(), aError);
    }

    @Override
    public void error(final CharSequence aMessage) {
        myLogger.error(aMessage.toString());
    }

    @Override
    public void error(final CharSequence aMessage, final Throwable aError) {
        myLogger.error(aMessage.toString(), aError);
    }

    @Override
    public void error(final Throwable aError) {
        myLogger.error(aError.getMessage(), aError);
    }

    @Override
    public void info(final CharSequence aMessage) {
        myLogger.info(aMessage.toString());
    }

    @Override
    public void info(final CharSequence aMessage, final Throwable aError) {
        myLogger.info(aMessage.toString(), aError);
    }

    @Override
    public void info(final Throwable aError) {
        myLogger.info(aError.getMessage(), aError);
    }

    @Override
    public boolean isDebugEnabled() {
        return myLogger.isDebugEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return myLogger.isErrorEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return myLogger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return myLogger.isWarnEnabled();
    }

    @Override
    public void warn(final CharSequence aMessage) {
        myLogger.warn(aMessage.toString());
    }

    @Override
    public void warn(final CharSequence aMessage, final Throwable aError) {
        myLogger.warn(aMessage.toString(), aError);
    }

    @Override
    public void warn(final Throwable aError) {
        myLogger.warn(aError.getMessage(), aError);
    }

}
