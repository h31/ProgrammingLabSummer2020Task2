import java.io.BufferedWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) throw new IllegalArgumentException();
        Flags flags = new Flags();
        flags.checkArguments(args);
        String output = Functions.printDirectory(flags);
        if (flags.getOFlag()) {
            BufferedWriter writer = new BufferedWriter(flags.getOutput());
            writer.write(output);
            writer.close();
        } else {
            System.out.println(output);
        }
    }
}
