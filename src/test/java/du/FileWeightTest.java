package du;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileWeightTest {
    String createFileWeight(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        FileWeight weight = new FileWeight(arguments);
        return weight.getWeight();
    }

    @Test
    void h() throws IOException {
        assertEquals("279.7KB", createFileWeight(new String[]{"-h", "src/test/resources"}));
        assertEquals("277.6KB", createFileWeight(new String[]{"-h", "src/test/resources/file3.txt"}));
        assertEquals("279.7KB 101.0B 1.9KB", createFileWeight(new String[]{"-h", "src/test/resources", "src/test/resources/file1.txt", "src/test/resources/file2.txt"}));
    }

    @Test
    void si() throws IOException {
        assertEquals("286.4", createFileWeight(new String[]{"-si", "src/test/resources"}));
        assertEquals("284.3", createFileWeight(new String[]{"-si", "src/test/resources/file3.txt"}));
        assertEquals("286.4 0.1 2.0", createFileWeight(new String[]{"-si", "src/test/resources", "src/test/resources/file1.txt", "src/test/resources/file2.txt"}));
    }

    @Test
    void hSi() throws IOException {
        assertEquals("286.4KB", createFileWeight(new String[]{"-si", "-h", "src/test/resources"}));
        assertEquals("284.3KB", createFileWeight(new String[]{"-si", "-h", "src/test/resources/file3.txt"}));
        assertEquals("286.4KB 101.0B 2.0KB", createFileWeight(new String[]{"-si", "-h", "src/test/resources", "src/test/resources/file1.txt", "src/test/resources/file2.txt"}));
    }

    @Test
    void noFlag() throws IOException {
        assertEquals("279.7", createFileWeight(new String[]{"src/test/resources"}));
        assertEquals("277.6", createFileWeight(new String[]{"src/test/resources/file3.txt"}));
        assertEquals("279.7 0.1 1.9", createFileWeight(new String[]{"src/test/resources", "src/test/resources/file1.txt", "src/test/resources/file2.txt"}));
    }

    @Test
    void c() throws IOException {
        assertEquals("279.7", createFileWeight(new String[]{"-c", "src/test/resources/file3.txt", "src/test/resources/file1.txt", "src/test/resources/file2.txt"}));
    }

    @Test
    void ch() throws IOException {
        assertEquals("2.0KB", createFileWeight(new String[]{"-c", "-h", "src/test/resources/file1.txt", "src/test/resources/file2.txt"}));
    }
}
