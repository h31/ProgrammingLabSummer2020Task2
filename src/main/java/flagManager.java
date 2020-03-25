import java.io.File;
import java.io.IOException;

public class flagManager {
    public boolean method;
    public int key;
    public String pathIn;
    public String pathOut;

    void checker (String[] args) throws IOException {
        String command = String.join(" ", args);
        if (args[0].equals("-h"))
            help(false);
        if (!command.matches("((-c)|(-d)) [a-zA-Z0-9]+ [a-zA-Z0-9/.-]+(.[a-z]+)( -o [a-zA-Z0-9/.]+(.[.a-z]+))?"))
            help(true);
        setMethod(args[0]);
        setKey(args[1]);
        setPathIn(args[2]);
        setPathOut(args);
    }

    void setMethod (String mode) {
        method = mode.equals("-c");
    }

    void setKey (String inputKey) {
        if (inputKey.matches("[0-9a-dA-D]+"))
            key = Integer.parseInt (inputKey, 16);
        else
            throw new IllegalArgumentException(inputKey);
    }

    void setPathIn (String path) {
        File fInput = new File(path);
        if (!fInput.exists() && fInput.isFile())
            throw new IllegalArgumentException(path);
        pathIn = path;
    }

    void setPathOut (String[] args) throws IOException {
        if (args.length > 3) {
            fileManager file = new fileManager();
            pathOut = file.creator(method, args[4]);
    }
        else {
            pathOut = args[2];
            fileManager file = new fileManager();
            pathOut = file.creator(method, pathOut);
        }
    }

    void help (boolean code) {
        if (code)
            System.out.println("Error in the use of commands.");
        System.out.println("Valid flags are:\n" +
                "-c encryption\n" +
                "-d decryption\n" +
                "This is followed by specifying the encryption key.\n" +
                "-o (optional) specify the location of the output file.\n" +
                "By default, output files are created in the directory of the incoming file." +
                "-h help");
        if (!code) {
            System.out.println("Implementation of ciphxor\n" +
                    "Version 1\n" +
                    "Mikhail Shomov, student of St. Petersburg Polytechnic University");
            System.exit(1);
        }
        else
            throw new IllegalArgumentException();

    }
}
