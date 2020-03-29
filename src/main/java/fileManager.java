import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class fileManager {
    private static final Logger log = Logger.getLogger(fileManager.class);
    String creator (boolean method, String pathOut, boolean manual) throws IOException {
        if (manual){
            File fOut = new File(pathOut);
            if (!fOut.createNewFile()) {
                System.err.println("A file with this name already exists in this directory. Please use the -o argument.");
                log.error("File" + fOut + "already exists");
                throw new FileAlreadyExistsException(pathOut);
            }
            return fOut.getPath();
        }
        if (method) {
            File fOut = new File(pathOut + ".crp");
            if (!fOut.createNewFile()) {
                System.err.println("A file with this name already exists in this directory. Please use the -o argument.");
                log.error("File" + fOut + "already exists");
                throw new FileAlreadyExistsException(pathOut);
            }
            return fOut.getPath();
        }
        else {
            if (pathOut.matches("[a-zA-Zа-яёА-ЯЁ0-9-/.]+(.crp)")){
                File fOut = new File(pathOut.substring(0, pathOut.lastIndexOf(".")));
                if (!fOut.createNewFile()) {
                    System.err.println("A file with this name already exists in this directory. Please use the -o argument.");
                    log.error("File" + fOut + "already exists");
                    throw new FileAlreadyExistsException(pathOut);
                }
                return fOut.getPath();
            }
            System.err.println("The input file name is incorrect");
            log.error("The input file name is incorrect");
            throw new IllegalArgumentException(pathOut);
        }
    }

    void reader (flagManager flag) throws IOException {
        crypto crypter = new crypto();
        if (flag.method) {
            FileReader fileIn = new FileReader(flag.pathIn);
            Scanner scan = new Scanner(fileIn);
            while (scan.hasNextLine()) {
                byte[] crypt = crypter.encode(scan.nextLine() + "\n", flag.key);
                byteWriter(flag.pathOut, crypt);
            }
            fileIn.close();
            System.out.println("Encoding completed");
            log.info("Encoding completed");
        }
        else {
            FileInputStream fis = new FileInputStream(flag.pathIn);
            byte[] data = fis.readAllBytes();
            String text = crypter.decode(data, flag.key);
            writer(flag.pathOut, text);
            fis.close();
            System.out.println("Decoding completed");
            log.info("Decoding completed");
        }
    }

    void byteWriter (String path, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(path, true);
        fos.write(data, 0, data.length);
        fos.close();
    }

    void writer (String path, String data) throws IOException {
        FileWriter fileOut = new FileWriter(path, true);
        fileOut.write(data);
        fileOut.close();
    }
}
