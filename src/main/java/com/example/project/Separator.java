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
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))) {
            String line = reader.readLine();
            while (line != null) {
                try (BufferedWriter newFile = new BufferedWriter(new FileWriter(line))) {
                    int k = Integer.parseInt(reader.readLine());
                    for (int i = 0; i < k; i++) {
                        char add = (char) reader.read();
                        newFile.write(add);
                    }
                    line = reader.readLine();
                }
            }
        }
    }
}
