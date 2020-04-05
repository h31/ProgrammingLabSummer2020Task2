package du;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileWeightTest {
    String createFileWeight(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        FileWeight weight = new FileWeight(arguments);
        return weight.degree();
    }

    @Test
    void h() throws IOException {
        assertEquals("40.4MB ", createFileWeight(new String[]{"-h", "files"}));
        assertEquals("5.0B ", createFileWeight(new String[]{"-h", "files/test3.txt"}));
        assertEquals("110.0B 98.3KB 40.4MB ", createFileWeight(new String[]{"-h", "files/test.txt", "files/testDir/test1.txt", "files"}));
    }

    @Test
    void si() throws IOException {
        assertEquals("42371.1 ", createFileWeight(new String[]{"-si", "files"}));
        assertEquals("0.0 ", createFileWeight(new String[]{"-si", "files/test3.txt"}));
        assertEquals("0.1 100.7 42371.1 ", createFileWeight(new String[]{"-si", "files/test.txt", "files/testDir/test1.txt", "files"}));
    }

    @Test
    void hSi() throws IOException {
        assertEquals("42.4MB ", createFileWeight(new String[]{"-si", "-h", "files"}));
        assertEquals("5.0B ", createFileWeight(new String[]{"-si", "-h", "files/test3.txt"}));
        assertEquals("110.0B 100.7KB 42.4MB ", createFileWeight(new String[]{"-si","-h", "files/test.txt", "files/testDir/test1.txt", "files"}));
    }

    @Test
    void noFlag() throws IOException {
        assertEquals("41378.0 ", createFileWeight(new String[]{"files"}));
        assertEquals("0.0 ", createFileWeight(new String[]{"files/test3.txt"}));
        assertEquals("0.1 98.3 41378.0 ", createFileWeight(new String[]{"files/test.txt", "files/testDir/test1.txt", "files"}));
    }

    @Test
    void c() throws IOException {
        assertEquals("41376.1", createFileWeight(new String[]{"-c", "files/test.txt", "files/testDir/test1.txt", "files/test3.txt", "files/test4.txt"}));
    }

    @Test
    void hCSi() throws IOException {
        assertEquals("42.4MB", createFileWeight(new String[]{"-si", "-h", "-c", "files/test.txt", "files/testDir/test1.txt", "files/test3.txt", "files/test4.txt"}));
        }
}
