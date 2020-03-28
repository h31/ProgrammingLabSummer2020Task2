import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void main() throws IOException {
        String text = "Привет, Мир!\nТест шифровки-дешифровки";
        File dir = new File("testDirectory");
        dir.mkdir();
        File file = new File("testDirectory/test.txt");
        file.createNewFile();
        FileWriter fileW = new FileWriter(file);
        fileW.write(text);
        fileW.close();

        String key1 = "123ABC";
        File fileEncode = new File("testDirectory/test-encode.txt");
        File fileDecode = new File("testDirectory/test.txt.crp");

        Main.main(new String[]{"-c", key1, file.getAbsolutePath()});
        Main.main(new String[]{"-d", key1, fileDecode.getAbsolutePath(), "-o", fileEncode.getAbsolutePath()});



//        String[] entries = dir.list();
//        for(String s: entries){
//            File currentFile = new File(dir.getPath(),s);
//            currentFile.delete();
//        }
//        dir.delete();
    }
}