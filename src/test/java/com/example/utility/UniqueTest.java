package com.example.utility;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UniqueTest {
    StringBuilder stringBuilderTest = new StringBuilder();
    File file = new File("output.txt");

    void readOutputFile() throws FileNotFoundException {
        Scanner fileInput = new Scanner(file);
        while (fileInput.hasNextLine()) {
            stringBuilderTest.append(fileInput.nextLine()).append("\n");
        }
    }

    @Test
    void equalsIgnoreCaseString() throws IOException {
        UniqueLauncher.main(new String[]{"-i", "-o", "output.txt", "inputForIgnoreCase.txt"});
        readOutputFile();

        assertEquals("how are youHOW ARE YOU\n" +
                        "HelloHELLO\n" +
                        "Good\n",
                stringBuilderTest.toString());
    }

    @Test
    void equalsIgnoreSomeCharsString() throws IOException {
        UniqueLauncher.main(new String[]{"-s", "3", "-o", "output.txt", "inputForIgnoreSomeChars.txt"});

        readOutputFile();

        assertEquals("Hello\n" +
                "GO\n" +
                "Walk\n", stringBuilderTest.toString());
    }

    @Test
    void equalsUniqueString() throws IOException {
        UniqueLauncher.main(new String[]{"-u", "-o", "output.txt", "inputForEqualsUnique.txt"});

        readOutputFile();

        assertEquals("go\n" +
                "next\n", stringBuilderTest.toString());
    }

    @Test
    void equalsWithCountString() throws IOException {
        UniqueLauncher.main(new String[]{"-c", "-o", "output.txt", "inputForCountString.txt"});

        readOutputFile();

        assertEquals("2 hello world\n" +
                "hello\n" + "2 i am a text\n" + "go\n", stringBuilderTest.toString());
    }

    @Test
    void exceptionInput() {
        assertThrows(FileNotFoundException.class, () -> UniqueLauncher.main(new String[]{"-c", "-o", "output.txt", "input.txt"}));
    }

    @Test
    void exceptionOutput() {
        assertThrows(FileNotFoundException.class, () -> UniqueLauncher.main(new String[]{"-c", "-o", "outpu123t.txt", "inputForCountString.txt"}));
    }

    @Test
    void exceptionKeysNotExist() {
        assertThrows(IllegalArgumentException.class, () -> UniqueLauncher.main(new String[]{"-o", "output.txt", "inputForCountString.txt"}));
    }


    /**
     * Тестирование вывода в консоль с перенаправлением потока
     */
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreamsOutput() {
        outContent = new ByteArrayOutputStream();//обновление потока вывода для каждого теста
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void outputConsoleCountString() throws IOException {
        UniqueLauncher.main(new String[]{"-c", "inputForCountString.txt"});

        assertEquals("2 hello world\n" +
                "hello\n" +
                "2 i am a text\n" +
                "go\n", outContent.toString());
        outContent.close();
    }

    @Test
    public void outputConsoleUnique() throws IOException {
        UniqueLauncher.main(new String[]{"-u", "inputForEqualsUnique.txt"});

        assertEquals("go\n" + "next\n"
                , outContent.toString());
    }

    @Test
    public void outputConsoleIgnoreSomeChars() throws IOException {
        UniqueLauncher.main(new String[]{"-s", "3", "inputForIgnoreSomeChars.txt"});

        assertEquals("Hello\n" +
                        "GO\n" +
                        "Walk"
                , outContent.toString());
    }

    @Test
    public void outputConsoleIgnoreCase() throws IOException {
        UniqueLauncher.main(new String[]{"-i", "inputForIgnoreCase.txt"});

        assertEquals("how are youHOW ARE YOU\n" +
                        "HelloHELLO\n" +
                        "Good\n"
                , outContent.toString());
    }

    /**
     * Тестирование ввода в консоль
     */
    private ByteArrayInputStream inputContentIgnoreCase = new ByteArrayInputStream(("Hello HELLO").getBytes());
    private ByteArrayInputStream inputContentUniqueString = new ByteArrayInputStream("hello hello go man man".getBytes());
    private ByteArrayInputStream inputContentCountString = new ByteArrayInputStream("helloWorld helloWorld go next next".getBytes());
    private ByteArrayInputStream inputContentIgnoreSomeChars = new ByteArrayInputStream("qweHello qrtHello qedHello qqeWorld".getBytes());

    @Test
    void inputConsoleIgnoreCase() throws IOException {
        System.setIn(inputContentIgnoreCase);

        UniqueLauncher.main(new String[]{"-i", "-o", "output.txt"});
        readOutputFile();

        assertEquals("HelloHELLO\n", stringBuilderTest.toString());

    }

    @Test
    void inputConsoleUniqueString() throws IOException {
        System.setIn(inputContentUniqueString);

        UniqueLauncher.main(new String[]{"-u", "-o", "output.txt"});
        readOutputFile();

        assertEquals("go\n", stringBuilderTest.toString());
    }

    @Test
    void inputConsoleCountString() throws IOException {
        System.setIn(inputContentCountString);

        UniqueLauncher.main(new String[]{"-c", "-o", "output.txt"});
        readOutputFile();

        assertEquals("2 helloWorld\n" +
                "go\n" +
                "2 next\n", stringBuilderTest.toString());
    }

    @Test
    void inputConsoleIgnoreSomeChars() throws IOException {
        System.setIn(inputContentIgnoreSomeChars);

        UniqueLauncher.main(new String[]{"-s", "3", "-o", "output.txt"});
        readOutputFile();

        assertEquals("Hello\n" +
                "World\n", stringBuilderTest.toString());
    }

    /**
     * Возвращение исходных потоков
     */
    @AfterAll
    static void restoreStreamsInput() {
        System.setIn(System.in);
    }

    @AfterEach
    void restoreStreamsOutput() {
        System.setOut(System.out);
    }

}