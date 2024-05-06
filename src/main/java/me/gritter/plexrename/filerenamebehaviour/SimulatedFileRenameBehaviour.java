package me.gritter.plexrename.filerenamebehaviour;

import java.io.File;

public class SimulatedFileRenameBehaviour implements FileRenameBehaviour {

    @Override
    public void rename(File file, String newName) {
        System.out.println();

        if (newName.equals(file.getName())) {
            System.out.println("[SimulatedRenameBehaviour] File '" + file.getName() + "' wouldn't get renamed, because the new name matches the old one.");
        } else {
            System.out.println("[SimulatedRenameBehaviour]                 File '" + file.getName() + "'");
            System.out.println("[SimulatedRenameBehaviour] would get renamed to '" + newName + "'.");
        }
    }
}
