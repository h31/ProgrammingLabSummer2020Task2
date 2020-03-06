import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Flags {
    static final byte canRead = 0b100;
    static final byte canWrite = 0b010;
    static final byte canExecute = 0b001;
    static boolean lFlag = false;
    static boolean hFlag = false;
    static boolean rFlag = false;
    static boolean oFlag = false;
    static FileWriter output;
    static File directory;

    static void setOutputFile(String outputFile) throws IOException {
        Flags.output = new FileWriter(outputFile);
    }

    static void setDirectory(String directoryFile) {
        Flags.directory = new File(directoryFile);
    }
}
