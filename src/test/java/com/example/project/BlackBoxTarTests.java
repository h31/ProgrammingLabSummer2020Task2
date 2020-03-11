package com.example.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BlackBoxTarTests {
    //тесты для проверки правильности выбрасывания исключений
    @Test
    void illegalArguments() {
        Assertions.assertThrows(FileNotFoundException.class, () -> Separator.fileReader("abc"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Compressor.fileWriter(new ArrayList<>(), "test.txt"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Tar.main("file1.txt -out test.txt".split("\\s+")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Tar.main("tar -u -out test.txt".split("\\s+")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Tar.main("tar -u 123".split("\\s+")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Tar.main("tar -u".split("\\s+")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Tar.main("tar -out 123.txt".split("\\s+")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Tar.main("tar 123.txt test1.txt".split("\\s+")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> Tar.main("tar 123.txt test1.txt -out".split("\\s+")));
    }

    //проверка правильности разархивирования файлов
    @Test
    void fileReaderTests() throws IOException {
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
    void fileWriterTests() throws IOException {
        Compressor.fileWriter(new ArrayList<File>() {{
            add(new File("input/part1.txt"));
            add(new File ("input/part2.txt"));
        }}, "test1.txt");
        Assertions.assertTrue(new File("test1.txt").exists());
        Compressor.fileWriter(new ArrayList<File>() {{
            add(new File("input/part1.txt"));
        }}, "test2.txt");
        Assertions.assertTrue(new File("test2.txt").exists());
        new File("test1.txt").delete();
        new File("test2.txt").delete();
    }
}
