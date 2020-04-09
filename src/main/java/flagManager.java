import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class flagManager {
    public boolean approach;
    public int key;
    public String pathIn;
    public String pathOut;
    Logger log = LogManager.getLogger(flagManager.class.getName());

    void parsing(String[] args) throws IOException {
        String command = String.join(" ", args);
        log.info("The program started with arguments: " + command);
        if (args[0].equals("-h"))
            help(false);
        if (!command.matches("((-c)|(-d)) [a-zA-Z0-9]+ [.\\-/a-zA-Zа-яёА-ЯЁ0-9]+(\\.[a-z]+)( -o [a-zA-Z0-9а-яёА-ЯЁ./\\-]+)?"))
            help(true);
        setApproach(args[0]);
        setKey(args[1]);
        setPathIn(args[2]);
        setPathOut(args);
    }

    void setApproach(String mode) {
        approach = mode.equals("-c");
    }

    void setKey (String inputKey) {
        if (inputKey.matches("[0-9a-fA-F]+"))
            key = Integer.parseInt (inputKey, 16);
        else {
            System.err.println("Incorrect cipher key. The key is set in hexadecimal format.");
            log.error("Incorrect cipher key");
            throw new IllegalArgumentException(inputKey);
        }
    }

    void setPathIn (String path) throws FileNotFoundException {
        File fInput = new File(path);
        if (!fInput.exists() || !fInput.isFile()) {
            log.error("The input file does not exist");
            System.err.println("The input file does not exist");
            throw new FileNotFoundException(path);
        }
        pathIn = path;
    }

    void setPathOut (String[] args) throws IOException {
        if (args.length > 3) {
            fileManager file = new fileManager();
            pathOut = file.creator(approach, args[4], true);
    }
        else {
            pathOut = args[2];
            fileManager file = new fileManager();
            pathOut = file.creator(approach, pathOut, false);
        }
    }

    void help (boolean error) {
        if (error) {
            System.err.println("Error in the use of commands.");
            log.error("Invalid arguments");
        }
        System.out.println("Valid flags are:\n" +
                "-c encryption\n" +
                "-d decryption\n" +
                "This is followed by specifying the encryption key. The key is set in hexadecimal format.\n" +
                "-o (optional) specify the location of the output file.\n" +
                "By default, output files are created in the directory of the incoming file." +
                "-h help");
        if (!error) {
            System.out.println("Implementation of ciphxor\n" +
                    "Version 1.0\n" +
                    "Mikhail Shomov, student of St. Petersburg Polytechnic University");
            log.info("Assistance requested");
            System.exit(0);
        }
        else
            throw new IllegalArgumentException();

    }
}
