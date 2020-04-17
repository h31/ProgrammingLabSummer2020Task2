package com.example.project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")

public class Tailer {

    private final int lastSymbols;

    private final int lastStrings;

    public Tailer(int lastSymbols, int lastStrings) {
        this.lastSymbols = lastSymbols;
        this.lastStrings = lastStrings;
    }

    private int tail(BufferedReader reader, BufferedWriter writer) throws IOException {
        if (lastSymbols == 0) {
            return extractStrings(reader, writer);
        } else {
            return extractSymbols(reader, writer);
        }
    }

    private int extractSymbols(BufferedReader reader, BufferedWriter writer) throws IOException {
        ArrayDeque<Character> deque = new ArrayDeque<>(lastSymbols);
        int count = 0;
        int sym = reader.read();
        while (sym != -1) {
            if (deque.size() == lastSymbols) deque.pollLast();
            deque.add((char) sym);
            sym = reader.read();
        }

        while (deque.peekFirst() != null) {
            writer.write(String.valueOf(Objects.requireNonNull(deque.pollFirst())));
            count++;
        }
        return count;
    }

    private int extractStrings(BufferedReader reader, BufferedWriter writer) throws IOException {
        ArrayDeque<String> deque = new ArrayDeque<>(lastStrings);
        int count = 0;
        String string = reader.readLine();
        while (string != null) {
            if (deque.size() == lastStrings) deque.pollFirst();
            deque.add(string);
            count++;
            string = reader.readLine();
        }

        while (deque.peekFirst() != null) {
            writer.write(Objects.requireNonNull(deque.pollFirst()));
            if (deque.size() > 0) writer.newLine();
            count++;
        }
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
