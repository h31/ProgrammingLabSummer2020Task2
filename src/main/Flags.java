import java.io.File;

class Flags {
    static boolean lFlag = false;
    static boolean hFlag = false;
    static boolean rFlag = false;
    static boolean oFlag = false;
    private static File output;
    private static File directory;


    static void setOutputFile(String outputFile) {
        if (new File(outputFile).exists()) Flags.output = new File(outputFile);
        else throw new IllegalArgumentException();
    }

    static void setDirectory(String directoryFile) {
        if (new File(directoryFile).exists()) Flags.directory = new File(directoryFile);
        else throw new IllegalArgumentException();
    }
}
