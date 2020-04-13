package com.example.utility;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

import java.util.*;

//изменить имена в тестах

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

    /**
     * Отдельный метод реализующий вывод данных в файл
     */
    public void outputFileData(String output) throws IOException {
        File file = new File(output);
        if (file.exists()) {//Проверка существованмя файла
            System.setOut(new PrintStream(output));
            System.out.write(dataForOutFile.toString().getBytes());
        } else {
            System.err.print("File not found" + ": " + output);
        }
    }

    /**
     * Сравнение игнорируя первые несколько символов каждой строки или регистр
     */
    public void equalsIgnoreSomeChars(int countIgnore) {
        StringBuilder firstString = new StringBuilder(allLines.get(0));

        for (int i = 1; i < allLines.size(); i++) {
            String wordWithIgnore = firstString.substring(countIgnore, allLines.get(i - 1).length());//выделение подстроки

            if (wordWithIgnore.equals(allLines.get(i).substring(countIgnore)) || wordWithIgnore.equalsIgnoreCase(allLines.get(i).substring(countIgnore))) {
                uniqueWords.add(firstString.toString());
            } else {
                if (uniqueWords.isEmpty())
                    dataForOutFile.append(firstString.append("\n"));
                else
                    dataForOutFile.append(uniqueWords.iterator().next()).append("\n");

                firstString.setLength(0);
                firstString.append(allLines.get(i));
                uniqueWords.clear();
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
