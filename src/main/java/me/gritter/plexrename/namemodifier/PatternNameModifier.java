package me.gritter.plexrename.namemodifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternNameModifier implements NameModifier {

    private static final Pattern CORRECT_PATTERN = Pattern.compile("[^-]+- S\\d{2}E\\d{2}( - .+)?");

    private static final String NAME_FORMAT = "%s - S%02dE%02d";
    private static final String TITLE_APPEND_FORMAT = " - %s";

    private final Pattern filePattern;

    private final Integer defaultSeasonNumber;

    private final boolean includeTitle;

    private final String seriesName;

    public PatternNameModifier(Pattern filePattern, Integer defaultSeasonNumber, boolean includeTitle, String seriesName) {
        this.filePattern = filePattern;
        this.defaultSeasonNumber = defaultSeasonNumber;
        this.includeTitle = includeTitle;
        this.seriesName = seriesName;
    }

    @Override
    public String generateNewName(String name) {
        if (CORRECT_PATTERN.matcher(name).matches()) {
            System.out.println("[PatternNameModifier] '" + name + "' already appears to be in the correct format, skipping...");
            return null;
        }

        int season = parseSeasonNumber(name);
        int episode = parseEpisodeNumber(name);

        String newName = String.format(NAME_FORMAT, seriesName, season, episode);
        if (includeTitle) {
            String title = parseTitle(name);
            newName += String.format(TITLE_APPEND_FORMAT, title);
        }

        return newName;
    }

    private int parseSeasonNumber(String name) {
        Integer season = safeFindIntGroup(name, "season");

        if (season == null) {
            if (defaultSeasonNumber == null) {
                throw new IllegalStateException("Season number could be parsed and no default was provided for '" + name + "'.");
            } else {
                season = defaultSeasonNumber;
            }
        }

        return season;
    }

    private int parseEpisodeNumber(String name) {
        Integer episode = safeFindIntGroup(name, "episode");

        if (episode == null) {
            throw new IllegalStateException("Episode number could be parsed for '" + name + "'.");
        }

        return episode;
    }

    private String parseTitle(String name) {
        String title = safeFindStringGroup(name, "title");

        if (title == null) {
            throw new IllegalStateException("Title could be parsed for '" + name + "'.");
        }

        return title;
    }

    private String safeFindStringGroup(String input, String groupName) {
        Matcher matcher = filePattern.matcher(input);
        if (!matcher.find()) {
            throw new IllegalStateException("No match for '" + input + "'.");
        }

        try {
            return matcher.group(groupName);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private Integer safeFindIntGroup(String input, String groupName) {
        String value = safeFindStringGroup(input, groupName);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ignored) {
            }
        }

        return null;
    }
}
