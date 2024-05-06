package me.gritter.plexrename;

import me.gritter.plexrename.filerenamebehaviour.FileRenameBehaviour;
import me.gritter.plexrename.filerenamebehaviour.SafeFileRenameBehaviour;
import me.gritter.plexrename.findfilesbehaviour.FindFilesBehaviour;
import me.gritter.plexrename.findfilesbehaviour.RecursivePatternFindFilesBehaviour;
import me.gritter.plexrename.namemodifier.CompositeNameModifier;
import me.gritter.plexrename.namemodifier.NameModifier;
import me.gritter.plexrename.namemodifier.PatternNameModifier;
import me.gritter.plexrename.namemodifier.ReplaceNameModifier;

import java.io.File;

import static java.util.regex.Pattern.compile;

public class Main {

    public static void main(String[] args) {
        File workingDirectory = new File("Z:\\plex\\Super Awesome Series");

        FindFilesBehaviour findFilesBehaviour = new RecursivePatternFindFilesBehaviour(
                compile(".*\\.mkv"),
                compile("Season \\d+")
        );

        FileRenameBehaviour fileRenameBehaviour = new SafeFileRenameBehaviour();

        NameModifier nameModifier = new CompositeNameModifier(
                new PatternNameModifier(
                        compile("SuperAwesomeSeries.s(?<season>\\d+)\\.?e(?<episode>\\d+)\\.(?<title>.+)"),
                        null,
                        true,
                        "Super Awesome Series"
                ),
                new ReplaceNameModifier(".", " ")
        );

        new PlexRenameWorker(workingDirectory, findFilesBehaviour, fileRenameBehaviour, nameModifier).run();
    }
}
