import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

public class fileManager {

    String creator (boolean method, String pathOut) throws IOException {
        if (method) {
            File fOut = new File(pathOut + ".crp");
            if (!fOut.createNewFile()) {
                System.out.println("A file with this name already exists in this directory. Please use the -o argument.");
                throw new FileAlreadyExistsException(pathOut);
            }
            return fOut.getPath();
        }
        else {
            if (pathOut.matches("[a-zA-Z0-9-/.]+(.crp)")){
                File fOut = new File(pathOut.substring(0, pathOut.length() - 4));
                if (!fOut.createNewFile()) {
                    System.out.println("A file with this name already exists in this directory. Please use the -o argument.");
                    throw new FileAlreadyExistsException(pathOut);
                }
                return fOut.getPath();
            }
            throw new IllegalArgumentException(pathOut);
        }
    }

    void reader (flagManager flag) throws IOException {
        crypto crypter = new crypto();
        if (flag.method) {
            FileReader fileIn = new FileReader(flag.pathIn);
            Scanner scan = new Scanner(fileIn);
            while (scan.hasNextLine()) {
                byte[] crypt = crypter.encode(scan.nextLine(), flag.key);
                byteWriter(flag.pathOut, crypt);
            }
            fileIn.close();
            System.out.println("Encoding completed");

        }
        else {
            FileInputStream fis = new FileInputStream(flag.pathIn);
            byte[] data = fis.readAllBytes();
            String text = crypter.decode(data, flag.key);
            writer(flag.pathOut, text);
            fis.close();
            System.out.println("Decoding completed");
        }
    }

    void byteWriter (String path, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(path, true);
        fos.write(data, 0, data.length);
        fos.close();
    }

    void writer (String path, String data) throws IOException {
        FileWriter fileOut = new FileWriter(path, true);
        fileOut.write(data + "\n");
        fileOut.close();
    }
}
