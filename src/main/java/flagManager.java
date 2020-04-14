import java.io.IOException;
import java.nio.file.*;

public class flagManager {
    public boolean approach;
    public long key;
    public String pathIn;
    public String pathOut;
    messageManager msg = new messageManager();
    void parsing(String[] args) throws IOException {
        String command = String.join(" ", args);
        msg.basicMsg(0, command);
        int mark = args.length;
        boolean custom = false;
        if (args[0].matches("((-c)|(-d))"))
            setApproach(args[0]);
        else if (args[0].matches("(-h)"))
            msg.error(0, null);
        else
            msg.error(1, command);
        if (args.length > 2) {
            setKey(args[1]);
            for (int i = 2; i < args.length; i++)
                if (args[i].equals("-o")) {
                    mark = i;
                    custom = true;
                }
            StringBuilder path = new StringBuilder();
            for (int i = 2; i < mark; i++)
                path.append(args[i]).append(" ");
            setPathIn(path.toString().trim());
            path = new StringBuilder();
            if (custom) {
                for (int i = mark + 1; i < args.length; i++)
                    path.append(args[i]).append(" ");
                pathOut = path.toString().trim();
            } else
                pathOut = pathIn;
            setPathOut(custom);
        }
    }

    void setApproach(String mode) {
        approach = mode.equals("-c");
    }

    void setKey (String inputKey) throws IOException {
        if (inputKey.matches("[0-9a-fA-F]+"))
            key = Integer.parseInt (inputKey, 16);
        else {
            msg.error(2, inputKey);
        }
    }

    void setPathIn (String path) throws IOException {
        Path fInput = Paths.get(path);
        if (Files.notExists(fInput.toAbsolutePath()) || !Files.isRegularFile(fInput.toAbsolutePath())) {
            msg.error(3, fInput.toAbsolutePath().toString());
        }
        pathIn = path;
    }

    void setPathOut (boolean custom) throws IOException {
        fileManager file = new fileManager();
        pathOut = file.creator(approach, pathOut, custom);
    }
}
