package com.example.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WhiteBoxTarTests {
    //тесты для проверки содержимого разархивированных файлов
    @Test
    void fileReaderTests() throws IOException {
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
    void fileWriterTests() throws IOException {
        Compressor.fileWriter(new ArrayList<File>() {{
            add(new File("input/part0.txt"));
            add(new File("input/part1.txt"));
            add(new File("input/part2.txt"));
        }}, "test.txt");
        int k = 0;
        Scanner check = new Scanner(new File("output/check.txt"));
        Scanner test = new Scanner(new File("test.txt"));
        String str;
        while (check.hasNext() || test.hasNext()) {
            str = check.nextLine();
            if (str.contains("input\\part")) {
                //добавил эту проверку, потому что в разных системах разные правила на имена файлов.
                Assertions.assertEquals(new File("input/part" + k + ".txt").toString(), test.nextLine());
                k++;
            } else Assertions.assertEquals(str, test.nextLine());
        }
        test.close();
        check.close();
        new File("test.txt").delete();
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
    }

    //проверка правильности разархивирования файлов и обратной архивации
    @Test
    void separateAndCompress() throws IOException {
        Separator.fileReader("input/read.txt");
        Compressor.fileWriter(new ArrayList<File>() {{
            add(new File("file0.txt"));
            add(new File("file1.txt"));
            add(new File("file2.txt"));
        }}, "test.txt");
        Scanner begin = new Scanner(new File("input/read.txt"));
        Scanner end = new Scanner(new File("test.txt"));
        while (begin.hasNext() || end.hasNext()) {
            Assertions.assertEquals(begin.nextLine(), end.nextLine());
        }
        begin.close();
        end.close();
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
        new File("test.txt").delete();
    }
}
