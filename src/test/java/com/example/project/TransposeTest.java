package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.project.TransposeLauncher.main;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransposeTest {


    @Test
    void transpose() throws IOException {
        main(("-a 3 -t -r -o src/main/resources/output1 src/main/resources/input1").split("\\s+"));
        assertEquals(Files.readAllLines(Paths.get("src/main/resources/expected1")), Files.readAllLines(Paths.get("src/main/resources/output1")));

        main(("-a 3 -t -o src/main/resources/output2 src/main/resources/input1").split("\\s+"));
        assertEquals(Files.readAllLines(Paths.get("src/main/resources/expected2")), Files.readAllLines(Paths.get("src/main/resources/output2")));

    }
}