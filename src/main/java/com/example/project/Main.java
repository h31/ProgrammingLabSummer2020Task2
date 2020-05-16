package com.example.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        Split split = new Split();
        int flagL = Arrays.asList(args).indexOf("-l");
        int flagC = Arrays.asList(args).indexOf("-c");
        int flagN = Arrays.asList(args).indexOf("-n");
        int flagFile = -1;
        String input = null;
        for (int i = 0; i < args.length; i++)
            if (args[i].matches("[0-9A-Za-z/]+.(txt)")) {
                flagFile = i;
                input = args[flagFile];
                break;
            }
        if (flagFile == -1) {
            System.out.println("Не указан файл");
            throw new IllegalArgumentException();
        }
        ArrayList<String> list = split.reader(input);
        if (!((flagL == flagC) || (flagL == flagN) || (flagC == flagN))) {
            System.out.println("Лишние флаги");
            throw new IllegalArgumentException();
        }
        int size = 100;
        int sizeParent = list.size();
        int countFile = sizeParent / size;
        if (sizeParent % size != 0)
            countFile++;
        if (flagL != -1) {
            size = Integer.parseInt(args[flagL + 1]);
            countFile = sizeParent / size;
            if (sizeParent % size != 0)
                countFile++;
        }
        if (flagC != -1) {
            size = Integer.parseInt(args[flagC + 1]);
            int count = 0;
            for (String s : list) {
                count += s.length();
            }
            if (sizeParent % size == 0)
                countFile = count / size;
            else
                countFile = count / size + 1;
        }
        if (flagN != -1) {
            countFile = Integer.parseInt(args[flagN + 1]);
            if (sizeParent % countFile == 0)
                size = sizeParent / countFile;
            else
                size = sizeParent / countFile + 1;
        }
        if (size <= 0 || countFile <= 0) {
            System.out.println("Числа должны быть больше 0");
            throw new IllegalArgumentException();
        }
        String name = split.nameFiles(args, input);
        ArrayList<String> outputFiles = split.outputFiles(args, countFile, name);
        split.write(outputFiles, list, size, sizeParent, flagC);
    }
}
