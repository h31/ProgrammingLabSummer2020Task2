import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SizeTest {
    SizeTest() throws IOException {
    }

    File writeText() throws IOException {
        File file = new File("regina.txt");
        FileWriter writer = new FileWriter(file);
        writer.write("i you we wish you gedree jgjfhwh 99993200123984849020339urh ffner");
        writer.close();
        return file;
    }

    Size simple = new Size(writeText());

    @Test
    void getName() {
        assertEquals("regina.txt", simple.getName());
    }

    @Test
    void getSize() throws IOException {
        assertEquals(65.0, new Size(writeText()).getSize());
    }

    @Test
    void getDirectorySize() {
        assertEquals(5141.0, Size.getDirectorySize(new File("src/test/resources")));
}
}