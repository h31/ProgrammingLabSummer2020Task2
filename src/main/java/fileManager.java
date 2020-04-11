import java.io.*;
import java.nio.file.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class fileManager {
    Logger log = LogManager.getLogger(flagManager.class.getName());

    String creator (boolean approach, String pathOut, boolean custom) throws IOException {
        Path fOut = Paths.get(pathOut);
        if (custom)
            fOut = Paths.get(pathOut);
        else
            if (approach)
                fOut = Paths.get(fOut.toString() + ".crp");
            else {
                if (fOut.getFileName().toString().contains(".crp"))
                    fOut = Paths.get(pathOut.substring(0, pathOut.lastIndexOf(".")));
                else {
                    fOut = Paths.get(pathOut + ".txt");
                    System.out.println("Attention! You are trying to decrypt a file without the .crp extension. Possible failure.");
                    log.warn("Attempt to decrypt non .crp " + fOut);
                }
            }
        try {
            Files.createFile(fOut);
        }
        catch (FileAlreadyExistsException e) {
            System.err.println("A file with this name already exists in this directory. Please use the -o argument.");
            log.error("File" + fOut + "already exists");
            throw e;
        }
        catch (AccessDeniedException e) {
            System.err.println("Access to the folder is forbidden");
            log.error("Access to the folder " + fOut.getParent() + " is forbidden");
            throw e;
        }
        return fOut.toString();
    }

    void reader (flagManager flag) throws IOException {
        Crypto crypter = new Crypto();
        if (flag.approach) {
            Path path = Paths.get(flag.pathIn);
            BufferedReader br = Files.newBufferedReader(path);
            String line;
            while((line = br.readLine()) != null){
                byte[] crypt = crypter.encode(line + "\n", flag.key);
                writer(flag.pathOut, null, crypt);
            }
            br.close();
            System.out.println("Encoding completed");
            log.info("Encoding completed");
        }
        else {
            FileInputStream fis = new FileInputStream(flag.pathIn);
            byte[] data = fis.readAllBytes();
            String text = crypter.decode(data, flag.key);
            writer(flag.pathOut, text, null);
            fis.close();
            System.out.println("Decoding completed");
            log.info("Decoding completed");
        }
    }

    void writer (String path, String dataString,  byte[] dataByte) throws IOException {
        if (dataByte == null) {
            FileWriter fileOut = new FileWriter(path, true);
            fileOut.write(dataString);
            fileOut.close();
        }
        else {
            FileOutputStream fos = new FileOutputStream(path, true);
            fos.write(dataByte, 0, dataByte.length);
            fos.close();
        }
    }
}
