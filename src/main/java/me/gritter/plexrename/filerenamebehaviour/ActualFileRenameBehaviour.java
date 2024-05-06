package me.gritter.plexrename.filerenamebehaviour;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ActualFileRenameBehaviour implements FileRenameBehaviour {

    @Override
    public void rename(File file, String newName) {
        File target = new File(file.getParentFile(), newName);

        try {
            Files.move(file.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
