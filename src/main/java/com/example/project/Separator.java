package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class Separator {
    /**
     * метод, который разархивирует файл
     *
     * @param fileName - файл, который надо разархивировать.
     * @throws FileNotFoundException - исключение в случае если файл не найден
     */
    static void fileReader(String fileName) throws FileNotFoundException {
        Scanner lines = new Scanner(new File(fileName));
        Formatter newFiles;
        while (lines.hasNext()) {
            newFiles = new Formatter(lines.nextLine());
            int k = lines.nextInt();
            lines.nextLine();
            for (int i = 0; i < k; i++) {
                newFiles.format(lines.nextLine() + "\n");
            }
            if (lines.hasNext()) lines.nextLine();
            newFiles.close();
        }
        lines.close();
    }
}
