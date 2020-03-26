package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LsTest {

    @Test
    void main() throws IOException {
        String str = "";
        File tempfile = new File("src/test/resources/testfolder/randomText.txt");
        Ls.ProgramFile programFile = new Ls.ProgramFile(tempfile);
        str = programFile.getFilePermissions();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            assertEquals("rwx", str);
        }else{
            assertEquals("rw-", str);
        }
        System.out.println(str);
        str = programFile.getPermBitMask();
        assertEquals("111", str);
        str = programFile.humanReadableSize();
        assertEquals("206 B", str);
        File file = new File("src/test/resources/testfolder/CalculatorTest.txt");
        Ls.ProgramFile lastModifiedFile = new Ls.ProgramFile(file);
        str = lastModifiedFile.getLastModificate();
        assertEquals("26.03.2020 15:47:06", lastModifiedFile.getLastModificate());
    }
}