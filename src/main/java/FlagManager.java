import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FlagManager {
    public boolean approach;
    public long key;
    public String pathIn;
    public String pathOut;
    MessageManager msg = new MessageManager();
    void parsing(String[] args) throws Exception {
        String command = String.join(" ", args);
        msg.basicMsg(0, command);
        int length = args.length;
        boolean custom = false;
        if (args[0].matches("((-c)|(-d))"))
            setApproach(args[0]);
        else if (args[0].matches("(-h)"))
            msg.error(0, null);
        else
            msg.error(1, command);
        if (length > 2) {
            setKey(args[1]);
            for (int i = 2; i < args.length; i++)
                if (args[i].equals("-o")) {
                    length = i;
                    custom = true;
                }
            StringBuilder path = new StringBuilder();
            for (int i = 2; i < length; i++)
                path.append(args[i]).append(" ");
            setPathIn(path.toString().trim());
            path = new StringBuilder();
            if (custom) {
                for (int i = length + 1; i < args.length; i++)
                    path.append(args[i]).append(" ");
                pathOut = path.toString().trim();
            }
            else
                pathOut = pathIn;
        }
        else
            msg.error(1, command);
        setPathOut(custom);
    }

    void setApproach(String mode) {
        approach = mode.equals("-c");
    }

    void setKey (String inputKey) throws Exception {
        try {
            key = Integer.parseInt (inputKey, 16);
        }
        catch (NumberFormatException e) {
            msg.error(2, inputKey);
        }
    }

    void setPathIn (String path) throws Exception {
        Path fInput = Paths.get(path);
        if (Files.notExists(fInput.toAbsolutePath()) || !Files.isRegularFile(fInput.toAbsolutePath()))
            msg.error(3, fInput.toAbsolutePath().toString());
        pathIn = path;
    }

    void setPathOut (boolean custom) throws Exception {
        FileManager file = new FileManager();
        pathOut = file.creator(approach, pathOut, custom);
    }
}
