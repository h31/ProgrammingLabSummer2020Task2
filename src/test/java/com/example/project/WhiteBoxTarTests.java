package com.example.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WhiteBoxTarTests {
    //тесты для проверки содержимого разархивированных файлов
    @Test
    void fileReaderTests() throws IOException {
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
        Separator.fileUnpack("input/read.txt");
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
    void fileWriterTests() throws IOException {
        Packager.filePack(new ArrayList<File>() {{
            add(new File("input/part0.txt"));
            add(new File("input/part1.txt"));
            add(new File("input/part2.txt"));
        }}, new File("test.txt"));
        equalsOfFiles(new File("output/check.txt"), new File("test.txt"));
        new File("test.txt").delete();
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
    }

    //проверка правильности разархивирования файлов и обратной архивации
    @Test
    void separateAndCompress() throws IOException {
        new File("test1.txt").delete();
        new File("test.txt").delete();
        Separator.fileUnpack("input/read.txt");
        Packager.filePack(new ArrayList<File>() {{
            add(new File("file0.txt"));
            add(new File("file1.txt"));
            add(new File("file2.txt"));
        }}, new File("test.txt"));
        equalsOfFiles(new File("input/read.txt"), new File("test.txt"));
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
        new File("test.txt").delete();
    }


    //упаковываем файлы в архив, потом этот архив запаковываем в другой архив, и разархивируем
    @Test
    void doubleCompressAndSeparateIt() throws IOException {
        Packager.filePack(new ArrayList<File>() {{
            add(new File("input/part0.txt"));
            add(new File("input/part1.txt"));
        }}, new File("test1.txt"));
        Packager.filePack(new ArrayList<File>() {{
            add(new File("test1.txt"));
            add(new File("input/part2.txt"));
        }}, new File("test2.txt"));
        new File("test1.txt").delete();
        equalsOfFiles(new File("output/doubleArchived.txt"), new File("test2.txt"));
        Separator.fileUnpack("test2.txt");
        equalsOfFiles(new File("input/part2.txt"), new File("input_part2.txt"));
        Assertions.assertTrue(new File("test1.txt").exists());
        Separator.fileUnpack("test1.txt");
        equalsOfFiles(new File("input/part0.txt"), new File("input_part0.txt"));
        equalsOfFiles(new File("input/part1.txt"), new File("input_part1.txt"));
        new File("test1.txt").delete();
        new File("test2.txt").delete();
        new File("input_part0.txt").delete();
        new File("input_part1.txt").delete();
        new File("input_part2.txt").delete();
    }

    //проверка соответствия файлов
    void equalsOfFiles(File file1, File file2) throws FileNotFoundException {
        Scanner check = new Scanner(file1);
        Scanner test = new Scanner(file2);
        while (check.hasNext() || test.hasNext()) {
            Assertions.assertEquals(check.nextLine(), test.nextLine());
        }
        check.close();
        test.close();
    }
}
