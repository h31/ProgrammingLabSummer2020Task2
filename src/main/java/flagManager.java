import java.io.File;
import java.io.IOException;

public class flagManager {
    public boolean method;
    public String key;
    public String pathIn;
    public String pathOut;

    void checker (String[] args) throws IOException {
        String command = String.join(" ", args);
        System.out.println(command);
        if (!command.matches("((-c)|(-d)) [a-zA-Z0-9]+ [a-zA-Z/.]+(.[a-z]+)( -o [a-zA-Z/.]+(.[a-z]+))?"))
            throw new IllegalArgumentException();

        setMethod(args[0]);
        key = args[1];
        setPathIn(args[2]);
        setPathOut(args);
    }
    void setMethod (String mode) {
        if (mode.equals("-c")) {
            System.out.println("Encryption mode");
            method = true;
        }
        else {
            System.out.println("Decryption mode");
            method = false;
        }
    }

    void setPathIn (String path) {
        File fInput = new File(path);
        if (!fInput.exists() && fInput.isFile())
            throw new IllegalArgumentException(path);
        pathIn = path;
    }

    void setPathOut (String[] args) throws IOException {
        if (args.length > 3) {
            pathOut = args[4];
        }
        else {
            pathOut = args[2];
            fileManager file = new fileManager();
            pathOut = file.creator(method, pathOut);
        }
        System.out.println("Destination " + pathOut);
    }

}
