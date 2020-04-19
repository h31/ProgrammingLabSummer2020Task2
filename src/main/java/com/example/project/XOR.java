package main.java.com.example.project;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class XOR {

    public static void main(String[] args) throws Exception {
        Scanner lineScan = new Scanner(System.in);
        System.out.println("Введите строку: ");
        String line = lineScan.nextLine();
        String[] subLine = line.split(" ");
        String command = subLine[0];
        char[] key = subLine[1].toCharArray();
        FileReader reader = new FileReader(subLine[2]);
        FileWriter writer = new FileWriter("-o " + subLine[2]);

        if (command.equals("-c") || command.equals("-d")) {
            int c;
            int i = 0;
            while ((c = reader.read()) != -1) {
                if (c >= '0' && c <= '9') {
                    int res = (c ^ key[i % key.length]);
                    writer.write( res + 48);
                    i += 1;
                }else {
                    int res = c ^ key[i % key.length];
                    writer.write((char) res);
                    i += 1;
                }
            }
            writer.flush();
        }
    }

}