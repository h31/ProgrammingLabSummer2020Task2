import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

class Flags {
    private boolean longType = false;
    private boolean humanType = false;
    private boolean reversedType = false;
    private boolean output = false;
    private FileOutputStream outputWriter;
    private File directory;

    void handlingArguments(String[] args) throws IOException {
        if (args.length < 1) throw new IllegalArgumentException();
        for (int i = 0; i != args.length; i++) {
            switch (args[i]) {
                case ("-l"):
                    setLongType(true);
                    break;
                case ("-h"):
                    setHumanType(true);
                    break;
                case ("-r"):
                    setReversedType(true);
                    break;
                case ("-o"):
                    if (i + 1 < args.length - 1) {
                        setOutputWriter(new FileOutputStream(args[i + 1]));
                        setOutput(true);
                        i++;
                        break;
                    }
                    throw new IllegalArgumentException("Wrong arguments. Example: ls [-l] [-h] [-r] [-o output.file] directory_or_file"); // Локализация
                default:
                    if (i == args.length - 1) {
                        setDirectory(new File(args[i]));
                        break;
                    }
                    throw new IllegalArgumentException("Wrong arguments. Example: ls [-l] [-h] [-r] [-o output.file] directory_or_file"); // Локализация

            }
        }
    }

    public boolean isLongType() {
        return longType;
    }

    public void setLongType(boolean longType) {
        this.longType = longType;
    }

    public boolean isHumanType() {
        return humanType;
    }

    public void setHumanType(boolean humanType) {
        this.humanType = humanType;
    }

    public boolean isReversedType() {
        return reversedType;
    }

    public void setReversedType(boolean reversedType) {
        this.reversedType = reversedType;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public FileOutputStream getOutputWriter() {
        return outputWriter;
    }

    public void setOutputWriter(FileOutputStream outputWriter) {
        this.outputWriter = outputWriter;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flags flags = (Flags) o;
        return longType == flags.longType &&
                humanType == flags.humanType &&
                reversedType == flags.reversedType &&
                output == flags.output &&
                Objects.equals(outputWriter, flags.outputWriter) &&
                directory.equals(flags.directory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(longType, humanType, reversedType, output, outputWriter, directory);
    }

    @Override
    public String toString() {
        return "Flags{" +
                "longType=" + longType +
                ", humanType=" + humanType +
                ", reversedType=" + reversedType +
                ", output=" + output +
                ", outputWriter=" + outputWriter +
                ", directory=" + directory +
                '}';
    }
}
