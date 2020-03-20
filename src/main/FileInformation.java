import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

class FileInformation {
    private byte permissions = 0b000;
    private final String lastModified;
    private long size;
    private final String name;

    FileInformation(File file) {
        this.name = file.getName();
        this.size = file.isDirectory() ? getDirectorySize(file) : file.length();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.lastModified = sdf.format(new Date(file.lastModified()));
        if (file.canExecute()) this.permissions |= Flags.canExecute;
        if (file.canWrite()) this.permissions |= Flags.canWrite;
        if (file.canRead()) this.permissions |= Flags.canRead;
    }

    private long getDirectorySize(File file) {
        long size = 0;
        for (File element : Objects.requireNonNull(file.listFiles())) {
            if (element.isDirectory()) size += getDirectorySize(element);
            size += element.length();
        }
        return size;
    }

    private String transformSize(long size) {
        double result = size;
        StringBuilder sb = new StringBuilder();
        String[] prefixArray = new String[]{"Б", "К", "М", "Г"};
        int counter = 0;
        while (result >= 1024) {
            result /= 1024;
            counter++;
        }
        sb.append(String.format("%.1f", result)).append(prefixArray[counter]);
        return sb.toString();
    }

    private String transformPermission(byte permissions) {
        StringBuilder sb = new StringBuilder();
        if ((permissions & Flags.canRead) != 0) sb.append("r");
        else sb.append("-");
        if ((permissions & Flags.canWrite) != 0) sb.append("w");
        else sb.append("-");
        if ((permissions & Flags.canExecute) != 0) sb.append("x");
        else sb.append("-");
        return sb.toString();
    }

    String toString(boolean hFlag) {
        if (hFlag)
            return transformPermission(this.permissions) + " " + this.lastModified + " " + transformSize(this.size) + " " + this.name;
        else
            return Integer.toBinaryString(this.permissions) + " " + this.lastModified + " " + this.size + " " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInformation that = (FileInformation) o;
        return permissions == that.permissions &&
                size == that.size &&
                lastModified.equals(that.lastModified) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissions, lastModified, size, name);
    }
}
