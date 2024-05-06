package me.gritter.plexrename.findfilesbehaviour;

import java.io.File;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RecursivePatternFindFilesBehaviour implements FindFilesBehaviour {

    private final Pattern filePattern;

    private final Pattern directoryPattern;

    public RecursivePatternFindFilesBehaviour(Pattern filePattern, Pattern directoryPattern) {
        this.filePattern = filePattern;
        this.directoryPattern = directoryPattern;
    }

    @Override
    public Stream<File> findFiles(File parent) {
        System.out.println("[RecursivePatternFindFilesBehaviour] Looking through " + parent.getPath());

        File[] children = parent.listFiles();
        if (children == null) {
            return Stream.empty();
        }

        Stream.Builder<File> result = Stream.builder();

        for (File child : children) {
            if (child.isDirectory()) {
                if (!directoryPattern.matcher(child.getName()).matches()) {
                    System.out.println("[RecursivePatternFindFilesBehaviour] Ignoring directory '" + child.getName() + "' because the directory pattern doesn't match.");
                } else {
                    findFiles(child).forEach(result::add);
                }
            } else if (!filePattern.matcher(child.getName()).matches()) {
                System.out.println("[RecursivePatternFindFilesBehaviour] Ignoring file '" + child.getName() + "' because the file pattern doesn't match.");
            } else {
                result.add(child);
            }
        }

        return result.build();
    }
}
