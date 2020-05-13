package com.example.project;


import java.io.*;
import java.util.ArrayList;

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
        StringBuilder word;
        String line = reader.readLine();
        while (line != null) {
            String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                word = new StringBuilder(words[i]);
                int wordLength = word.length();
                int missingChars = number - wordLength;
                if (0 < missingChars && !right)
                    for (int j = 0; j < missingChars; j++) {
                        word.append(" ");}
                if (0 < missingChars && right)
                    for (int j = 0; j < missingChars; j++) {
                        word.insert(0, " ");}
                if (0 > missingChars && right && trim)
                    word = new StringBuilder(word.substring(-missingChars));
                if (0 > missingChars && !right && trim)
                    word = new StringBuilder(word.substring(0, number));
                if (i < outputText.size()) {
                    outputText.set(i, outputText.get(i) + " " + word);
                } else outputText.add(word.toString());
            }
            line = reader.readLine();
        }
        for (String lines:outputText) writer.write(lines + "\n");
    }

    public void transposes(String file, String ofile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        if (file != null) reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        if (ofile != null) {writer = new BufferedWriter(new FileWriter(ofile));}
        transposes(reader, writer);
        reader.close();
        writer.close();
    }
}
