package com.example.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Separator {
    /**
     * метод, который разархивирует файл
     *
     * @param fileName - файл, который надо разархивировать.
     * @throws IOException - исключение в случае если файл не найден
     */
    static void fileReader(String fileName) throws IOException {
        Scanner lines = new Scanner(new File(fileName));
        FileWriter newFiles;
        while (lines.hasNext()) {
            newFiles = new FileWriter(lines.nextLine());
            int k = lines.nextInt();
            lines.nextLine();
            for (int i = 0; i < k; i++) {
                newFiles.write(lines.nextLine() + "\n");
            }
            if (lines.hasNext()) lines.nextLine();
            newFiles.close();
        }
        lines.close();
    }
}
