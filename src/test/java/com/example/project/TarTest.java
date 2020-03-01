package com.example.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;


class TarTest {


    //тесты для проверки разбиения одного файла на несколько
    @Test
    void fileReaderTests() throws FileNotFoundException {
        Tar.fileReader("input/read.txt");
        Assertions.assertTrue(new File("file0.txt").exists());
        Assertions.assertTrue(new File("file1.txt").exists());
        Assertions.assertTrue(new File("file2.txt").exists());
        //проверить содержимое
        new File("file0.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
    }


}
