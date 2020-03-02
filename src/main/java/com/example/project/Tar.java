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
        String line = new Scanner(System.in).nextLine();
        if (line.contains("-u")) {
            if (!line.matches("^tar\\s+-u\\s+[^\\\\*:?|\"<>]+\\.txt$")) throw new IllegalArgumentException();
        } else if (!line.matches("^tar\\s+([^\\\\*:?|\"<>]+\\.txt)+\\s+-out\\s+[^\\\\*:?|\"<>]+\\.txt$"))
            throw new IllegalArgumentException();
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
        if (filesToConnect.isEmpty()) throw new IllegalArgumentException();
        Formatter newFiles = new Formatter(output);// файл, куда буду записывать
        for (int i = 0; i < filesToConnect.size(); i++) {
            File part = new File(filesToConnect.get(i));//поочерёдно перебираю файлы, которые буду соединять
            Scanner lines = new Scanner(part);
            if (i != 0) newFiles.format("\r\n");
            newFiles.format(part.toString() + "\r\n"); //отделяю файлы друг от друга пробелами и названиями
            while (lines.hasNext()) {
                newFiles.format(lines.next() + "\r\n");
            }
            newFiles.format("File ended\r\n");//пометка что закончился этот файл
        }
        newFiles.close();
    }

    /**
     * метод, который разархивирует файл
     * @param fileName - файл, который надо разархивировать.
     * @throws FileNotFoundException - исключение в случае если файл не найден
     */
    static void fileReader(String fileName) throws FileNotFoundException {
        String line;
        File x = new File(fileName);
        Scanner lines = new Scanner(x);
        Formatter newFiles = new Formatter(lines.nextLine());
        while (lines.hasNext()) {
            line = lines.nextLine();
            if (line.equals("File ended") && lines.hasNext()) {
                lines.nextLine();
                newFiles.close();
                newFiles = new Formatter(lines.nextLine());
            } else newFiles.format(line + "\r\n");
        }
        newFiles.close();
    }
}