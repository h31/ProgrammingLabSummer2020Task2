import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.*;

class Flags {
    private boolean longType = false;
    private boolean humanType = false;
    private boolean reversedType = false;
    private boolean output = false;
    private FileOutputStream outputWriter;
    private File directory;
    private ResourceBundle resource = ResourceBundle.getBundle("resources", Locale.getDefault());

    void handlingArguments(String[] args) throws FileNotFoundException {
        if (args.length < 1) throw new IllegalArgumentException(resource.getString("args_error"));
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
                    throw new IllegalArgumentException(resource.getString("args_error"));
                default:
                    if (i == args.length - 1) {
                        setDirectory(new File(args[i]));
                        break;
                    }
                    throw new IllegalArgumentException(resource.getString("args_error")); // Локализация

            }
        }
    }
    /**
     * Метод заполнябщий поток информацией о файле/каталоге
     *
     * @param stream - любой поток наследуемый от OutputStream
     * @throws IOException - при отсутствии файла/каталога
     */
    OutputStream printDirectory(OutputStream stream) throws IOException {
        if (getDirectory().isFile()) {
            stream.write(new FileInformation(getDirectory()).toString(isHumanType()).getBytes());
            return stream;
        }
        File[] filesArray = arrayPreparation(getDirectory().listFiles(), isReversedType());
        if (isLongType()) {
            for (int i = 0; i != filesArray.length; i++) {
                stream.write(new FileInformation(filesArray[i]).toString(isHumanType()).getBytes());
                if (i != filesArray.length - 1) stream.write("\n".getBytes());
            }
        } else {
            for (int i = 0; i != filesArray.length; i++) {
                stream.write(filesArray[i].getName().getBytes());
                if (i != filesArray.length - 1) stream.write("\n".getBytes());
            }
        }
        return stream;
    }
    private File[] arrayPreparation(File[] filesArray, boolean rFlag) throws NoSuchFileException {
        if (filesArray == null) throw new NoSuchFileException(resource.getString("dir_error"));
        Arrays.sort(filesArray);
        if (rFlag) Collections.reverse(Arrays.asList(filesArray));
        return filesArray;
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

    public ResourceBundle getResource() {
        return resource;
    }

    public void setResource(ResourceBundle resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flags flags = (Flags) o;
        return isLongType() == flags.isLongType() &&
                isHumanType() == flags.isHumanType() &&
                isReversedType() == flags.isReversedType() &&
                isOutput() == flags.isOutput() &&
                Objects.equals(getOutputWriter(), flags.getOutputWriter()) &&
                getDirectory().equals(flags.getDirectory()) &&
                getResource().equals(flags.getResource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLongType(), isHumanType(), isReversedType(), isOutput(), getOutputWriter(), getDirectory(), getResource());
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
                ", resource=" + resource +
                '}';
    }
}
