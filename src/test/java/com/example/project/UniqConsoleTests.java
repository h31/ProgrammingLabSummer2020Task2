package com.example.project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class UniqConsoleTests {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setOutContent() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    void setOutDefault() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    void setInContent(String data) {
        input = new ByteArrayInputStream(data.getBytes());
        System.setIn(input);
    }

    @Test
    void voidArgs() throws IOException {
        final String content = "";
        setInContent(content);

        UniqMain.main(new String[0]);

        assertEquals("", output.toString());
    }

    @Test
    void outputOnTheConsole() throws IOException {
        UniqMain.main(new String[]{"files/input.txt", "-i", "-s", "2"});

        final String expected = "А роза упала на лапу Азора\n" +
                "  А роза упала на лапу Азора\n" +
                "А роза упала на лапу Азора\n" +
                "Съешь еще этих мягких французских булок, да выпей чай\n" +
                "а роза упала на лапу азора\n" +
                "Съешь еще этих мягких французских булок, да выпей чай";

        assertEquals(expected, output.toString());
    }

    @Test
    void inputInConsole() throws IOException {
        final String content = "hello\nhello";
        setInContent(content);

        UniqMain.main(new String[]{"-o", "files/output.txt"});
        final String expected = "hello";

        UniqTests.assertFileContent("files/output.txt", expected);
        new File("files/output.txt").delete();
    }

    @Test
    void inputAndOutputInConsole() throws IOException {
        final String content = "hello world\nHELLO WORLD";
        setInContent(content);

        UniqMain.main(new String[]{"-i"});
        final String expected = "hello world";

        assertEquals(expected, output.toString());
    }
}
