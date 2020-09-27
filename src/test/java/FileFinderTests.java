import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileFinderTests {
    FileFinder fileFind = new FileFinder();

    File currentDirectory = new File(new File("").getAbsolutePath());
    File testFileOne = new File(currentDirectory + "//directoryForTests//testDirectoryOne", "testFileOne");
    File testFileTwo = new File(currentDirectory + "//directoryForTests//testDirectoryTwo", "testFileTwo");
    File testFileThree = new File(currentDirectory + "//directoryForTests", "testFileThree");
    File Test = new File(currentDirectory, "Test.txt");

    @Test
    public void test1() {
        assertEquals("Файл testFileTwo не найден.",
                fileFind.start(new String[]{"-d", String.valueOf(currentDirectory), "testFileTwo"}));
    }

    @Test
    public void test2() {
        assertEquals("Файл testFileTwo найден." + "\n" + "Путь: " + testFileTwo.getAbsolutePath(),
                fileFind.start(new String[]{"-r", "-d", String.valueOf(currentDirectory), "testFileTwo"}));
    }

    @Test
    public void test3() {
        assertEquals("Файл testFileOne найден." + "\n" + "Путь: " + testFileOne.getAbsolutePath(),
                fileFind.start(new String[]{"-r", "testFileOne"}));
    }

    @Test
    public void test4() {
        assertEquals("Файл testFileThree найден." + "\n" + "Путь: " + testFileThree.getAbsolutePath(),
                fileFind.start(new String[]{"-r", "testFileThree"}));
    }

    @Test
    public void test5() {
        assertEquals("Файл testFileFour не найден.",
                fileFind.start(new String[]{"-r", "-d", String.valueOf(currentDirectory), "testFileFour"}));
    }

    @Test
    public void test6() {
        assertEquals("Файл Test.txt найден." + "\n" + "Путь: " + Test.getAbsolutePath(),
                fileFind.start(new String[]{"-d", String.valueOf(currentDirectory), "Test.txt"}));
    }
}