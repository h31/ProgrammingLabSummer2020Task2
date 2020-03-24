import java.io.*;
import java.util.Scanner;

public class fileManager {

    String creator (boolean method, String pathOut) throws IOException {
        if (method) {
            File fOut = new File(pathOut + ".crp");
            if (!fOut.createNewFile()) {
                int i = 2;
                while (!fOut.createNewFile()) {
                    fOut = new File(pathOut + "-" + i + ".crp");
                    i++;
                }
            }
            return fOut.getPath();
        }
        else { //добавить расшифровщик (удаление расширения)
            File fOut = new File(pathOut);
            if (!fOut.createNewFile()) {
                throw new IllegalArgumentException(pathOut);
            }
            return fOut.getPath();
        }
    }

    void reader (flagManager flag) throws IOException {
        crypto crypter = new crypto();
        FileReader fileIn = new FileReader(flag.pathIn);
        Scanner scan = new Scanner(fileIn);
        while (scan.hasNextLine()) {
            String crypt = crypter.coder(scan.nextLine(), flag.key);
            writer(flag.pathOut, crypt);
        }
        fileIn.close();
    }

    void writer (String path, String data) throws IOException {
        FileWriter fileOut = new FileWriter(path, true);
        fileOut.write(data + "\n");
        fileOut.close();
    }
}
