
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.BUNDLE_NAME;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;
import info.freelibrary.util.MessageCodes;

/**
 * Sets the logging level for Maven builds to ERROR instead of INFO. The only other way to do this is to configure the
 * logging level for all Maven builds in <code>${MAVEN_HOME}/conf/logging/simplelogger.properties</code>.
 * <p>
 * The plugin comes with defaults, but can also be configured like: <pre><code>
     &lt;plugin&gt;
       &lt;groupId&gt;info.freelibrary&lt;/groupId&gt;
       &lt;artifactId&gt;freelib-utils&lt;/artifactId&gt;
       &lt;version&gt;${freelib.utils.version}&lt;/version&gt;
       &lt;executions&gt;
         &lt;execution&gt;
           &lt;id&gt;set-level&lt;/id&gt;
           &lt;phase&gt;validate&lt;/phase&gt;
           &lt;configuration&gt;
             &lt;myLevel&gt;debug&lt;/myLevel&gt;
             &lt;myExcludedLoggerNames&gt;
               &lt;loggerName&gt;org.apache.maven.cli.event.ExecutionEventLogger&lt;/loggerName&gt;
             &lt;/myExcludedLoggerNames&gt;
             &lt;myIncludedLoggerNames&gt;
               &lt;loggerName&gt;org.apache.maven.tools.plugin.scanner.DefaultMojoScanner&lt;/loggerName&gt;
             &lt;/myIncludedLoggerNames&gt;
           &lt;/configuration&gt;
           &lt;goals&gt;
             &lt;goal&gt;configure-logging&lt;/goal&gt;
           &lt;/goals&gt;
         &lt;/execution&gt;
       &lt;/executions&gt;
     &lt;/plugin&gt;
 * </code></pre>
 */
@Mojo(name = "configure-logging")
public class MavenLoggingMojo extends AbstractMojo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MavenLoggingMojo.class, BUNDLE_NAME);

    /**
     * The log level to reset the Maven loggers too.
     */
    @Parameter(property = "level", defaultValue = "error")
    private String myLevel;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final int level = MavenUtils.getLevelIntCode(myLevel);

        if (level == 0) {
            LOGGER.warn(MessageCodes.MVN_009, myLevel);
        }

        MavenUtils.setLogLevels(level == 0 ? MavenUtils.ERROR_LOG_LEVEL : level, MavenUtils.getMavenLoggers());
    }
}
