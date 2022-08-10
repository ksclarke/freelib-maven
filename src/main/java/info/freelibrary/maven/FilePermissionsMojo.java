
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
@Mojo(name = "set-file-perms")
public class FilePermissionsMojo extends AbstractMojo {

    /**
     * The Maven project directory.
     */
    @Parameter(defaultValue = "${project}")
    protected MavenProject myProject;

    /**
     * The permissions to set.
     */
    @Parameter(alias = "perms")
    protected int myPerms;

    /**
     * The file or directory on which to set permissions.
     */
    @Parameter(alias = "file")
    protected File myFile;

    /**
     * A list of files or directories on which to set permissions.
     */
    @Parameter(alias = "files")
    protected List<String> myFiles;

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

}
