import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        flagManager flag = new flagManager();
        flag.parsing(args);
        fileManager file = new fileManager();
        file.reader(flag);
    }
}