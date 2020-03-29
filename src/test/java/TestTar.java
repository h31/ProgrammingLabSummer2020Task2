import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.*;
import static java.nio.file.StandardOpenOption.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestTar {
    List<String> ft1 = new ArrayList<>(List.of("start test", "", "double n", "text text text", "rn scape test"));
    List<String> ft2 = new ArrayList<>(List.of("aaaaaaaaaaa"));
    List<String> ft3 = new ArrayList<>(List.of("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras tristique imperdiet tortor, nec cursus neque placerat sed.",
            "In maximus justo ex, nec finibus justo lobortis ut."));
    List<String> ft4 = new ArrayList<>(List.of("end"));
    List<String> out = new ArrayList<>(List.of("start test", "", "double n", "text text text", "rn scape test",
            "aaaaaaaaaaa", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras tristique imperdiet tortor, nec cursus neque placerat sed.",
            "In maximus justo ex, nec finibus justo lobortis ut.", "end", "file1.text 5", "file2.text 1", "file3.text 2", "file4.text 1", "4"));

    @Test
    public void testout() throws IOException {
        BufferedWriter f1 = newBufferedWriter(Paths.get("file1.text"), WRITE, CREATE, TRUNCATE_EXISTING);
        BufferedWriter f2 = newBufferedWriter(Paths.get("file2.text"), WRITE, CREATE, TRUNCATE_EXISTING);
        BufferedWriter f3 = newBufferedWriter(Paths.get("file3.text"), WRITE, CREATE, TRUNCATE_EXISTING);
        BufferedWriter f4 = newBufferedWriter(Paths.get("file4.text"), WRITE, CREATE, TRUNCATE_EXISTING);
        f1.write("start test\n\ndouble n\ntext text text\r\nrn scape test");
        f1.close();
        f2.write("aaaaaaaaaaa");
        f2.close();
        f3.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras tristique imperdiet tortor, nec cursus neque placerat sed.\n" +
                "In maximus justo ex, nec finibus justo lobortis ut.");
        f3.close();
        f4.write("end");
        f4.close();
        Parse.main(new String[]{"file1.text", "file2.text", "file3.text", "file4.text", "-out", "output.txt"});
        Assertions.assertEquals(out, readAllLines(Paths.get("output.txt")));
    }


    @Test
    public void testu() throws IOException {
        delete(Paths.get("file1.text"));
        delete(Paths.get("file2.text"));
        delete(Paths.get("file3.text"));
        delete(Paths.get("file4.text"));
        Parse.main(new String[]{"-u", "output.txt"});
        Assertions.assertEquals(ft1, readAllLines(Paths.get("file1.text")));
        Assertions.assertEquals(ft2, readAllLines(Paths.get("file2.text")));
        Assertions.assertEquals(ft3, readAllLines(Paths.get("file3.text")));
        Assertions.assertEquals(ft4, readAllLines(Paths.get("file4.text")));
    }

    @Test
    public void testparse() throws IOException {
        delete(Paths.get("file1.text"));
        delete(Paths.get("file2.text"));
        delete(Paths.get("file3.text"));
        delete(Paths.get("file4.text"));
        Parse.main(new String[]{"output.txt", "-u"});
        Assertions.assertEquals(ft1, readAllLines(Paths.get("file1.text")));
        Assertions.assertEquals(ft2, readAllLines(Paths.get("file2.text")));
        Assertions.assertEquals(ft3, readAllLines(Paths.get("file3.text")));
        Assertions.assertEquals(ft4, readAllLines(Paths.get("file4.text")));
    }

    @Test
    public void testErrorOut() {
        Throwable thrown = Assertions.assertThrows(Exception.class, () -> {
            Parse.main(new String[]{"file555.text", "file2.text", "file3.text", "file4.text", "-out", "output.txt"});
        });
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void testErrorU() throws IOException {
        Throwable thrown = Assertions.assertThrows(Exception.class, () -> Parse.main(new String[]{"-u", "outputttt.txt"}));
        assertNotNull(thrown.getMessage());
        BufferedWriter out = newBufferedWriter(Paths.get("output.txt"), WRITE, CREATE, TRUNCATE_EXISTING);
        out.write("20");
        out.close();
        thrown = Assertions.assertThrows(Exception.class, () -> Parse.main(new String[]{"-u", "output.txt"}));
        assertNotNull(thrown.getMessage());
    }
}
