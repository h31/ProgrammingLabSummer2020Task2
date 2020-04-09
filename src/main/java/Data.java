import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Data {
    private String name;
    private long size;

    Data(File file) {
        this.name = file.getPath();
        this.size = file.isDirectory() ? getDirectorySize(file) : file.length();
    }

    static long getDirectorySize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            for (File element : Objects.requireNonNull(file.listFiles())) {
                size += getDirectorySize(element);
            }
        } else {
            size += file.length();
        }
        return size;
    }

    static public String sumSize(Flags flag) {
        StringBuilder output = new StringBuilder();
        ArrayList<File> fileList = flag.getLisOfFiles();
        double res = fileList.stream().mapToDouble(element ->
                getDirectorySize(new File(String.valueOf(element)))).sum();
        if (flag.getH()) {
            if (flag.getSi()) return output.append(siFlagSize(res)).toString();
            return output.append(necessarySize(res)).toString();
        }
        if (flag.getSi()) {
            return output.append(String.format(Locale.US, "%.1f", res / 1000)).append("K").toString();
        }
        return output.append(String.format(Locale.US, "%.1f", res / 1024)).append("K").toString();
    }

    static private String necessarySize(double size) {
        double newSize = size;
        StringBuilder string = new StringBuilder();
        String[] firstChar = new String[]{"Б", "K", "M", "Г"};
        int count = 0;
        if (newSize >= 1024) {
            newSize /= 1024;
            count++;
        }
        return string.append(String.format(Locale.US, "%.1f", newSize)).append(firstChar[count]).toString();
    }

    static private String siFlagSize(double size) {
        double newSize = size;
        StringBuilder string = new StringBuilder();
        String[] firstChar = new String[]{"Б", "K", "M", "Г"};
        int count = 0;
        if (newSize >= 1000) {
            newSize /= 1000;
            count++;
        }
        return string.append(String.format(Locale.US, "%.1f", newSize)).append(firstChar[count]).toString();
    }

    String toStringOutput(Flags flag) {//вывожу строки размер + имя файлов,каталогов
        StringBuilder output = new StringBuilder();
        if (flag.getH()) {
            if (flag.getSi()) return output.append(siFlagSize(size)).append(" ").append(name).toString();
            return output.append(necessarySize(size)).append(" ").append(name).toString();
        }
        double newSize = size;
        if (flag.getSi()) {
            return output.append(String.format(Locale.US, "%.1f", newSize / 1000)).append("K ").append(name).toString();
        }
        return output.append(String.format(Locale.US, "%.1f", newSize / 1024)).append("K ").append(name).toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data1 = (Data) o;
        return size == data1.size &&
                Objects.equals(name, data1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }

    @Override
    public String toString() {
        return "Data{}";
    }
}

