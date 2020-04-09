import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class Flags {
    private boolean h = false;
    private boolean c = false;
    private boolean si = false;
    private ArrayList<File> listOfFiles = new ArrayList<>();


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
        for (int i = 0; i != args.length; i++) {
            switch (args[i]) {
                case ("-h"):
                    setH(true);
                    break;
                case ("-c"):
                    setC(true);
                    break;
                case ("--si"):
                    setSi(true);
                    break;
                default: {
                    if (!new File(args[i]).exists()) {
                        System.out.println("Данного файла или каталога " + args[i] + " не существует");
                        break;
                    }
                    getLisOfFiles().add(new File(args[i]));
                    break;
                }
            }
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

    @Override
    public String toString() {
        return "Flags{" +
                "h=" + h +
                ", c=" + c +
                ", si=" + si +
                ", listOfFiles=" + listOfFiles +
                '}';
    }
}

