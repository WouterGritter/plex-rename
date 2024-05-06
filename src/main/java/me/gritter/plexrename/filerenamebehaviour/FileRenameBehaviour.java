package me.gritter.plexrename.filerenamebehaviour;

import java.io.File;

public interface FileRenameBehaviour {

    void rename(File file, String newName);
}
