package me.gritter.plexrename.findfilesbehaviour;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class PatternFindFilesBehaviour implements FindFilesBehaviour {

    private final Pattern filePattern;

    public PatternFindFilesBehaviour(Pattern filePattern) {
        this.filePattern = filePattern;
    }

    @Override
    public Stream<File> findFiles(File parent) {
        System.out.println("[PatternFindFilesBehaviour] Looking through " + parent.getPath());

        File[] children = parent.listFiles();
        if (children == null) {
            return Stream.empty();
        }

        Stream.Builder<File> result = Stream.builder();

        for (File child : children) {
            if (child.isDirectory()) {
                System.out.println("[PatternFindFilesBehaviour] Ignoring directory '" + child.getName() + "'.");
            } else if (!filePattern.matcher(child.getName()).matches()) {
                System.out.println("[PatternFindFilesBehaviour] Ignoring file '" + child.getName() + "' because the file pattern doesn't match.");
            } else {
                result.add(child);
            }
        }

        return result.build();
    }
}
