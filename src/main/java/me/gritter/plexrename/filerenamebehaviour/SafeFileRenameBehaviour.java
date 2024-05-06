package me.gritter.plexrename.filerenamebehaviour;

import java.io.File;
import java.util.Scanner;

public class SafeFileRenameBehaviour implements FileRenameBehaviour {

    private static final FileRenameBehaviour DEFAULT_BACKEND = new ActualFileRenameBehaviour();

    private final FileRenameBehaviour backend;

    private boolean renameBlindly = false;

    public SafeFileRenameBehaviour(FileRenameBehaviour backend) {
        this.backend = backend;
    }

    public SafeFileRenameBehaviour() {
        this(DEFAULT_BACKEND);
    }

    @Override
    public void rename(File file, String newName) {
        System.out.println();

        if (newName.equals(file.getName())) {
            System.out.println("[SafeRenameBehaviour] File '" + file.getName() + "' won't get renamed, because the new name matches the old one.");
            return;
        }

        System.out.println("[SafeRenameBehaviour] Renaming '" + file.getName() + "'");
        System.out.println("[SafeRenameBehaviour]       to '" + newName + "'.");

        if (!renameBlindly) {
            System.out.println("[SafeRenameBehaviour] Type y to rename this file, Y to rename this file and all upcoming files, s to skip this rename and any other key to exit.");
            System.out.print("[SafeRenameBehaviour] Action (y/Y/s/e): ");
            String input = readLine();

            switch (input) {
                case "y":
                    // Rename this file only.
                    break;
                case "Y":
                    // Rename this file and all upcoming files.
                    renameBlindly = true;
                    break;
                case "s":
                    // Skip this file
                    return;
                default:
                    // Exit
                    System.exit(0);
                    return;
            }
        }

        backend.rename(file, newName);
    }

    private String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
