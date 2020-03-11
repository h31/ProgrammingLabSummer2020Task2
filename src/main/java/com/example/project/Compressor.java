package com.example.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Compressor {
    /**
     * метод, архивирующий несколько файлов в один.
     *
     * @param filesToArchive - файлы, которые будут заархивированы
     * @param output         - название архива
     * @throws IOException - исключение в случае если файл не найден
     */
    static void fileWriter(List<File> filesToArchive, String output) throws IOException {
        StringBuilder lineToWrite;
        int counterOfLines;
        if (filesToArchive.isEmpty()) throw new IllegalArgumentException();
        FileWriter newArchive = new FileWriter(output);
        for (File file : filesToArchive) {
            counterOfLines = 0;
            lineToWrite = new StringBuilder();
            Scanner lines = new Scanner(file);
            newArchive.write(file.toString() + "\n");
            while (lines.hasNext()) {
                counterOfLines++;
                lineToWrite.append(lines.nextLine()).append("\n");
            }
            newArchive.write(counterOfLines + "\n");
            newArchive.write(lineToWrite.toString());
            newArchive.write("\n");
        }
        newArchive.close();
    }
}
