package com.example.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BlackBoxTarTests {
    //тесты для проверки правильности выбрасывания исключений
    @Test
    void illegalArguments() {
        Assertions.assertThrows(FileNotFoundException.class, () -> Separator.fileReader("abc"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Compressor.fileWriter(new ArrayList<>(), "test.txt"));
    }

    //проверка правильности разархивирования файлов
    @Test
    void fileReaderTests() throws FileNotFoundException {
        Separator.fileReader("input/read.txt");
        Assertions.assertTrue(new File("file0.txt").exists());
        Assertions.assertTrue(new File("file1.txt").exists());
        Assertions.assertTrue(new File("file2.txt").exists());
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
    }

    //проверка правильности архивации файлов
    @Test
    void fileWriterTests() throws FileNotFoundException {
        Compressor.fileWriter(new ArrayList<String>() {{
            add("input/file1.txt");
            add("input/file2.txt");
        }}, "test1.txt");
        Assertions.assertTrue(new File("test1.txt").exists());
        Compressor.fileWriter(new ArrayList<String>() {{
            add("input/file1.txt");
        }}, "test2.txt");
        Assertions.assertTrue(new File("test2.txt").exists());
        new File("test1.txt").delete();
        new File("test2.txt").delete();
    }
}
