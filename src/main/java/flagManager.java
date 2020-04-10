import java.nio.file.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class flagManager {
    public boolean approach;
    public long key;
    public String pathIn;
    public String pathOut;
    Logger log = LogManager.getLogger(flagManager.class.getName());

    void parsing(String[] args) throws IOException {
        int mark = args.length;
        boolean custom = false;
        if (args[0].matches("((-c)|(-d))"))
            setApproach(args[0]);
        else
            help(!args[0].matches("(-h)"));
        if (args[1].matches("[a-zA-Z0-9]+"))
            setKey(args[1]);
        for (int i = 2; i < args.length; i++)
            if (args[i].equals("-o")) {
                mark = i;
                custom = true;
            }
        String path = "";
        for (int i = 2; i < mark; i++)
            path += args[i] + " ";
        setPathIn(path.trim());
        path = "";
        if (custom) {
            for (int i = mark + 1; i < args.length; i++)
                path += args[i] + " ";
            pathOut = path.trim();
        }
        else
            pathOut = pathIn;
        setPathOut(custom);
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
        Path fInput = Paths.get(path);
        if (Files.notExists(fInput.toAbsolutePath()) || !Files.isRegularFile(fInput.toAbsolutePath())) {
            log.error("The input file does not exist");
            System.err.println("The input file does not exist");
            throw new FileNotFoundException(path);
        }
        pathIn = path;
    }

    void setPathOut (boolean custom) throws IOException {
        fileManager file = new fileManager();
        pathOut = file.creator(approach, pathOut, custom);
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
