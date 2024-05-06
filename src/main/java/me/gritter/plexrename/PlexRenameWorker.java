package me.gritter.plexrename;

import me.gritter.plexrename.findfilesbehaviour.FindFilesBehaviour;
import me.gritter.plexrename.findfilesbehaviour.RecursivePatternFindFilesBehaviour;
import me.gritter.plexrename.namemodifier.*;
import me.gritter.plexrename.filerenamebehaviour.FileRenameBehaviour;
import me.gritter.plexrename.filerenamebehaviour.SafeFileRenameBehaviour;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;
import static org.apache.commons.lang3.StringUtils.substringBeforeLast;

public class PlexRenameWorker {

    private final File workingDirectory;

    private final FindFilesBehaviour findFilesBehaviour;

    private final FileRenameBehaviour fileRenameBehaviour;

    private final NameModifier nameModifier;

    public PlexRenameWorker(File workingDirectory, FindFilesBehaviour findFilesBehaviour, FileRenameBehaviour fileRenameBehaviour, NameModifier nameModifier) {
        if (!workingDirectory.exists()) {
            throw new IllegalArgumentException("Working directory does not exist.");
        } else if (!workingDirectory.isDirectory()) {
            throw new IllegalArgumentException("Working directory is not a directory.");
        }

        this.workingDirectory = workingDirectory;
        this.findFilesBehaviour = findFilesBehaviour;
        this.fileRenameBehaviour = fileRenameBehaviour;
        this.nameModifier = nameModifier;
    }

    public void run() {
        Collection<File> files = findFilesBehaviour.findFiles(workingDirectory).toList();

        for (File file : files) {
            String name = substringBeforeLast(file.getName(), ".");

            String newName = nameModifier.generateNewName(name);

            if (newName == null) {
                System.out.println("[PlexRenameWorker] Skipping null new name for file '" + file.getName() + "'.");
            } else {
                String extension = substringAfterLast(file.getName(), ".");
                String newFileName = newName + "." + extension;

                fileRenameBehaviour.rename(file, newFileName);
            }
        }
    }
}
