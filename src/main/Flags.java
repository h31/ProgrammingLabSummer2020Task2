import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

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
        if (args.length < 1) throw new IllegalArgumentException();
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
                    if (i + 1 < args.length - 1) {
                        setOutput(new FileWriter(args[i + 1]));
                        setOFlag(true);
                        i++;
                        break;
                    }
                    throw new IllegalArgumentException();
                default:
                    if (i == args.length - 1) {
                        setDirectory(new File(args[i]));
                        break;
                    }
                    throw new IllegalArgumentException();

            }
        }
    }

    boolean getLFlag() {
        return lFlag;
    }

    void setLFlag(boolean lFlag) {
        this.lFlag = lFlag;
    }

    boolean getHFlag() {
        return hFlag;
    }

    void setHFlag(boolean hFlag) {
        this.hFlag = hFlag;
    }

    boolean getRFlag() {
        return rFlag;
    }

    void setRFlag(boolean rFlag) {
        this.rFlag = rFlag;
    }

    boolean getOFlag() {
        return oFlag;
    }

    void setOFlag(boolean oFlag) {
        this.oFlag = oFlag;
    }

    FileWriter getOutput() {
        return output;
    }

    void setOutput(FileWriter output) {
        this.output = output;
    }

    File getDirectory() {
        return directory;
    }

    void setDirectory(File directory) {
        this.directory = directory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flags flags = (Flags) o;
        return lFlag == flags.lFlag &&
                hFlag == flags.hFlag &&
                rFlag == flags.rFlag &&
                oFlag == flags.oFlag &&
                Objects.equals(getOutput(), flags.getOutput()) &&
                getDirectory().equals(flags.getDirectory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(lFlag, hFlag, rFlag, oFlag, getOutput(), getDirectory());
    }
}
