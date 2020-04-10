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
        boolean stack = false;
        String buffer = "";
        boolean custum = false;
        for (int i = 0; i < args.length; i++) {
            if (i == 0 && args[i].matches("((-c)|(-d))"))
                setApproach(args[i]);
            else if (i == 0 && args[i].matches("(-h)"))
                help(false);
            else if (i == 0 && !args[i].matches("((-c)|(-d))"))
                help(true);
            if (i == 1 && args[i].matches("[a-zA-Z0-9]+"))
                setKey(args[i]);
            else if (i > 1 && !args[i].startsWith("'") && !stack && pathIn == null)
                setPathIn(args[i]);
            else if (i > 1 && args[i].startsWith("'") && !stack && pathIn == null) {
                stack = true;
                buffer = args[i];
            }
            else if (!args[i].endsWith("'") && stack && pathIn == null)
                buffer += " " + args[i];
            else if (i > 1 && args[i].endsWith("'") && stack) {
                stack = false;
                buffer += " " + args[i];
                setPathIn(buffer.substring(1, buffer.length() - 1));
                buffer = "";
            }
            else if (args[i].equals("-o") && !custum && i != args.length - 1)
                custum = true;
            else if (custum && !args[i].startsWith("'")) {
                pathOut = args[i];
                setPathOut(true);
            }
            else if (custum && args[i].startsWith("'")){
                stack = true;
                buffer = args[i];
            }
            else if (!args[i].endsWith("'") && custum && stack && pathIn == null)
                buffer += " " + args[i];
            else if (custum && args[i].endsWith("'")){
                stack = false;
                buffer += " " + args[i];
                pathOut = buffer;
                setPathOut(true);
            }
            if (!custum && i == args.length - 1){
                pathOut = pathIn;
                setPathOut(false);
            }
        }
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
        if (Files.notExists(fInput) || !Files.isRegularFile(fInput)) {
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
