package com.example.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.project.Tar.*;


class TarTest {

    //тесты для проверки правильности выбрасывания исключений
    @Test
    void illegalArguments() {
        Assertions.assertThrows(FileNotFoundException.class, () -> fileReader("abc"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> fileWriter(new ArrayList<>(), "test.txt"));
    }

    //тесты для проверки разбиения одного файла на несколько и проверка содержимого
    @Test
    void fileReaderTests() throws FileNotFoundException {
        fileReader("input/read.txt");
        Assertions.assertTrue(new File("file0.txt").exists());
        Assertions.assertTrue(new File("file1.txt").exists());
        Assertions.assertTrue(new File("file2.txt").exists());
        Scanner file0 = new Scanner(new File("file0.txt"));
        Assertions.assertEquals("123", file0.nextLine());
        Assertions.assertNotEquals("123", file0.nextLine());
        Assertions.assertEquals("789", file0.nextLine());
        file0.close();
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
    }

    //тесты для проверки соединения нескольких файлов и проверка содержимого
    @Test
    void fileWriterTests() throws FileNotFoundException {
        fileWriter(new ArrayList<String>(){{add("input/file1.txt"); add("input/file2.txt");}}, "test.txt");
        Assertions.assertTrue(new File("test.txt").exists());
        Scanner test = new Scanner(new File("test.txt"));
        Assertions.assertEquals("input\\file1.txt", test.nextLine());
        Assertions.assertEquals("123", test.nextLine());
        Assertions.assertNotEquals("789", test.nextLine());
        Assertions.assertEquals("789", test.nextLine());
        Assertions.assertEquals("File ended", test.nextLine());
        Assertions.assertEquals("", test.nextLine());
        Assertions.assertEquals("input\\file2.txt", test.nextLine());
        test.close();
        new File("test.txt").delete();
    }
}
