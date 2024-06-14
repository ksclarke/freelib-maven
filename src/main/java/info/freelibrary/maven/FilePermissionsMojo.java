
package info.freelibrary.maven;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import info.freelibrary.util.FileUtils;

/**
 * A Maven plugin to set file permissions on a project file (probably one created by the build).
 */
@Mojo(name = MojoNames.SET_FILE_PERMS)
public class FilePermissionsMojo extends AbstractMojo {

    /**
     * The file or directory on which to set permissions.
     */
    @Parameter(alias = Config.FILE)
    protected File myFile;

    /**
     * A list of files or directories on which to set permissions.
     */
    @Parameter(alias = Config.FILES)
    protected List<String> myFiles;

    /**
     * The permissions to set.
     */
    @Parameter(alias = Config.PERMISSIONS)
    protected int myPerms;

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final Set<PosixFilePermission> perms = FileUtils.convertToPermissionsSet(myPerms);

        try {
            if (myFile != null) {
                Files.setPosixFilePermissions(myFile.toPath(), perms);
            } else if (myFiles != null) {
                final Iterator<String> iterator = myFiles.iterator();

                while (iterator.hasNext()) {
                    Files.setPosixFilePermissions(new File(iterator.next()).toPath(), perms);
                }
            }
        } catch (final IOException details) {
            throw new MojoExecutionException(details.getMessage(), details);
        }
    }

    /**
     * Configuration options for the Mojo.
     */
    private static final class Config {

        /**
         * The file configuration option.
         */
        private static final String FILE = "file";

        /**
         * The files configuration option.
         */
        private static final String FILES = "files";

        /**
         * The permissions configuration option.
         */
        private static final String PERMISSIONS = "perms";
    }
}
