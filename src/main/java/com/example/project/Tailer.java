package com.example.project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@SuppressWarnings("WeakerAccess")

public class Tailer {

    private final int lastSymbols;

    private final int lastStrings;

    public Tailer(int lastSymbols, int lastStrings) {
        this.lastSymbols = lastSymbols;
        this.lastStrings = lastStrings;
    }

    private int tail(BufferedReader reader, BufferedWriter writer) throws IOException {
        if (lastStrings == 0) {
            return extractSymbols(reader, writer);
        } else {
            return extractStrings(reader, writer);
        }
    }

    //кидают ли исключения?
    private int extractSymbols(BufferedReader reader, BufferedWriter writer) {
        int count = 0;


        return count;
    }

    private int extractStrings(BufferedReader reader, BufferedWriter writer) {
        int count = 0;


        return count;
    }

    public int tail(ArrayList<String> inputNames, String outputName) throws IOException {
        BufferedWriter writer;
        if (outputName == null) {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        } else {
            writer = Files.newBufferedWriter(Paths.get(outputName));
        }

        BufferedReader reader;
        if (inputNames == null) {
            //No input files, console input:
            reader = new BufferedReader(new InputStreamReader(System.in));
        } else if (inputNames.size() == 1) {
            //1 input file:
            reader = Files.newBufferedReader(Paths.get(inputNames.get(0)));
        } else {
            //2 or more input files:
            int tailCounter = 0;
            for (String inputName : inputNames) {
                try (BufferedReader reader1 = Files.newBufferedReader(Paths.get(inputName)); writer) {
                    writer.write(new File(inputName).getName());
                    writer.newLine();
                    tailCounter += tail(reader1, writer);
                }
            }
            return tailCounter;
        }

        try (reader; writer) {
            return tail(reader, writer);
        }
    }
}
