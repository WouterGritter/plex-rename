package me.gritter.plexrename.findfilesbehaviour;

import java.io.File;
import java.util.stream.Stream;

public interface FindFilesBehaviour {

    Stream<File> findFiles(File parent);
}
