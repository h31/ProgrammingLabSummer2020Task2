package com.example.project;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Formatter;
import java.io.File;

public class Tar {

    public static void main(String[] args) throws FileNotFoundException {
        int k = 1;
        List<String> filesToConnect = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i != args.length - 1) str.append(args[i]).append(" ");
            else str.append(args[i]);
        }
        String line = str.toString();
        if (line.contains("-u")) {
            if (!line.matches("^tar\\s+-u\\s+[^\\\\*:?|\"<>]+\\.txt$")) System.out.println("Wrong command");
        } else if (!line.matches("^tar\\s+([^\\\\*:?|\"<>]+\\.txt)+\\s+-out\\s+[^\\\\*:?|\"<>]+\\.txt$"))
            System.out.println("Wrong command");;
        String[] parts = line.split("\\s+");
        if (parts[1].equals("-u")) fileReader(parts[2]);
        else {
            while (!parts[k].equals("-out")) {
                filesToConnect.add(parts[k]);
                k++;
            }
            fileWriter(filesToConnect, parts[k + 1]);
        }
    }

    /**
     * метод, архивирующий несколько файлов в один.
     * @param filesToConnect - файлы, которые будут заархивированы
     * @param output - название архива
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
            newFiles.format(part.toString()+ "\n"); //отделяю файлы друг от друга пробелами и названиями
            while (lines.hasNext()) {
                counterOfLines++;
                lineToWrite.append(lines.nextLine() + "\n");
            }
        }
        newFiles.format(counterOfLines + "\n");
        newFiles.format(lineToWrite.toString());
        newFiles.close();
    }

    /**
     * метод, который разархивирует файл
     * @param fileName - файл, который надо разархивировать.
     * @throws FileNotFoundException - исключение в случае если файл не найден
     */
    static void fileReader(String fileName) throws FileNotFoundException {
        File x = new File(fileName);
        StringBuilder a = new StringBuilder();
        Scanner lines = new Scanner(x);
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
    }
}