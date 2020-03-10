import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Flags {
    static final byte canRead = 0b100;
    static final byte canWrite = 0b010;
    static final byte canExecute = 0b001;
    private boolean lFlag = false;
    private boolean hFlag = false;
    private boolean rFlag = false;
    private boolean oFlag = false;
    private FileWriter output;
    private File directory;

    void checkArguments(String[] args) throws IOException {
        for (int i = 0; i != args.length; i++) {
            switch (args[i]) {
                case ("-l"):
                    setLFlag(true);
                    break;
                case ("-h"):
                    setHFlag(true);
                    break;
                case ("-r"):
                    setRFlag(true);
                    break;
                case ("-o"):
                    if (i + 1 != args.length - 1) {
                        setOutput(new FileWriter(args[i + 1]));
                        setOFlag(true);
                        i++;
                    }
                    break;
                default:
                    if (i == args.length - 1) setDirectory(new File(args[i]));
                    else throw new IllegalArgumentException();
                    break;
            }
        }
    }

    boolean getLFlag() {
        return lFlag;
    }

    private void setLFlag(boolean lFlag) {
        this.lFlag = lFlag;
    }

    boolean getHFlag() {
        return hFlag;
    }

    private void setHFlag(boolean hFlag) {
        this.hFlag = hFlag;
    }

    boolean getRFlag() {
        return rFlag;
    }

    private void setRFlag(boolean rFlag) {
        this.rFlag = rFlag;
    }

    boolean getOFlag() {
        return oFlag;
    }

    private void setOFlag(boolean oFlag) {
        this.oFlag = oFlag;
    }

    FileWriter getOutput() {
        return output;
    }

    private void setOutput(FileWriter output) {
        this.output = output;
    }

    File getDirectory() {
        return directory;
    }

    private void setDirectory(File directory) {
        this.directory = directory;
    }
}
