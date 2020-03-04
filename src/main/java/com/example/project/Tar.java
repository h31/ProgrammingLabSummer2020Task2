package com.example.project;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
            if (!line.matches("^tar\\s+-u\\s+[^*?|\"<>]+\\.txt$")) {
                System.out.println("Wrong command");
                return;
            }
        } else if (!line.matches("^tar\\s+([^*?|\"<>]+\\.txt)+\\s+-out\\s+[^*:?|\"<>]+\\.txt$")) {
            System.out.println("Wrong command");
            return;
        }
        String[] parts = line.split("\\s+");
        if (parts[1].equals("-u")) Separator.fileReader(parts[2]);
        else {
            while (!parts[k].equals("-out")) {
                filesToConnect.add(parts[k]);
                k++;
            }
            Compressor.fileWriter(filesToConnect, parts[k + 1]);
        }
    }
}