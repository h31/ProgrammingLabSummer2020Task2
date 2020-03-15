package com.example.project;

import java.io.*;
import java.util.List;

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
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            newArchive.write(file.toString() + "\n");
            int k;
            while ((k = reader.read()) != -1) {
                if ((char) k == '\n') counterOfLines++;
                lineToWrite.append((char) k);
            }
            newArchive.write(counterOfLines + "\n");
            newArchive.write(lineToWrite.toString());
            newArchive.write("\n");
        }
        newArchive.close();
    }
}
