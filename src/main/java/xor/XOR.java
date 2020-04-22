package xor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class XOR {

    // xor шифрование файла
    public void encodeDecode(String inputFile, String outputFile, String key) {
        String text = readFile(inputFile);
        if (text != null) {
            text = xorMethod(text, key);
            writeFile(outputFile, text);
        }
    }

    // xor шифрование
    String xorMethod(String pText, String pKey) {
        char[] res = new char[pText.length()];
        for (int i = 0; i < pText.length(); i++) {
            res[i] = (char) (pText.charAt(i) ^ pKey.charAt(i % pKey.length()));
        }
        return new String(res);
    }

    // чтение файла
    private String readFile(String nameFile) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(nameFile), StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < lines.size(); i++) {
                stringBuilder.append(lines.get(i));
                if (i < lines.size() - 1) {
                    stringBuilder.append('\n');
                }
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // запись в файл
    private void writeFile(String nameFile, String encodeText) {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(nameFile), StandardCharsets.UTF_8);
            writer.write(encodeText);
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

