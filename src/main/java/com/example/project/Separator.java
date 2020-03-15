package com.example.project;

import java.io.*;

public class Separator {
    /**
     * метод, который разархивирует файл
     *
     * @param fileName - файл, который надо разархивировать.
     * @throws IOException - исключение в случае если файл не найден
     */
    static void fileReader(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        FileWriter newFiles;
        String line = reader.readLine();
        while (line != null) {
            newFiles = new FileWriter(line);
            int k = Integer.parseInt(reader.readLine());
            while (k > 0) {
                char add = (char) reader.read();
                if (add == '\n') k--;
                newFiles.write(add);
            }
            char add = (char) reader.read();
            if (add == '\r') {
                newFiles.write(add);
                reader.readLine();
            }
            line = reader.readLine();
            newFiles.close();
        }
        reader.close();
    }
}
