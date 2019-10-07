
package info.freelibrary.maven;

import static info.freelibrary.util.Constants.BUNDLE_NAME;
import static info.freelibrary.util.FileUtils.sizeFromBytes;

import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import info.freelibrary.util.Logger;
import info.freelibrary.util.LoggerFactory;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * Sets Maven project properties with values for system.cores, system.free.memory, and system.total.memory; memory
 * values are set with unit of measurement appended (e.g., 200m, 3g, 5000k).
 * <p>
 * To manually run the plugin: `mvn info.freelibrary:freelib-utils:[VERSION]:set-cpumem-properties` (supplying
 * whatever version is appropriate). Usually, though, the plugin would just be configured to run as a part of the
 * Maven lifecycle.
 * </p>
 */
@Mojo(name = "set-cpumem-properties", defaultPhase = LifecyclePhase.INITIALIZE)
public class CPUandMemoryMojo extends AbstractMojo {

    public static final String SYSTEM_CORES = "system.cores";

    public static final String SYSTEM_FREE_MEMORY = "system.free.memory";

    public static final String SYSTEM_TOTAL_MEMORY = "system.total.memory";

    private static final Logger LOGGER = LoggerFactory.getLogger(CPUandMemoryMojo.class, BUNDLE_NAME);

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * A percentage of the total memory to return instead of the total.
     */
    @Parameter(alias = "free-mem-percent", property = "free-mem-percent", defaultValue = "1")
    private String myFreeMemPercent;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Properties properties = myProject.getProperties();
        final int cores = Runtime.getRuntime().availableProcessors();
        final SystemInfo si = new SystemInfo();
        final HardwareAbstractionLayer hal = si.getHardware();
        final GlobalMemory memory = hal.getMemory();
        final double freeMemPercent = Double.parseDouble(myFreeMemPercent);
        final long freeMemory = memory.getAvailable();

        properties.setProperty(SYSTEM_CORES, Integer.toString(cores));

        if (!"1".equals(myFreeMemPercent)) {
            LOGGER.info(MessageCodes.MVN_007, myFreeMemPercent, sizeToString(freeMemory));
        }

        properties.setProperty(SYSTEM_FREE_MEMORY, sizeToString((long) (freeMemory * freeMemPercent)));
        properties.setProperty(SYSTEM_TOTAL_MEMORY, sizeToString(memory.getTotal()));

        LOGGER.info(MessageCodes.MVN_004, properties.getProperty(SYSTEM_FREE_MEMORY));
        LOGGER.info(MessageCodes.MVN_005, properties.getProperty(SYSTEM_TOTAL_MEMORY));
        LOGGER.info(MessageCodes.MVN_006, properties.getProperty(SYSTEM_CORES));

    }

    /**
     * Convert the size string returned by {@link FileUtils#sizeFromBytes} into the format we need for the JVM.
     *
     * @param aSizeInBytes
     * @return A formatted string representation of the supplied amount of memory
     */
    private String sizeToString(final long aSizeInBytes) {
        final String size = sizeFromBytes(aSizeInBytes, true).replace(" ", "");
        return size.substring(0, size.length() - 1);
    }
}
