package io_and_handling_exceptions.input_output.exercise1;

import java.io.File;
import java.util.Scanner;

public class LsCommandSimulator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the full path to the directory: ");
        String path = scanner.nextLine();

        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
            File[] contents = directory.listFiles();
            if (contents != null) {
                for (File file : contents) {
                    String type = file.isDirectory() ? "<DIR>" : "<FILE>";

                    String r = file.canRead() ? "r" : "-";
                    String w = file.canWrite() ? "w" : "-";
                    String h = file.isHidden() ? "h" : "-";

                    System.out.println(file.getAbsolutePath() + " " + type + " " + r + w + h);
                }
            }
        } else {
            System.out.println("Error: The path provided is not a valid directory.");
        }
        scanner.close();
    }
}