import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileAlreadyExistsException;


public class messageManager {
    Logger log = LogManager.getLogger(messageManager.class.getName());

    void basicMsg (int code, String remark) {
        switch (code){
            case (0):
                log.info("The program started with arguments: " + remark);
                break;
            case (1):
                System.out.println("Encoding completed");
                log.info("Encoding completed");
                break;
            case (2):
                System.out.println("Decoding completed");
                log.info("Decoding completed");
                break;
        }
    }

    void error (int code, String remark)  throws IOException {
        String help = "Valid flags are:\n" +
                "-c encryption\n" +
                "-d decryption\n" +
                "This is followed by specifying the encryption key. The key is set in hexadecimal format.\n" +
                "-o (optional) specify the location of the output file.\n" +
                "By default, output files are created in the directory of the incoming file.\n" +
                "-h help";
        String author = "\nImplementation of ciphxor\n" +
                "Version 1.0\n" +
                "Mikhail Shomov(mikle@shomov.spb.ru), student of St. Petersburg Polytechnic University";
        switch (code) {
            case (0):
                System.out.println(help + author);
                break;
            case (1):
                log.error("Invalid arguments " + remark);
                System.err.println("Error in the use of commands. \n" + help);
                throw new IllegalArgumentException();
            case (2):
                log.error("Incorrect cipher key " + remark);
                System.err.println("Incorrect cipher key. The key is set in hexadecimal format.");
                throw new IllegalArgumentException();
            case (3):
                log.error("The input file does not exist " + remark);
                System.err.println("The input file does not exist");
                throw new FileNotFoundException();
            case (4):
                log.error("File " + remark + " already exists");
                throw new FileAlreadyExistsException("A file with this name already exists in this directory. Please use the -o argument.");
            case (5):
                log.error("Access to the folder " + remark + " is forbidden");
                throw new AccessDeniedException("Access to the folder " + remark + " is forbidden");

        }
    }

    void attention (int code, String remark) {
        switch (code) { //невзирая на предупреждения IDEA, принял решение оставить контсрукцию switch, для возможности масштабирования
            case (1):
                System.out.println("Attention! You are trying to decrypt a file without the .crp extension. Possible failure.");
                log.warn("Attempt to decrypt non .crp " + remark);
        }
    }
}
