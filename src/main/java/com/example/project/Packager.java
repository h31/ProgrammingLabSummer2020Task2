package com.example.project;

import java.io.*;
import java.util.List;

public class Packager {
    /**
     * метод, архивирующий несколько файлов в один.
     *
     * @param filesToArchive - файлы, которые будут заархивированы
     * @param output         - созданный архив
     * @throws IOException - исключение в случае если файл не найден
     */
    static void filePack(List<File> filesToArchive, File output) throws IOException {
        StringBuilder lineToWrite;
        if (filesToArchive.isEmpty()) throw new IllegalArgumentException();
        try (BufferedWriter newArchive = new BufferedWriter(new FileWriter(output))) {
            for (File file : filesToArchive) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                    lineToWrite = new StringBuilder();
                    String[] partsOfName = file.toString().split("[/\\\\]");
                    for (int i = 0; i < partsOfName.length; i++) {
                        if (i != 0) lineToWrite.append("_");
                        lineToWrite.append(partsOfName[i]);
                    }
                    newArchive.write(lineToWrite.toString() + "\n");
                    int k;
                    int counterOfChars = 0;
                    lineToWrite = new StringBuilder();
                    while ((k = reader.read()) != -1) {
                        counterOfChars++;
                        lineToWrite.append((char) k);
                    }
                    newArchive.write(counterOfChars + "\n");
                    newArchive.write(lineToWrite.toString());
                }
            }
        }
    }
}
