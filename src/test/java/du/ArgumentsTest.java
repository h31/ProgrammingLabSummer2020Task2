package du;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {
    Arguments createArguments() throws IOException {
        return new Arguments(new String[]{"-h", "-c", "test.txt", "file.txt"});
    }

    @Test
    void getFlag() throws IOException {
        assertEquals(new Flags(true, true, false), createArguments().getFlags());
    }

    @Test
    void getFiles() throws IOException {
        assertEquals(2, createArguments().getFiles().size());
    }
}
