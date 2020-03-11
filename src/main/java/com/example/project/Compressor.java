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
        StringBuilder lineToWrite = new StringBuilder();
        int counterOfLines = 0;
        if (filesToArchive.isEmpty()) throw new IllegalArgumentException();
        FileWriter newArchive = new FileWriter(output);
        for (int i = 0; i < filesToArchive.size(); i++) {
            Scanner lines = new Scanner(filesToArchive.get(i));
            if (i != 0) {
                newArchive.write(counterOfLines + "\n");
                newArchive.write(lineToWrite.toString());
                counterOfLines = 0;
                lineToWrite = new StringBuilder();
                newArchive.write("\n");
            }
            newArchive.write(filesToArchive.get(i).toString() + "\n");
            while (lines.hasNext()) {
                counterOfLines++;
                lineToWrite.append(lines.nextLine()).append("\n");
            }
        }
        newArchive.write(counterOfLines + "\n");
        newArchive.write(lineToWrite.toString());
        newArchive.close();
    }
}
