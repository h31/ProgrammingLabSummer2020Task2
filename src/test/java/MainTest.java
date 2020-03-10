import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void mainTest() throws IOException {
        Flags flag = new Flags();
        flag.setDirectory(new File("testfolder/folder/inner-folder"));
        assertEquals("FileInformation.java\nFlags.java\nFunctions.java\nMain.java", Functions.printDirectory(flag));
        flag.setDirectory(new File("testfolder/blabla"));
        assertEquals("Данного каталога не существует", Functions.printDirectory(flag));
        flag.setDirectory(new File("testfolder/folder/inner-folder/Main.java"));
        assertEquals("111 10.03.2020 15:19:53 601 Main.java", Functions.printDirectory(flag));
        flag.setLFlag(true);
        flag.setDirectory(new File("testfolder/folder"));
        assertEquals("111 10.03.2020 15:25:07 2221 Flags.java\n111 10.03.2020 15:33:48 6755 inner-folder",
                Functions.printDirectory(flag));
        flag.setHFlag(true);
        assertEquals("rwx 10.03.2020 15:25:07 2,2К Flags.java\nrwx 10.03.2020 15:33:48 6,6К inner-folder",
                Functions.printDirectory(flag));
        flag.setRFlag(true);
        assertEquals("rwx 10.03.2020 15:33:48 6,6К inner-folder\nrwx 10.03.2020 15:25:07 2,2К Flags.java",
                Functions.printDirectory(flag));
        Main.main(new String[]{"-o", "test.txt", "testfolder/folder/inner-folder"});
        assertEquals("FileInformation.java\nFlags.java\nFunctions.java\nMain.java",
                new String(Files.readAllBytes(Paths.get("test.txt"))));
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{"-o"}));
        Main.main(new String[]{"-l", "-h", "-r", "-o", "test.txt", "testfolder/folder/inner-folder"});
        assertEquals("rwx 10.03.2020 15:19:53 601,0Б Main.java\n" +
                        "rwx 10.03.2020 15:30:39 1,5К Functions.java\n" +
                        "rwx 10.03.2020 15:25:07 2,2К Flags.java\n" +
                        "rwx 10.03.2020 14:18:39 2,3К FileInformation.java",
                new String(Files.readAllBytes(Paths.get("test.txt"))));
    }
}