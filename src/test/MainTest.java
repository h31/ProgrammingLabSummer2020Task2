import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void main() throws IOException {
        Flags flag = new Flags();
        flag.setDirectory(new File("testfolder/folder/inner-folder"));
        assertEquals("FileInformation.test\nFlags.test\nFunctions.test\nMain.test", Functions.printDirectory(flag, new ByteArrayOutputStream()).toString());
        flag.setDirectory(new File("testfolder/blabla"));
        assertThrows(NoSuchFileException.class, () -> Functions.printDirectory(flag, new ByteArrayOutputStream()));
        flag.setDirectory(new File("testfolder/folder/inner-folder/Main.test"));
        assertEquals("111 10.03.2020 15:19:53 601 Main.test", Functions.printDirectory(flag, new ByteArrayOutputStream()).toString());
        flag.setLongType(true);
        flag.setDirectory(new File("testfolder/folder"));
        assertEquals("111 10.03.2020 15:25:07 2221 Flags.test\n111 10.03.2020 16:29:30 6755 inner-folder",
                Functions.printDirectory(flag, new ByteArrayOutputStream()).toString());
        flag.setHumanType(true);
        assertEquals("rwx 10.03.2020 15:25:07 2,2К Flags.test\nrwx 10.03.2020 16:29:30 6,6К inner-folder",
                Functions.printDirectory(flag, new ByteArrayOutputStream()).toString());
        flag.setReversedType(true);
        assertEquals("rwx 10.03.2020 16:29:30 6,6К inner-folder\nrwx 10.03.2020 15:25:07 2,2К Flags.test",
                Functions.printDirectory(flag, new ByteArrayOutputStream()).toString());
        Main.main(new String[]{"-o", "test.txt", "testfolder/folder/inner-folder"});
        assertEquals("FileInformation.test\nFlags.test\nFunctions.test\nMain.test",
                new String(Files.readAllBytes(Paths.get("test.txt"))));
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{"-o"}));
        Main.main(new String[]{"-l", "-h", "-r", "-o", "test.txt", "testfolder/folder/inner-folder"});
        assertEquals("rwx 10.03.2020 15:19:53 601,0Б Main.test\n" +
                        "rwx 10.03.2020 15:30:39 1,5К Functions.test\n" +
                        "rwx 10.03.2020 15:25:07 2,2К Flags.test\n" +
                        "rwx 10.03.2020 14:18:39 2,3К FileInformation.test",
                new String(Files.readAllBytes(Paths.get("test.txt"))));
    }
}