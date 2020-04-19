package com.example.project;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Transpose {
    private final String num;
    private final boolean trim;
    private final boolean right;

    public Transpose(String num, boolean trim, boolean right) {
        this.num = num;
        this.trim = trim;
        this.right = right;
    }

    public void transposes(BufferedReader reader, BufferedWriter writer) throws IOException {
        int number = -1;
        if (trim || right) { number = 10; }
        if (num != null) { number = Integer.parseInt(num); }
        ArrayList<String> outputText = new ArrayList<>();
        String word = "";
        String line = reader.readLine();
        while (line!=null) {
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                word = words[i];
                int wordLength = word.length();
                if (wordLength < number && !right)
                    for (int j = 0; j < number - wordLength; j++) {word += " ";}
                else if (wordLength < number && right)
                    for (int j = 0; j < number - wordLength; j++) {word =  " "+ word;}
                else if (wordLength > number && right && trim)
                    word = word.substring(wordLength-number);
                else if (wordLength > number && !right && trim)
                    word = word.substring(0, number);
                if (i < outputText.size()) {
                    outputText.set(i, outputText.get(i) + " " + word);
                } else outputText.add(i, word);
            }
            line = reader.readLine();
        }
        for (String lines:outputText) writer.write(lines + "\n");
    }

    public void transposes(String file, String ofile) throws IOException {
        BufferedReader reader;
        if (file == null) reader = new BufferedReader(new InputStreamReader(System.in));
        else {reader = new BufferedReader(new FileReader(file));}
        BufferedWriter writer;
        if (ofile == null) {writer = new BufferedWriter(new OutputStreamWriter(System.out));}
        else { writer = new BufferedWriter(new FileWriter(ofile));}
        transposes(reader, writer);
        reader.close();
        writer.close();
    }
}
