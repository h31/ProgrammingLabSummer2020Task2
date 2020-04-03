import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
class MainTest {
    @Test
    void main() throws IOException {
        Main.main(new String[]{"-h", "-si", "-c", "src/test/resources/cat.txt", "src/test/resources"});
        assertEquals("5,1K src\\test\\resources\\cat.txt\r\n" +
                "12,0K src\\test\\resources\r\n" +
                "17,1K", new String(Files.readAllBytes(Paths.get("output"))));
        Main.main(new String[]{"-si","-c","src/test/resources/cat.txt","src/test/resources"});
        assertEquals("5,1K src\\test\\resources\\cat.txt\r\n" +
                "12,0K src\\test\\resources\r\n" +
                "17,1K",new String(Files.readAllBytes(Paths.get("output"))));
        Main.main(new String[]{"-c","src/test/resources/cat.txt","src/test/resources"});
        assertEquals("4,9K src\\test\\resources\\cat.txt\r\n" +
                "11,7K src\\test\\resources\r\n" +
                "16,7K", new String(Files.readAllBytes(Paths.get("output"))));
        Main.main(new String[]{"-h","-c","src/test/resources/cat.txt","src/test/resources"});
        assertEquals("4,9K src\\test\\resources\\cat.txt\r\n" +
                "11,7K src\\test\\resources\r\n" +
                "16,7K",new String(Files.readAllBytes(Paths.get("output"))));
        Main.main(new String[]{"-h","-si","src/test/resources/cat.txt","src/test/resources"});
        assertEquals("5,1K src\\test\\resources\\cat.txt\r\n" +
                "12,0K src\\test\\resources",
                new String(Files.readAllBytes(Paths.get("output"))));
        Main.main(new String[]{"-h","src/test/resources/cat.txt","src/test/resources","src/regina"});
        assertEquals("4,9K src\\test\\resources\\cat.txt\r\n" +
                "11,7K src\\test\\resources\r\n" +
                "Данного файла или каталога не существует",new String(Files.readAllBytes(Paths.get("output"))));
        Main.main(new String[]{"-si","src/test/resources/cat.txt","src/test/resources","src/test/resources/dog.txt"});
        assertEquals("5,1K src\\test\\resources\\cat.txt\r\n" +
                "12,0K src\\test\\resources\r\n" +
                "5,1K src\\test\\resources\\dog.txt",
                new String(Files.readAllBytes(Paths.get("output"))));
        Main.main(new String[]{"src/test/resources/cat.txt","src/test/resources","src/test/resources/dog.txt"});
        assertEquals("4,9K src\\test\\resources\\cat.txt\r\n" +
                "11,7K src\\test\\resources\r\n" +
                "5,0K src\\test\\resources\\dog.txt",new String(Files.readAllBytes(Paths.get("output"))));








    }

}