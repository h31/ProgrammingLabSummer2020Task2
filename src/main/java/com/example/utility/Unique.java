package com.example.utility;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Objects;

public class Unique {
    private final StringBuilder dataForOutFile = new StringBuilder();//строки для помещения в файл
    private final ArrayList<String> allLines = new ArrayList<>();//список для считаывания строк из файла
    private final HashSet<String> uniqueWords = new HashSet<>();//сет для обьединения одинаковых строк

    public StringBuilder getDataForOutFile() {
        return dataForOutFile;
    }

    public ArrayList<String> getAllLines() {
        return allLines;
    }

    UniqueLauncher uniqueLauncher = new UniqueLauncher();

    /**
     * Отдельный метод реализующий ввод данных из консоли
     */
    public void inputFileDataConsole() {
        Scanner scanner = new Scanner(System.in);
        String inputData = scanner.nextLine();

        allLines.addAll(Arrays.asList(inputData.split("\\\\\\\\n")));
    }

    /**
     * Отдельный метод реализующий ввод данных из файла
     */
    public void inputFileData(String input) throws IOException {
        File file = new File(input);
        if (!file.exists()) {
            System.err.print("File not found" + ": " + input);
        } else {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                allLines.add(line);
            }
            bufferedReader.close();
        }
    }

    void append(String firstString) {
        if (uniqueWords.isEmpty())
            dataForOutFile.append(firstString).append("\n");
        else
            dataForOutFile.append(uniqueWords.iterator().next()).append("\n");

        uniqueWords.clear();
    }

    /**
     * Сравнение игнорируя первые несколько символов каждой строки, регистр или сравнение по умолчанию
     */
    public void equalsCombined(String option, int num) {
        String firstString = allLines.get(0);

        for (int i = 1; i < allLines.size(); i++) {
            if (option.equals("i")) {//ветки в условиях не обьедены для большей читаемости кода
                if (firstString.equalsIgnoreCase(allLines.get(i))) {
                    uniqueWords.add(firstString);
                } else {
                    append(firstString);
                    firstString = allLines.get(i);
                }

            } else if (option.equals("s")) {
                String wordWithIgnore = firstString.substring(num, allLines.get(i - 1).length());//выделение подстроки

                if (wordWithIgnore.equals(allLines.get(i).substring(num))) {//проверка слово с игнорируемыми символами
                    uniqueWords.add(firstString);
                } else {
                    append(firstString);
                    firstString = allLines.get(i);
                }
            } else {
                if (firstString.equals(allLines.get(i))) {
                    uniqueWords.add(firstString);
                } else {
                    append(firstString);
                    firstString = allLines.get(i);
                }
            }
        }
        dataForOutFile.append(firstString);
    }

    /**
     * Метод возвращающий только уникальные строки
     */
    public void equalsUnique() {
        int countWord = 0;

        for (int i = 0; i < allLines.size(); i++) {
            for (int j = 0; j < allLines.size(); j++) {
                if (allLines.get(i).equals(allLines.get(j)) && i != j) {
                    countWord++;
                }
            }
            if (countWord == 0) {
                dataForOutFile.append(allLines.get(i)).append("\n");
            }
            countWord = 0;
        }
    }

    /**
     * Метод добавляющий количество замененных строк
     */
    public void equalsWithCountString() {
        int count = 1;
        for (int i = 0; i < allLines.size(); i++) {
            for (int j = i + 1; j < allLines.size(); j++) {
                if (allLines.get(i).equals(allLines.get(j))) {
                    uniqueWords.add(allLines.get(j));
                    count++;
                }
            }
            dataForOutFile.append(count).append(" ").append(allLines.get(i)).append("\n");

            if (!uniqueWords.isEmpty()) {
                i += count - 1;
                count = 1;
                uniqueWords.clear();
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unique unique = (Unique) o;
        return Objects.equals(dataForOutFile, unique.dataForOutFile) &&
                Objects.equals(allLines, unique.allLines) &&
                Objects.equals(uniqueWords, unique.uniqueWords);

    }

    @Override
    public int hashCode() {
        return Objects.hash(dataForOutFile, allLines, uniqueWords);
    }

    @Override
    public String toString() {
        return "Unique{" +
                "dataForOutFile=" + dataForOutFile +
                ", allLines=" + allLines +
                ", uniqueWords=" + uniqueWords +
                '}';
    }
}
