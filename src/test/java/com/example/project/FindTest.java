package com.example.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public class FindTest {

    @Test
    public void isDirTest() {
        Assertions.assertTrue(Find.isDir("C:\\Program Files"));
        Assertions.assertFalse(Find.isDir("C:\\Programm Files"));
        Assertions.assertFalse(Find.isDir("C:\\ProgramFiles"));
        Assertions.assertTrue(Find.isDir("D:\\IntelliJ IDEA"));
    }
}
