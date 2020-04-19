package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.example.project.TransposeLauncher.main;
import static org.junit.jupiter.api.Assertions.*;

class TransposeTest {


    @Test
    void transpose() throws IOException {

        main(("-a 3 -t -r -o src/test/resources/output_1 src/test/resources/input_1").split("\\s+"));
        assertEquals(Files.readAllLines(Paths.get("src/test/resources/expected_1")), Files.readAllLines(Paths.get("src/test/resources/output_1")));

        main("-a 2 -o src/test/resources/output_2 src/test/resources/input_2".split("\\s+"));
        assertEquals(Files.readAllLines(Paths.get("src/test/resources/expected_2")), Files.readAllLines(Paths.get("src/test/resources/output_2")));

        main("-t src/test/resources/input_2".split("\\s+"));

    }
}