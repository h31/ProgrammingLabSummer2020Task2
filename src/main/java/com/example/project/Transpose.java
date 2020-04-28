package com.example.project;

import java.io.*;
import java.util.HashMap;

class Transpose {
    private final String inputName;

    private final String outputName;

    public Transpose(String inputName, String outputName) {
        this.inputName = inputName;
        this.outputName = outputName;
    }

    public void transpose(int a, boolean t, boolean r) throws IOException {
        String iString;
        String inputStrings;
        HashMap<Integer, String> transposeMap = new HashMap();
        BufferedReader reader;
        if (inputName == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        } else {
            reader = new BufferedReader(new FileReader(inputName));
        }
        inputStrings = reader.readLine();
        while (inputStrings != null) {
            String[] words = inputStrings.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                int wordsLength = words[i].length();
                int length = a - wordsLength;
                StringBuilder w = new StringBuilder();
                if (length <= 0) {
                    if (!t) w.append(words[i]);
                    if (t & !r) w.append(words[i], 0, a);
                    if (t & r) w.append(words[i], wordsLength - a, wordsLength);
                } else if (!r) {
                    w.append(words[i]);
                    for (int j = 0; j < length; j++) w.append(" ");
                } else {
                    for (int j = 0; j < length; j++) w.append(" ");
                    w.append(words[i]);
                }
                if (!transposeMap.containsKey(i)) iString = w.toString();
                else
                    iString = String.format("%s %s", transposeMap.get(i), w);
                transposeMap.put(i, iString);
            }
            inputStrings = reader.readLine();
        }
        reader.close();
        BufferedWriter writer;
        if (outputName == null) {
            for (int key : transposeMap.keySet()) {
                System.out.println(transposeMap.get(key));
            }
        } else {
            File file = new File(outputName);
            writer = new BufferedWriter(new FileWriter(file));
            for (int key : transposeMap.keySet()) {
                writer.write(transposeMap.get(key));
                writer.newLine();
            }
            writer.close();
        }
    }
}
