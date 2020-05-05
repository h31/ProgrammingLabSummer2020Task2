package com.example.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;


public class FindTest {

    @Test
    public void isDirTest() {
        File dir = new File(System.getProperty("user.dir"));
        File dir1 = new File(dir.getAbsolutePath());
        if (dir1.isDirectory()) {
            for (File file: dir1.listFiles()) {
                if (file.isDirectory()) {
                    Assertions.assertTrue(Find.isDir(file.getAbsolutePath()));
                }
            }
        }
        Assertions.assertTrue(Find.isDir(dir.getAbsolutePath()));
        Assertions.assertTrue(Find.isDir(dir1.getAbsolutePath()));
    }

}
