package com.example.project;

//библиотека args4j для обработки командной строки
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Objects;

public class Unique {
    private StringBuilder dataForOutFile = new StringBuilder();//строки для помещения в файл
    private ArrayList<String> allLines = new ArrayList<>();//список для считаывания строк из файла

    //Опции для ключенй
    @Option(name = "-i", metaVar = "IgnoreCase", usage = "Equals with IgnoreCase")
    private boolean flagIisSet;

    @Option(name = "-o", metaVar = "OutputFile", usage = "name of output file")
    private String nameOfOutput;

    @Option(name = "-s", metaVar = "IgnoreNumString", usage = "Ignore begin 'num' string")
    private int num;

    @Option(name = "-c", metaVar = "CountString", usage = "Write counted string")
    private boolean flagCisSet;

    @Option(name = "-u", metaVar = "OnlyUnique", usage = "Write only uniques string")
    private boolean flagUisSet;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String nameOfInput;

    /**
     * Отдельный метод реализующий ввод данных из файла или из консоли
     */
    public void inputFileData() throws FileNotFoundException {
        try {
            //добавление все строк файла в массив для последующего сравнения
            Scanner fileInput = new Scanner(new File(nameOfInput));
            while (fileInput.hasNextLine()) {
                allLines.add(fileInput.nextLine());
            }
        } catch (NullPointerException ex) {//ввод с консоли если файл не сущетсвует
            Scanner scanner = new Scanner(System.in);
            String inputData = scanner.nextLine();
            allLines.addAll(Arrays.asList(inputData.split(" ")));

        } catch (FileNotFoundException ex) {//исключение, если в названии файла допущена ошибка
            System.out.println("Files name have mistake");
            throw new FileNotFoundException();
        }
    }

    /**
     * Отдельный метод реализующий вывод данных в файл или в консоль
     */
    public void outputFileData() throws IOException {
        try {
            File file = new File(nameOfOutput);//проверка существования файла без создания нового
            if (!file.exists()){
                throw new FileNotFoundException();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(nameOfOutput);
            fileOutputStream.write(dataForOutFile.toString().getBytes());

        } catch (NullPointerException ex) {//вывод в консоль если названия файла нет
            System.out.print(dataForOutFile);
        }
    }

    /**
     * Сравнение игнорируя регистр
     */
    public void equalsIgnoreCase() throws IOException {
        inputFileData();

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

        outputFileData();
    }

    /**
     * Сравнение игнорируя первые несколько символов каждой строки
     */
    public void equalsIgnoreSomeChars() throws IOException {
        inputFileData();

        HashSet<String> set = new HashSet<>();
        StringBuilder firstWord = new StringBuilder(allLines.get(0));

        for (int i = 1; i < allLines.size(); i++) {
            String wordWithIgnore = firstWord.substring(num, allLines.get(i - 1).length());//выделение подстроки
            if (wordWithIgnore.equals(allLines.get(i).substring(num))) {
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
        dataForOutFile.append(allLines.get(allLines.size() - 1).substring(num));

        outputFileData();
    }

    /**
     * Метод возвращающий только уникальные строки
     */
    public void equalsUnique() throws IOException {
        inputFileData();

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
        outputFileData();
    }

    /**
     * Метод добавляющий количество замененных строк
     */
    public void equalsWithCountString() throws IOException {
        inputFileData();

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

        outputFileData();
    }

    public static void main(String[] args) throws IOException {
        new Unique().launch(args);
    }

    /**
     * Обработка элементов командной строки
     */
    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());

            parser.printUsage(System.err);
            return;
        }

        //проверка ключенй на существование
        if (flagCisSet) {
            equalsWithCountString();
        } else if (flagUisSet) {
            equalsUnique();
        } else if (flagIisSet) {
            equalsIgnoreCase();
        }
        //опция -s имеет параметр типа int,поэтому проверка на существование в виде листа
        else if (Arrays.asList(args).contains("-s")) {
            equalsIgnoreSomeChars();
        } else {
            System.out.println("No key was found");
            throw new IllegalArgumentException();
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unique unique = (Unique) o;
        return flagIisSet == unique.flagIisSet &&
                num == unique.num &&
                flagCisSet == unique.flagCisSet &&
                flagUisSet == unique.flagUisSet &&
                Objects.equals(nameOfOutput, unique.nameOfOutput) &&
                Objects.equals(nameOfInput, unique.nameOfInput) &&
                Objects.equals(allLines, unique.allLines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataForOutFile, flagIisSet, nameOfOutput, num, flagCisSet, flagUisSet, nameOfInput, allLines);
    }

    @Override
    public String toString() {
        return "Unique{" +
                "dataForOutFile=" + dataForOutFile +
                ", flagIisSet=" + flagIisSet +
                ", nameOfOutput='" + nameOfOutput + '\'' +
                ", countIgnore=" + num +
                ", flagCisSet=" + flagCisSet +
                ", flagUisSet=" + flagUisSet +
                ", nameOfInput='" + nameOfInput + '\'' +
                ", allLines=" + allLines +
                '}';
    }
}
