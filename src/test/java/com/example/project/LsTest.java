package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        } else{
            assertEquals("rw-", str);
        }
        str = programFile.getPermBitMask();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            assertEquals("111", str);
        }else{
            assertEquals("110", str);
        }
        str = programFile.humanReadableSize();
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            assertEquals("206 B", str);
        } else{
            assertEquals("202 B", str);
        }
        File file = new File("src/test/resources/testfolder/CalculatorTest.txt");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Ls.ProgramFile lastModifiedFile = new Ls.ProgramFile(file);
        str = lastModifiedFile.getLastModificate();
        //assertEquals("26.03.2020 15:47:06", lastModifiedFile.getLastModificate());
        assertEquals(sdf.format(new Date(file.lastModified())), lastModifiedFile.getLastModificate());

    }
}