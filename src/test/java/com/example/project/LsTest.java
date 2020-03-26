package com.example.project;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LsTest {

    @Test
    void main() throws IOException {
        String str = "";
        File tempfile = new File("d://example.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempfile));
        bw.write("Nikita");
        bw.flush();
        bw.close();
        Ls.ProgramFile programFile = new Ls.ProgramFile(tempfile);
        //assertEquals("25.03.2020 22:43:57", str);
        //BufferedReader br = new BufferedReader(new FileReader(tempfile));
        /*Scanner scan = new Scanner(br);
        while (scan.hasNext()) {
            System.out.println(scan.nextLine());
        }*/
        str = programFile.getFilePermissions();
        assertEquals("rwx", str);
        str = programFile.getPermBitMask();
        //System.out.println(str);
        assertEquals("111", str);
        str = programFile.humanReadableSize();
        assertEquals("6 B", str);
        File file = new File("D:/CourseWorkOVT2.2");
        Ls.ProgramFile lastModifiedFile = new Ls.ProgramFile(file);
        str = lastModifiedFile.getLastModificate();
        System.out.println(lastModifiedFile.getLastModificate());
        assertEquals("23.03.2020 11:35:09", lastModifiedFile.getLastModificate());
    }
}