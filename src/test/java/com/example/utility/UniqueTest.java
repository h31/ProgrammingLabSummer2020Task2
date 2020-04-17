package com.example.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UniqueTest {

    void assertFileContent(String expected) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("output.txt"));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();

        assertEquals(expected, stringBuilder.toString());
    }

    /**
     * Тестирование методов с входными и выходными файлами
     */
    @Test
    void equalsIgnoreCaseString() throws IOException {
        UniqueLauncher.main(new String[]{"-i", "-o", "output.txt", "inputForIgnoreCase.txt"});

        assertFileContent("how are you\n" + "Hello\n" + "Good\n");
    }

    @Test
    void equalsIgnoreSomeCharsString() throws IOException {
        UniqueLauncher.main(new String[]{"-s", "3", "-o", "output.txt", "inputForIgnoreSomeChars.txt"});

        assertFileContent("aaaHello\n" + "hhhLets\n" + "zzzGO\n" + "tttWalk\n");
    }

    @Test
    void equalsUniqueString() throws IOException {
        UniqueLauncher.main(new String[]{"-u", "-o", "output.txt", "inputForEqualsUnique.txt"});

        assertFileContent("go\n" + "next\n");
    }

    @Test
    void equalsWithCountString() throws IOException {
        UniqueLauncher.main(new String[]{"-c", "-o", "output.txt", "inputForCountString.txt"});

        assertFileContent("2 hello world\n" + "1 hello\n" + "2 i am a text\n" + "1 go\n");
    }

    @Test
    void equalsDefault() throws IOException {
        UniqueLauncher.main(new String[]{"-o", "output.txt", "inputForDefault.txt"});

        assertFileContent("hello\n" + "Hello\n" + "Go\n" + "go\n" + "Walk\n");
    }

    /**
     * Тестирование случаев неправильного использования
     */
    private ByteArrayOutputStream outException = new ByteArrayOutputStream();
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final ByteArrayInputStream inputContentIgnoreCase = new ByteArrayInputStream(("Hello\\\\nHELLO\\\\nHow are you\\\\nhow are you").getBytes());
    private final ByteArrayInputStream inputContentUniqueString = new ByteArrayInputStream("hello\\\\nhello\\\\ngo\\\\nman\\\\nman".getBytes());
    private final ByteArrayInputStream inputContentCountString = new ByteArrayInputStream(("helloWorld\\\\nhelloWorld\\\\ngo\\\\nnext\\\\nnext").getBytes());
    private final ByteArrayInputStream inputContentIgnoreSomeChars = new ByteArrayInputStream("aaaHello\\\\nbbbHello\\\\ncccHello\\\\ndddWorld".getBytes());

    @BeforeEach
    void setUp() {
        System.setErr(new PrintStream(outException));
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void exceptionInput() throws IOException {
        UniqueLauncher.main(new String[]{"-c", "-o", "output.txt", "input.txt"});

        assertEquals("File not found: input.txt", outException.toString());
    }


    @Test
    void exceptionOutput() throws IOException {
        UniqueLauncher.main(new String[]{"-c", "-o", "outpu123t.txt", "inputForCountString.txt"});

        assertEquals("File not found: outpu123t.txt", outException.toString());
    }

    /**
     * Тестирование с входными или выходными данными из консоли
     */

    @Test
    void inputConsoleUniqueString() throws IOException {
        System.setIn(inputContentUniqueString);
        UniqueLauncher.main(new String[]{"-u", "-o", "output.txt"});

        assertFileContent("go\n");
    }

    @Test
    void outputConsoleUniqueString() throws IOException {
        UniqueLauncher.main(new String[]{"-u", "inputForEqualsUnique.txt"});

        assertEquals("go\n" + "next\n"
                , outContent.toString());
    }

    @Test
    void inputConsoleCountString() throws IOException {
        System.setIn(inputContentCountString);
        UniqueLauncher.main(new String[]{"-c", "-o", "output.txt"});

        assertFileContent("2 helloWorld\n" +
                "1 go\n" +
                "2 next\n");
    }

    @Test
    void outputConsoleCountString() throws IOException {
        UniqueLauncher.main(new String[]{"-c", "inputForCountString.txt"});

        assertEquals("2 hello world\n" +
                "1 hello\n" +
                "2 i am a text\n" +
                "1 go\n", outContent.toString());
    }

    @Test
    void inputConsoleIgnoreCase() throws IOException {
        System.setIn(inputContentIgnoreCase);
        UniqueLauncher.main(new String[]{"-i", "-o", "output.txt"});

        assertFileContent("Hello\n" + "How are you\n");
    }

    @Test
    void outputConsoleIgnoreCase() throws IOException {
        UniqueLauncher.main(new String[]{"-i", "inputForIgnoreCase.txt"});

        assertEquals("how are you\n" +
                        "Hello\n" +
                        "Good"
                , outContent.toString());
    }

    @Test
    void inputConsoleIgnoreSomeChars() throws IOException {
        System.setIn(inputContentIgnoreSomeChars);
        UniqueLauncher.main(new String[]{"-s", "3", "-o", "output.txt"});

        assertFileContent("aaaHello\n" +
                "dddWorld\n");
    }

    @Test
    void outputConsoleIgnoreSomeChars() throws IOException {
        UniqueLauncher.main(new String[]{"-s", "3", "inputForIgnoreSomeChars.txt"});

        assertEquals("aaaHello\n" + "hhhLets\n" +
                        "zzzGO\n" +
                        "tttWalk"
                , outContent.toString());
    }

}
