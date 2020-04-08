package du;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileWeightTest {
    String createFileWeight(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        FileWeight weight = new FileWeight(arguments);
        return weight.degree();
    }

    @Test
    void h() throws IOException {
        assertEquals("40.4MB ", createFileWeight(new String[]{"-h", "src/test/resources"}));
        assertEquals("5.0B ", createFileWeight(new String[]{"-h", "src/test/resources/test3.txt"}));
        assertEquals("110.0B 98.3KB 40.4MB ", createFileWeight(new String[]{"-h", "src/test/resources/test.txt", "src/test/resources/testDir/test1.txt", "src/test/resources"}));
    }

    @Test
    void si() throws IOException {
        assertEquals("42371.1 ", createFileWeight(new String[]{"-si", "src/test/resources"}));
        assertEquals("0.0 ", createFileWeight(new String[]{"-si", "src/test/resources/test3.txt"}));
        assertEquals("0.1 100.7 42371.1 ", createFileWeight(new String[]{"-si", "src/test/resources/test.txt", "src/test/resources/testDir/test1.txt", "src/test/resources"}));
    }

    @Test
    void hSi() throws IOException {
        assertEquals("42.4MB ", createFileWeight(new String[]{"-si", "-h", "src/test/resources"}));
        assertEquals("5.0B ", createFileWeight(new String[]{"-si", "-h", "src/test/resources/test3.txt"}));
        assertEquals("110.0B 100.7KB 42.4MB ", createFileWeight(new String[]{"-si","-h", "src/test/resources/test.txt", "src/test/resources/testDir/test1.txt", "src/test/resources"}));
    }

    @Test
    void noFlag() throws IOException {
        assertEquals("41378.0 ", createFileWeight(new String[]{"src/test/resources"}));
        assertEquals("0.0 ", createFileWeight(new String[]{"src/test/resources/test3.txt"}));
        assertEquals("0.1 98.3 41378.0 ", createFileWeight(new String[]{"src/test/resources/test.txt", "src/test/resources/testDir/test1.txt", "src/test/resources"}));
    }

    @Test
    void c() throws IOException {
        assertEquals("41376.1", createFileWeight(new String[]{"-c", "src/test/resources/test.txt", "src/test/resources/testDir/test1.txt", "src/test/resources/test3.txt", "src/test/resources/test2.txt"}));
    }

    @Test
    void hCSi() throws IOException {
        assertEquals("42.4MB", createFileWeight(new String[]{"-si", "-h", "-c", "src/test/resources/test.txt", "src/test/resources/testDir/test1.txt", "src/test/resources/test3.txt", "src/test/resources/test2.txt"}));
        }
}
