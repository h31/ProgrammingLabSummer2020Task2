import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) throws IOException {
        Flags flags = new Flags();
        flags.handlingArguments(args);
        PrintStream stream;
        if (flags.isOutput()) {
            stream = new PrintStream(flags.getOutputWriter());
        } else {
            stream = new PrintStream(System.out);
        }
        Functions.printDirectory(flags, stream);
    }
}
