import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.*;
import static java.nio.file.StandardOpenOption.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TestTar {
    @Test
    public void test() {
        try {
            BufferedWriter f1 = newBufferedWriter(Paths.get("file1.txt"), TRUNCATE_EXISTING, WRITE, CREATE);
            BufferedWriter f2 = newBufferedWriter(Paths.get("file2.txt"), TRUNCATE_EXISTING, CREATE, WRITE);
            BufferedWriter f3 = newBufferedWriter(Paths.get("file3.txt"), TRUNCATE_EXISTING, CREATE, WRITE);
            BufferedWriter f4 = newBufferedWriter(Paths.get("file4.txt"), TRUNCATE_EXISTING, CREATE, WRITE);
            f1.write("aaa aaa\nbbbbbb\n \ngo\n");
            f2.write("test");
            f3.write("123////\n////");
            f4.write("endtest");
            f1.close();
            f2.close();
            f3.close();
            f4.close();
            List<String> out = new ArrayList<>(List.of("4", "file1.txt 4", "file2.txt 1", "file3.txt 2", "file4.txt 1",
                    "aaa aaa", "bbbbbb", " ", "go", "test", "123////", "////", "endtest"));
            String[] args = {"file1.txt", "file2.txt", "file3.txt", "file4.txt", "-out", "output.txt"};
            Tar.main(args);
            assertEquals(out, readAllLines(Paths.get("output.txt")));
            out.add("j");
            assertNotEquals(out, readAllLines(Paths.get("output.txt")));
            BufferedWriter f4new = newBufferedWriter(Paths.get("file4.txt"), TRUNCATE_EXISTING, CREATE, WRITE);
            f4new.write("not end");
            f4new.close();
            BufferedWriter f5 = newBufferedWriter(Paths.get("file5.txt"), TRUNCATE_EXISTING, CREATE, WRITE);
            f5.write("end");
            f5.close();
            out.remove(out.size() - 1);
            out.set(out.size() - 1, "not end");
            out.add("end");
            args = new String[]{"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt", "-out", "output.txt"};
            Tar.main(args);
            out.set(0, "5");
            out.add(5, "file5.txt 1");
            assertEquals(out, readAllLines(Paths.get("output.txt")));
            delete(Paths.get("file1.txt"));
            delete(Paths.get("file2.txt"));
            delete(Paths.get("file3.txt"));
            delete(Paths.get("file4.txt"));
            delete(Paths.get("file5.txt"));
            args = new String[]{"-u", "output.txt"};
            Tar.main(args);
            args = new String[]{"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt", "-out", "outputnew.txt"};
            Tar.main(args);
            assertEquals(readAllLines(Paths.get("output.txt")), readAllLines(Paths.get("outputnew.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
