import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void main() throws IOException {
        File file = new File("testDirectory/test.txt");
        String key1 = "123ABC";
        String key2 = "HHFLE7";
        String key3 = "FE54A4D";
        File fileEncode = new File("testDirectory/test-encode.txt");
        File fileDecode = new File("testDirectory/test.txt.crp");
        File fileEDE = new File("testDirectory/test-encode-2.txt.crp");
        File fileEDED = new File("testDirectory/test-encode-2.txt");

        Main.main(new String[]{"-c", key1, file.getAbsolutePath()});
        Main.main(new String[]{"-d", key1, fileDecode.getAbsolutePath(), "-o", fileEncode.getAbsolutePath()});
        Main.main(new String[]{"-c", key1, fileEncode.getAbsolutePath()});
        Main.main(new String[]{"-c", key3, fileEncode.getAbsolutePath(), "-o", fileEDE.getAbsolutePath()});
        Main.main(new String[]{"-d", key1, fileEDE.getAbsolutePath()});
        File fileEncodeDecode = new File("testDirectory/test-encode.txt.crp");

        {
            assertTrue(textEquals(file, fileEncode));
            assertTrue(textEquals(fileDecode, fileEncodeDecode));
            assertFalse(textEquals(fileEncodeDecode, fileEDE));
            assertFalse(textEquals(fileEncode, fileEDED));
            assertFalse(textEquals(file, fileEDED));

            assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{"-c", key2, file.getAbsolutePath()}));
            assertThrows(FileNotFoundException.class, () -> Main.main(new String[]{"-c", key1, "testDirectory/test-fail.txt"}));
            assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{"-d", key1, "testDirectory/test.txt"}));
            assertThrows(FileAlreadyExistsException.class, () -> Main.main(new String[]{"-c", key1, "testDirectory/test.txt"}));
            assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{"-d", key1, "testDirectory/test.txt"}));
            assertThrows(FileAlreadyExistsException.class, () -> Main.main(new String[]{"-c", key1, "testDirectory/test.txt", "-o", "testDirectory/test.txt"}));
        }

        fileDecode.delete();
        fileEncode.delete();
        fileEncodeDecode.delete();
        fileEDE.delete();
        fileEDED.delete();
    }

    boolean textEquals (File file1, File file2) throws IOException {
        FileReader fR1 = new FileReader(file1);
        FileReader fR2 = new FileReader(file2);
        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);
        String line1;
        String line2;
        boolean equal = true;
        while (equal && ((line1 = reader1.readLine()) != null) && ((line2 = reader2.readLine()) != null))
            if (!line1.equalsIgnoreCase(line2))
                equal = false;
        reader1.close();
        reader2.close();
        return equal;
    }
}