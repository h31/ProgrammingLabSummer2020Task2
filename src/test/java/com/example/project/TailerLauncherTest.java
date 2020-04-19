package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TailerLauncherTest {
    boolean equalToOutput(String expected) throws IOException {
        return Files.readAllLines(Paths.get("test_files/output.txt")).equals(Files.readAllLines(Paths.get(expected)));
    }

    @Test
    void testLastSymbols() throws IOException {
        String[] args = {"-c", "5", "-o", "test_files/output.txt", "test_files/input.txt", "test_files/input1.txt"};
        TailerLauncher.main(args);
        assertTrue(equalToOutput("test_files/expectedSym.txt"));
    }

    @Test
    void testLast5Strings () throws IOException {
        String[] args = {"-n", "5", "-o", "test_files/output.txt", "test_files/input.txt", "test_files/input1.txt"};
        TailerLauncher.main(args);
        assertTrue(equalToOutput("test_files/expected5Str.txt"));
    }

    @Test
    void testLast10Strings () throws IOException {
        String[] args = {"-o", "test_files/output.txt", "test_files/input.txt", "test_files/input1.txt"};
        TailerLauncher.main(args);
        assertTrue(equalToOutput("test_files/expected10Str.txt"));
    }

    @Test
    void testBothFlags () {
        String[] args = {"-n", "3", "-c", "5", "test_files/input.txt"};
        assertThrows(Error.class, () -> TailerLauncher.main(args));
    }
}