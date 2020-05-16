package com.example.project;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

 public class Split {

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

    String nameFiles(String[] args, String input) {
        int flagO = Arrays.asList(args).indexOf("-o");
        String name = "x";
        if (flagO != -1) {
            name = args[flagO + 1];
        }
        if (name.equals("-")) {
            int index = input.lastIndexOf("/");
            name = input.substring(index + 1);
            index = name.lastIndexOf(".");
            if (index == 0)
                return name;
            if (index != -1)
                name = name.substring(0,index);
        }
        return name;
    }

    ArrayList<String> outputFiles(String[] args, int countFile, String name) {
        int flagD = Arrays.asList(args).indexOf("-d");
        ArrayList<String> outputFiles = new ArrayList<>();
        String[] letters = new String[]
                {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        if (flagD != -1) {
            int i = 1;
            while (countFile > 0) {
                outputFiles.add(name + i + ".txt");
                i++;
                countFile--;
            }
        } else {
            int i = 0;
            int j = 0;
            while (countFile > 0) {
                if (i > 25) {
                    i = 0;
                    j++;
                }
                if (j > 25) {
                    System.out.println("Превышено количество выходных файлов");
                    throw new ArrayIndexOutOfBoundsException();
                }
                outputFiles.add(name + letters[j] + letters[i] + ".txt");
                i++;
                countFile--;
            }
        }
        return outputFiles;
    }

    ArrayList<String> reader(String input) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(input));
        String str;
        ArrayList<String> list = new ArrayList<String>();
        while((str = reader.readLine()) != null ){
            if(!str.isEmpty()){
                list.add(str);
            }
        }
        reader.close();
        return list;
    }

    void write(ArrayList<String> outputFiles, ArrayList<String> list, int size, int sizeParent, int flagC) {
        File filePath = new File("output");
        filePath.mkdir();
        int countSize = 0;
        int count = 0;
        if (flagC != - 1) {
            int divStr = 0;
            for (int i = 0; i < sizeParent; i++) {
                String str = list.get(i);
                for (int j = 0; j < str.length(); j++) {
                    if (countSize >= size) {
                        count++;
                        countSize = 0;
                        divStr = 0;
                    }
                    countSize++;
                    File file = new File(filePath + "/" + outputFiles.get(count));
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                        String lineSeparator = System.getProperty("line.separator");
                        writer.write(str.charAt(j));
                        divStr++;
                        if (divStr > 100) {
                            writer.write(lineSeparator);
                            countSize++;
                            divStr = 0;
                        }
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else
            for (int i = 0; i < sizeParent; i++) {
                if (countSize >= size) {
                    count++;
                    countSize = 0;
                }
                countSize++;
                File file = new File(filePath + "/" + outputFiles.get(count));
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                    String lineSeparator = System.getProperty("line.separator");
                    writer.write(list.get(i) + lineSeparator);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}

