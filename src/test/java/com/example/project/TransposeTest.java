package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransposeTest {

    @Test
    void transposes() throws IOException {
        Transpose transpose = new Transpose("3", true, false);
        transpose.transposes("files/input.txt", "files/output1.txt");
        assertEquals(Files.readAllLines(Paths.get("files/output1.txt")),
                new ArrayList(Arrays.asList("Abc k   Rst",
                "ef  lmn v  ",
                "ghi q   w  ",
                "j   x  ",
                "yz ")));

        transpose = new Transpose(null, true, true);
        transpose.transposes("files/input.txt", "files/output2.txt");
        assertEquals(Files.readAllLines(Paths.get("files/output2.txt")),
                new ArrayList(Arrays.asList("      Abcd          k       Rstu",
                        "        ef      lmnop          v",
                        "       ghi          q          w",
                        "         j          x",
                        "        yz")));
    }
}