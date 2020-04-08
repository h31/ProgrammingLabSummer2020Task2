package com.example.project;

import com.sun.tools.javac.comp.Todo;

import java.io.*;

@SuppressWarnings("WeakerAccess")

public class Tailer {

    private final Integer lastSymbols;

    private final Integer lastStrings;

    public Tailer(Integer lastSymbols, Integer lastStrings) {
        this.lastSymbols = lastSymbols;
        this.lastStrings = lastStrings;
    }

    public int tail(InputStream in, OutputStream out) throws IOException {
        if (lastSymbols != null) {
            return 0;
        } else if (lastStrings != null) {
            return 0;
        } else {
            return 0;
        }
    }

    public int tail(String[] inputNames, String outputName) throws IOException {
        /*
          Учесть факт того, что файлов м. б. несколько, или не быть вовсе
         */
        try (FileInputStream inputStream = new FileInputStream(inputNames[0])) {
            try (FileOutputStream outputStream = new FileOutputStream(outputName)) {
                return tail(inputStream, outputStream);
            }
        }
    }
}
