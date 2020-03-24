import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        flagManager flag = new flagManager();
        flag.checker(args);
        fileManager file = new fileManager();
        file.creator(flag);
        file.reader(flag);
    }
}