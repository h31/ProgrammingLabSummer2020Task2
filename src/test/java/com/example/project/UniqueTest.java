package com.example.project;

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
    void equalsIgnoreCase() throws IOException {
        Unique.main(new String[]{"-i", "-o", "output.txt", "inputForIgnoreCase.txt"});

        readOutputFile();

        assertEquals("Как делаКАК ДЕЛА\n" + "ПриветприВет\n" + "Хорошо\n",
                stringBuilderTest.toString());

    }

    @Test
    void equalsIgnoreSomeCharsString() throws IOException {
        Unique.main(new String[]{"-s", "3", "-o", "output.txt", "inputForIgnoreSomeChars.txt"});

        readOutputFile();

        assertEquals("Привет\n" +
                "Хорошо\n" + "Нормально\n", stringBuilderTest.toString());

    }

    @Test
    void equalsUniqueString() throws IOException {
        Unique.main(new String[]{"-u", "-o", "output.txt", "inputForEqualsUnique.txt"});

        readOutputFile();

        assertEquals("go\n" +
                "next\n", stringBuilderTest.toString());

    }

    @Test
    void equalsWithCountString() throws IOException {
        Unique.main(new String[]{"-c", "-o", "output.txt", "inputForCountString.txt"});

        readOutputFile();

        assertEquals("2 hello world\n" +
                "hello\n" + "3 i am a text\n" + "go\n", stringBuilderTest.toString());
    }

    @Test
    void exceptionInput() {
        assertThrows(FileNotFoundException.class, () -> Unique.main(new String[]{"-c", "-o", "output.txt", "input.txt"}));
    }

    @Test
    void exceptionOutput() {
        assertThrows(FileNotFoundException.class, () -> Unique.main(new String[]{"-c", "-o", "outpu123t.txt", "inputForCountString.txt"}));
    }

    @Test
    void exceptionKeysNotExist() {
        assertThrows(IllegalArgumentException.class, () -> Unique.main(new String[]{"-o", "output.txt", "inputForCountString.txt"}));
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
        Unique.main(new String[]{"-c", "inputForCountString.txt"});

        assertEquals("2 hello world\n" +
                "hello\n" +
                "3 i am a text\n" +
                "go\n", outContent.toString());
        outContent.close();
    }

    @Test
    public void outputConsoleUnique() throws IOException {
        Unique.main(new String[]{"-u", "inputForEqualsUnique.txt"});

        assertEquals("go\n" + "next\n"
                , outContent.toString());
    }

    @Test
    public void outputConsoleIgnoreSomeChars() throws IOException {
        Unique.main(new String[]{"-s", "3", "inputForIgnoreSomeChars.txt"});

        assertEquals("Привет\n" +
                        "Хорошо\n" +
                        "Нормально"
                , outContent.toString());
    }

    @Test
    public void outputConsoleIgnoreCase() throws IOException {
        Unique.main(new String[]{"-i", "inputForIgnoreCase.txt"});

        assertEquals("Как делаКАК ДЕЛА\n" +
                        "ПриветприВет\n" +
                        "Хорошо\n"
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

        Unique.main(new String[]{"-i", "-o", "output.txt"});
        readOutputFile();

        assertEquals("HelloHELLO\n", stringBuilderTest.toString());

    }

    @Test
    void inputConsoleUniqueString() throws IOException {
        System.setIn(inputContentUniqueString);

        Unique.main(new String[]{"-u", "-o", "output.txt"});
        readOutputFile();

        assertEquals("go\n", stringBuilderTest.toString());
    }

    @Test
    void inputConsoleCountString() throws IOException {
        System.setIn(inputContentCountString);

        Unique.main(new String[]{"-c", "-o", "output.txt"});
        readOutputFile();

        assertEquals("2 helloWorld\n" +
                "go\n" +
                "2 next\n", stringBuilderTest.toString());
    }

    @Test
    void inputConsoleIgnoreSomeChars() throws IOException {
        System.setIn(inputContentIgnoreSomeChars);

        Unique.main(new String[]{"-s", "3", "-o", "output.txt"});
        readOutputFile();

        assertEquals("Hello\n" +
                "World\n", stringBuilderTest.toString());
    }

    @AfterAll
    static void restoreStreamsInput() {
        System.setIn(System.in);
    }

    @AfterEach
    void restoreStreamsOutput() {
        System.setOut(System.out);
    }

}