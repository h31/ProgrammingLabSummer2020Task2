package com.example.project;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SplitTest {
    @Test
    void readerTest() throws IOException {
        List<String> test = Arrays.asList("1","2","3","а","б","в","a","b","c");
        String input = "input/readerTest.txt";
        List<String> res = Split.reader(input);
        assertEquals(test, res);
    }

    @Test
    void nameFiles() {
        String[] args = new String[]{"-o","-"};
        String input = "qwerty.txt";
        String name = Split.nameFiles(args,input);
        assertEquals("qwerty", name);
        args = new String[]{"-o", "q"};
        name = Split.nameFiles(args,input);
        assertEquals("q", name);
        args = new String[]{};
        name = Split.nameFiles(args,input);
        assertEquals("x", name);
    }

    @Test
    void outputFiles() {
        String[] args = new String[]{};
        String name = "qwerty";
        int countFiles = 3;
        List<String> res = Split.outputFiles(args, countFiles, name);
        List<String> exp = Arrays.asList("qwertyaa.txt","qwertyab.txt","qwertyac.txt");
        assertEquals(exp, res);
        args = new String[]{"-d"};
        res = Split.outputFiles(args, countFiles, name);
        exp = Arrays.asList("qwerty1.txt","qwerty2.txt","qwerty3.txt");
        assertEquals(exp, res);
    }

    @Test
    void writeTest() throws IOException {
        ArrayList<String> outputFiles = new ArrayList<>();
        outputFiles.add("qwerty1.txt");
        outputFiles.add("qwerty2.txt");
        outputFiles.add("qwerty3.txt");
        ArrayList<String> list = new ArrayList<>();
        list.add("qwerty11.txt");
        list.add("qwerty22.txt");
        list.add("qwerty33.txt");
        int size = 1;
        int sizeParent = outputFiles.size();
        int flagC = -1;
        Split.write(outputFiles, list, size, sizeParent, flagC);
        List<String> exp = Arrays.asList("qwerty11.txt", "qwerty22.txt", "qwerty33.txt");
        ArrayList<String> res = readAll(outputFiles);
        assertEquals(exp, res);
        res.clear();
        flagC = 1;
        size = 12;
        Split.write(outputFiles, list, size, sizeParent, flagC);
        res = readAll(outputFiles);
        assertEquals(exp, res);
    }

    @Test
    void mainTest() throws IOException {
        String[] args = new String[]{"input/testFile.txt", "-l", "2", "-o", "qwerty"};
        ArrayList<String> outputFiles = new ArrayList<>();
        outputFiles.add("qwertyaa.txt");
        outputFiles.add("qwertyab.txt");
        List<String> exp1 = Arrays.asList("1a", "2b");
        List<String> exp2 = Arrays.asList("3c");
        Split.main(args);
        String dir = "output/";
        ArrayList<String> res1 = Split.reader(dir + outputFiles.get(0));
        ArrayList<String> res2 = Split.reader(dir + outputFiles.get(1));
        delAll(outputFiles);
        assertEquals(exp1, res1);
        assertEquals(exp2, res2);
        args = new String[]{"input/testFile.txt", "-n", "2","-d"};
        outputFiles.clear();
        outputFiles.add("x1.txt");
        outputFiles.add("x2.txt");
        Split.main(args);
        res1 = Split.reader(dir + outputFiles.get(0));
        res2 = Split.reader(dir + outputFiles.get(1));
        delAll(outputFiles);
        assertEquals(exp1, res1);
        assertEquals(exp2, res2);
        args = new String[]{"file", "input/testFile.txt", "-c", "4", "-o","-","-d"};
        outputFiles.clear();
        outputFiles.add("testFile1.txt");
        outputFiles.add("testFile2.txt");
        exp1 = Arrays.asList("1a2b");
        Split.main(args);
        res1 = Split.reader(dir + outputFiles.get(0));
        res2 = Split.reader(dir + outputFiles.get(1));
        delAll(outputFiles);
        assertEquals(exp1, res1);
        assertEquals(exp2, res2);
    }

    @Test
    void errors() {
        assertThrows(IllegalArgumentException.class, () -> Split.main(new String[]{"-c", "4", "-o","-","-d"}));
        assertThrows(IllegalArgumentException.class, () -> Split.main(new String[]{"input/testFile.txt", "-c", "4", "-n","3","-d"}));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Split.main(new String[]{"input/testFile.txt", "-n","677",}));
    }

    private ArrayList<String> readAll(ArrayList<String> outputFiles) throws IOException {
        String dir = "output/";
        ArrayList<String> res = new ArrayList<>();
        for (String outputFile : outputFiles) {
            res.addAll(Split.reader(dir + outputFile));
        }
        delAll(outputFiles);
        return res;
    }
    private void delAll(ArrayList<String> outputFiles) {
        String dir = "output/";
        for (String outputFile : outputFiles) {
            File file = new File(dir + outputFile);
            file.delete();
        }
    }


}