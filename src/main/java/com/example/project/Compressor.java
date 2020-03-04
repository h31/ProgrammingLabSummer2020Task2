package com.example.project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class Compressor {
    /**
     * метод, архивирующий несколько файлов в один.
     *
     * @param filesToConnect - файлы, которые будут заархивированы
     * @param output         - название архива
     * @throws FileNotFoundException - исключение в случае если файл не найден
     */
    static void fileWriter(List<String> filesToConnect, String output) throws FileNotFoundException {
        StringBuilder lineToWrite = new StringBuilder();
        int counterOfLines = 0;
        if (filesToConnect.isEmpty()) throw new IllegalArgumentException();
        Formatter newFiles = new Formatter(output);// файл, куда буду записывать
        for (int i = 0; i < filesToConnect.size(); i++) {
            File part = new File(filesToConnect.get(i));//поочерёдно перебираю файлы, которые буду соединять
            Scanner lines = new Scanner(part);
            if (i != 0) {
                newFiles.format(counterOfLines + "\n");
                newFiles.format(lineToWrite.toString());
                counterOfLines = 0;
                lineToWrite = new StringBuilder();
                newFiles.format("\n");
            }
            newFiles.format(part.toString() + "\n"); //отделяю файлы друг от друга пробелами и названиями
            while (lines.hasNext()) {
                counterOfLines++;
                lineToWrite.append(lines.nextLine()).append("\n");
            }
        }
        newFiles.format(counterOfLines + "\n");
        newFiles.format(lineToWrite.toString());
        newFiles.close();
    }
}
