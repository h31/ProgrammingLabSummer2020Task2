package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class OptionsTests {

    @Test
    void voidFlags() {
        UniqOptions options = new UniqOptions(new String[]{});
        assertNull(options.inputName);
        assertNull(options.outputName);
        assertEquals(0, options.num);
    }

    @Test
    void names() {
        UniqOptions options = new UniqOptions(new String[] {"-o", "output.txt", "input.txt", "-s", "4"});
        assertEquals("output.txt", options.outputName);
        assertEquals("input.txt", options.inputName);
        assertEquals(4, options.num);
    }

    @Test
    void errorUnexpectedString() {
        ByteArrayOutputStream outActual = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(outActual));

        String outExpected = "Unable to parse command-line options: \"output\" is not a valid value for \"-s\"\r\nTry --help\r\n";

        UniqOptions options = new UniqOptions(new String[] {"-s", "output"});
        assertEquals(outExpected, outActual.toString());

        System.setOut(original);
    }

    @Test
    void errorNoArgument() {
        ByteArrayOutputStream outActual = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(outActual));

        String outExpected1 = "Unable to parse command-line options: Option \"-s\" takes an operand\r\nTry --help\r\n";

        UniqOptions options = new UniqOptions(new String[] {"-s"});
        assertEquals(outExpected1, outActual.toString());

        System.setOut(original);
    }
}
