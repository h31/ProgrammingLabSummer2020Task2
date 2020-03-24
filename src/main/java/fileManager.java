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
        else {
            if (pathOut.matches("[a-zA-Z/.]+(.crp)")){
                System.out.println(pathOut.substring(0, pathOut.lastIndexOf(".")));
                File fOut = new File(pathOut.substring(0, pathOut.lastIndexOf(".")));
                if (!fOut.createNewFile()) {
                    throw new IllegalArgumentException(pathOut);
                }
                return fOut.getPath();
            }
            throw new IllegalArgumentException(pathOut);
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
