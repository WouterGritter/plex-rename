package me.gritter.plexrename.namemodifier;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.replace;

public class ReplaceNameModifier implements NameModifier {

    private final String find;
    private final String replace;

    public ReplaceNameModifier(String find, String replace) {
        this.find = find;
        this.replace = replace;
    }

    @Override
    public String generateNewName(String name) {
        return replace(name, find, replace);
    }
}
