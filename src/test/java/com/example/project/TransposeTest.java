/*package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransposeTest {

    private  void assertFileContent(String name, String expectedContent) {
        File file = new File(name);
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine() + "\n";
            while (line != null) {
                content.append(reader.readLine()).append("\n");
                line = reader.readLine() + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(expectedContent, content.toString());
    }

    @Test
    void transposes() throws IOException {
        Transpose transpose = new Transpose("3", true, false);
        transpose.transposes("files/input.txt", "files/output1.txt");
        assertFileContent("files/output1.txt",
                "Abc k   Rst\n" +
                "ef  lmn v  \n" +
                "ghi q   w  \n" +
                "j   x  \n" +
                "yz ");

        transpose = new Transpose(null, true, true);
        transpose.transposes("files/input.txt", "files/output2.txt");
        assertFileContent("files/output2.txt",
                "      Abcd          k        Rstu\n" +
                        "        ef      lmnop          v\n" +
                        "       ghi          q          w \n" +
                        "         j          x\n" +
                        "        yz");
    }
}*/