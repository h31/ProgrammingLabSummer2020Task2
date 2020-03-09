package com.example.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WhiteBoxTarTests {
    //тесты для проверки содержимого разархивированных файлов
    @Test
    void fileReaderTests() throws FileNotFoundException {
        Separator.fileReader("input/read.txt");
        Scanner file0 = new Scanner(new File("file0.txt"));
        Assertions.assertEquals("123", file0.nextLine());
        Assertions.assertNotEquals("123", file0.nextLine());
        Assertions.assertEquals("789", file0.nextLine());
        file0.close();
        Scanner file1 = new Scanner(new File("file1.txt"));
        Assertions.assertEquals("123", file1.nextLine());
        Assertions.assertNotEquals("123", file1.nextLine());
        Assertions.assertEquals("789", file1.nextLine());
        file1.close();
        Scanner file2 = new Scanner(new File("file2.txt"));
        Assertions.assertEquals("123", file2.nextLine());
        Assertions.assertNotEquals("123", file2.nextLine());
        Assertions.assertEquals("789", file2.nextLine());
        file2.close();
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
    }

    //тесты для проверки соединения нескольких файлов и проверка содержимого
    @Test
    void fileWriterTests() throws FileNotFoundException {
        Compressor.fileWriter(new ArrayList<String>() {{
            add("input/file1.txt");
            add("input/file2.txt");
        }}, "test.txt");
        Assertions.assertTrue(new File("test.txt").exists());
        Scanner test = new Scanner(new File("test.txt"));
        Assertions.assertNotEquals("*/<>", test.nextLine());
        Assertions.assertEquals("3", test.nextLine());
        Assertions.assertEquals("123", test.nextLine());
        Assertions.assertNotEquals("789", test.nextLine());
        Assertions.assertEquals("789", test.nextLine());
        Assertions.assertEquals("", test.nextLine());
        Assertions.assertNotEquals("*/<>", test.nextLine());
        test.close();
        new File("test.txt").delete();
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
    }
}
