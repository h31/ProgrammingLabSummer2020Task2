package com.example.project;

import com.sun.tools.javac.comp.Todo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@SuppressWarnings("WeakerAccess")

public class Tailer {

    private final Integer lastSymbols;

    private final Integer lastStrings;

    public Tailer(Integer lastSymbols, Integer lastStrings) {
        this.lastSymbols = lastSymbols;
        this.lastStrings = lastStrings;
    }

    public int tail(BufferedReader reader, BufferedWriter writer) throws IOException {
        if (lastStrings != null) {
            //TODO
        } else if (lastSymbols != null) {
            //TODO
        } else {
            //TODO
        }
        return 0; //temporary
    }

    public int tail(String[] inputNames, String outputName) throws IOException {
        BufferedWriter writer;
        if (outputName == null) {
            writer = new BufferedWriter(new OutputStreamWriter(System.out));
        } else {
            writer = Files.newBufferedWriter(Paths.get(outputName));
        }

        BufferedReader reader;
        if (inputNames.length > 1) {
            for (String inputName : inputNames) {
                reader = Files.newBufferedReader(Paths.get(inputName));
                writer.write(new File(inputName).getName());
                writer.newLine();
                return tail(reader, writer);
            }
        } else if (inputNames.length == 1) {
            reader = Files.newBufferedReader(Paths.get(inputNames[0]));
            return tail(reader, writer);
        } else {
            // Когда считывать с консоли
            //TODO
        }

        return 0; //temporary
    }
}
