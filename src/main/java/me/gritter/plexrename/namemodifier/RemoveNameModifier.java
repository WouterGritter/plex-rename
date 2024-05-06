package me.gritter.plexrename.namemodifier;

import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.*;

public class RemoveNameModifier implements NameModifier {

    private final String substringToRemove;
    private final SubstringPlacement substringPlacement;

    public RemoveNameModifier(String substringToRemove, SubstringPlacement substringPlacement) {
        this.substringToRemove = substringToRemove;
        this.substringPlacement = substringPlacement;
    }

    @Override
    public String generateNewName(String name) {
        return switch (substringPlacement) {
            case START -> removeStart(name, substringToRemove);
            case END -> removeEndIgnoreCase(name, substringToRemove);
            case ANYWHERE -> remove(name, substringToRemove);
        };
    }

    public enum SubstringPlacement {
        START, END, ANYWHERE;
    }
}
