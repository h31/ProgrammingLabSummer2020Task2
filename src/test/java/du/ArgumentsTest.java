package du;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArgumentsTest {
    Arguments createArguments() throws IOException {
        try {
            return  new Arguments(new String[]{"du", "-h", "test.txt", "-c","file.txt"});
        } catch (IOException e){
            System.exit(1);
        } finally {
            return new Arguments(new String[]{"du", "-h", "test.txt", "-c", "file.txt"});
        }

    }

    @Test
    void getFlag() throws IOException {
        assertEquals(new Flags(true, true,false), createArguments().getFlags());
    }

    @Test
    void getFiles() throws IOException {
        assertEquals(2, createArguments().getFiles().size());
    }
}
