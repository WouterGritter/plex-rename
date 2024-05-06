package me.gritter.plexrename.namemodifier;

import java.util.List;

public class CompositeNameModifier implements NameModifier {

    private final List<NameModifier> nameModifiers;

    public CompositeNameModifier(List<NameModifier> nameModifiers) {
        this.nameModifiers = nameModifiers;
    }

    public CompositeNameModifier(NameModifier... nameModifiers) {
        this.nameModifiers = List.of(nameModifiers);
    }

    @Override
    public String generateNewName(String name) {
        for (NameModifier nameModifier : nameModifiers) {
            name = nameModifier.generateNewName(name);
            if (name == null) {
                return null;
            }
        }

        return name;
    }
}
