import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Flags {
    private boolean h = false;
    private boolean c = false;
    private boolean si = false;
    private ArrayList<File> listOfFiles;


    void setH(boolean h) {
        this.h = h;
    }

    boolean getH() {
        return h;
    }

    void setC(boolean c) {
        this.c = c;
    }

    boolean getC() {
        return c;
    }

    void setSi(boolean si) {
        this.si = si;
    }

    boolean getSi() {
        return si;
    }

    ArrayList<File> getLisOfFiles() {
        return listOfFiles;
    }

    void setFile(ArrayList<File> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    void checkFlags(String[] args) {
        ArrayList<File> simple = new ArrayList<>();
        for (int i = 0; i != args.length; i++) {
            switch (args[i]) {
                case ("-h"):
                    setH(true);
                    break;
                case ("-c"):
                    setC(true);
                    break;
                case ("-si"):
                    setSi(true);
                    break;
                default:
                    simple.add(new File(args[i]));
                    break;
            }
        }
        setFile(simple);
    }

    static class Data1 {
        static private String name;
        static private long size;

        Data1(File file) {
            this.name = file.getAbsolutePath();
            this.size = file.isDirectory() ? getDirectorySize(file) : file.length();
        }

        static long getDirectorySize(File file) {
            long size = 0;
            if (file.isDirectory()) {
                for (File element : Objects.requireNonNull(file.listFiles())) {
                    size += getDirectorySize(element);
                }
            } else size += file.length();
            return size;
        }

        static public String sumSize(Flags flag) {
            StringBuilder output = new StringBuilder();
            double res = 0;
            ArrayList<File> fileList = flag.getLisOfFiles();
            for (int i = 0; i < fileList.size(); i++) {
                res += getDirectorySize(fileList.get(i));
            }
            if (flag.getH()) {
                if (flag.getSi()) return output.append(siFlagSize(res)).toString();
                return output.append(necessarySize(res)).toString();
            }
            if (flag.getSi()) {
                return output.append(String.format("%.1f", res / 1000)).append("K").toString();
            }
            return output.append(String.format("%.1f", res / 1024)).append("K").toString();
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
            return string.append(String.format("%.1f", newSize)).append(firstChar[count]).toString();
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
            return string.append(String.format("%.1f", newSize)).append(firstChar[count]).toString();
        }

        String toStringOutput(Flags flag) {//вывожу строки размер + имя файлов,каталогов
            File file = new File(name);
            if(file.exists()) {
            StringBuilder output = new StringBuilder();
            if (flag.getH()) {
                if (flag.getSi()) return output.append(siFlagSize(size)).append(" ").append(name).toString();
                return output.append(necessarySize(size)).append(" ").append(name).toString();
            }
            double newSize = size;
            if (flag.getSi()) {
                return output.append(String.format("%.1f", newSize / 1000)).append("K ").append(name).toString();
            }
            return output.append(String.format("%.1f", newSize / 1024)).append("K ").append(name).toString();
        }
            return "Данного файла или каталога не существует";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data1 data1 = (Data1) o;
            return size == data1.size &&
                    Objects.equals(name, data1.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, size);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flags flags = (Flags) o;
        return h == flags.h &&
                c == flags.c &&
                si == flags.si &&
                Objects.equals(listOfFiles, flags.listOfFiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(h, c, si, listOfFiles);
    }
}

