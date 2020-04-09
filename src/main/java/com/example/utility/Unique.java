package com.example.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Objects;


public class Unique {
    private StringBuilder dataForOutFile = new StringBuilder();//строки для помещения в файл
    private ArrayList<String> allLines = new ArrayList<>();//список для считаывания строк из файла

    /**
     * Отдельный метод реализующий ввод данных из файла или из консоли
     */
    public void inputFileData(String input) throws FileNotFoundException {
        try {
            //добавление всех строк файла в массив для последующего сравнения
            Scanner fileInput = new Scanner(new File(input));
            while (fileInput.hasNextLine()) {
                allLines.add(fileInput.nextLine());
            }
        } catch (NullPointerException ex) {//ввод с консоли если файл не сущетсвует
            Scanner scanner = new Scanner(System.in);
            String inputData = scanner.nextLine();
            allLines.addAll(Arrays.asList(inputData.split(" ")));

        } catch (FileNotFoundException ex) {//исключение, если в названии файла допущена ошибка
            System.out.println("There is an error in the file name or it is located in a different directory");
            throw new FileNotFoundException();
        }
    }

    /**
     * Отдельный метод реализующий вывод данных в файл или в консоль
     */
    public void outputFileData(String output) throws IOException {
        try {
            File file = new File(output);//проверка существования файла без создания нового
            if (!file.exists()) {
                System.out.println("There is an error in the file name or it is located in a different directory");
                throw new FileNotFoundException();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(output);
            fileOutputStream.write(dataForOutFile.toString().getBytes());

        } catch (NullPointerException ex) {//вывод в консоль если названия файла нет
            System.out.print(dataForOutFile);
        }
    }

    /**
     * Сравнение игнорируя регистр
     */
    public void equalsIgnoreCase() {
        StringBuilder firstWord = new StringBuilder(allLines.get(0));
        for (int i = 1; i < allLines.size(); i++) {
            if (firstWord.toString().equalsIgnoreCase(allLines.get(i))) {
                firstWord.append(allLines.get(i));
            } else if (allLines.get(i).equalsIgnoreCase(allLines.get(i - 1))) {
                firstWord.append(allLines.get(i));
            } else {
                dataForOutFile.append(firstWord).append("\n");
                firstWord.setLength(0);
                firstWord.append(allLines.get(i));
            }
        }
        dataForOutFile.append(firstWord).append("\n");//добавление последнего слова
    }

    /**
     * Сравнение игнорируя первые несколько символов каждой строки
     */
    public void equalsIgnoreSomeChars(int countIgnore) {
        HashSet<String> set = new HashSet<>();
        StringBuilder firstWord = new StringBuilder(allLines.get(0));

        for (int i = 1; i < allLines.size(); i++) {
            String wordWithIgnore = firstWord.substring(countIgnore, allLines.get(i - 1).length());//выделение подстроки
            if (wordWithIgnore.equals(allLines.get(i).substring(countIgnore))) {
                set.add(wordWithIgnore);
                firstWord.setLength(0);
                firstWord.append(allLines.get(i));
            } else if (allLines.get(i).equals(allLines.get(i - 1))) {
                firstWord.append(allLines.get(i));
            } else {
                if (!set.isEmpty()) {
                    dataForOutFile.append(set.iterator().next()).append("\n");
                    set.clear();
                } else {
                    dataForOutFile.append(firstWord).append("\n");
                }
                firstWord.setLength(0);
                firstWord.append(allLines.get(i));
            }
        }
        dataForOutFile.append(allLines.get(allLines.size() - 1).substring(countIgnore));
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
        HashSet<String> set = new HashSet<>();
        int count = 1;
        for (int i = 0; i < allLines.size(); i++) {
            for (int j = i + 1; j < allLines.size(); j++) {
                if (allLines.get(i).equals(allLines.get(j))) {
                    set.add(allLines.get(j));
                    count++;
                }
            }
            if (!set.isEmpty()) {
                dataForOutFile.append(count).append(" ").append(allLines.get(i)).append("\n");
                i += count - 1;
                count = 1;
                set.clear();
            } else
                dataForOutFile.append(allLines.get(i)).append("\n");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unique unique = (Unique) o;
        return Objects.equals(dataForOutFile, unique.dataForOutFile) &&
                Objects.equals(allLines, unique.allLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataForOutFile, allLines);
    }

    @Override
    public String toString() {
        return "Unique{" +
                "dataForOutFile=" + dataForOutFile +
                ", allLines=" + allLines +
                '}';
    }
}
