import java.io.*;
import java.util.Arrays;
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
                if (!fOut.createNewFile())
                    throw new IllegalArgumentException(pathOut);
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
        }
        else {
            FileInputStream fileInputStream = new FileInputStream(flag.pathIn);
            byte[] data = fileInputStream.readAllBytes();
            System.out.println(Arrays.toString(data));
            String text = crypter.decode(data, flag.key);
            writer(flag.pathOut, text);
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
