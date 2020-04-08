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
        assertEquals("100.4KB ", createFileWeight(new String[]{"-h", "src/test/resources"}));
        assertEquals("98.3KB ", createFileWeight(new String[]{"-h", "src/test/resources/test3.txt"}));
        assertEquals("110.0B 2.0KB 100.4KB ", createFileWeight(new String[]{"-h", "src/test/resources/test.txt", "src/test/resources/test2.txt", "src/test/resources"}));
    }

    @Test
    void si() throws IOException {
        assertEquals("102.8 ", createFileWeight(new String[]{"-si", "src/test/resources"}));
        assertEquals("100.7 ", createFileWeight(new String[]{"-si", "src/test/resources/test3.txt"}));
        assertEquals("0.1 2.0 102.8 ", createFileWeight(new String[]{"-si", "src/test/resources/test.txt", "src/test/resources/test2.txt", "src/test/resources"}));
    }

    @Test
    void hSi() throws IOException {
        assertEquals("102.8KB ", createFileWeight(new String[]{"-si", "-h", "src/test/resources"}));
        assertEquals("100.7KB ", createFileWeight(new String[]{"-si", "-h", "src/test/resources/test3.txt"}));
        assertEquals("110.0B 2.0KB 102.8KB ", createFileWeight(new String[]{"-si","-h", "src/test/resources/test.txt", "src/test/resources/test2.txt", "src/test/resources"}));
    }

    @Test
    void noFlag() throws IOException {
        assertEquals("100.4 ", createFileWeight(new String[]{"src/test/resources"}));
        assertEquals("98.3 ", createFileWeight(new String[]{"src/test/resources/test3.txt"}));
        assertEquals("0.1 2.0 100.4 ", createFileWeight(new String[]{"src/test/resources/test.txt", "src/test/resources/test2.txt", "src/test/resources"}));
    }

    @Test
    void c() throws IOException {
        assertEquals("102.5", createFileWeight(new String[]{"-c", "src/test/resources/test.txt", "src/test/resources/test2.txt", "src/test/resources"}));
    }
}
