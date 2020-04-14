import java.io.*;
import java.nio.file.*;

class fileManager {
    messageManager msg = new messageManager();
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
                    msg.attention(1, fOut.toString());
                }
            }
        try {
            Files.createFile(fOut);
        }
        catch (FileAlreadyExistsException e) {
            msg.error(4, fOut.toString());
        }
        catch (AccessDeniedException e) {
            msg.error(5, fOut.getParent().toString());
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
            msg.basicMsg(1, null);
        }
        else {
            FileInputStream fis = new FileInputStream(flag.pathIn);
            byte[] data = fis.readAllBytes();
            String text = crypter.decode(data, flag.key);
            writer(flag.pathOut, text, null);
            fis.close();
            msg.basicMsg(2, null);
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
