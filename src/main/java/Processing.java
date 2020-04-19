import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Processing {
    private boolean humanReadableHFlag = false;
    private boolean sumSizeCFlag = false;
    private boolean humanReadableSiFlag = false;
    private List<File> listOfFiles = new ArrayList<>();

    public void setH(boolean h) {
        this.humanReadableHFlag = h;
    }

    public boolean getH() {
        return humanReadableHFlag;
    }

    public void setC(boolean c) {
        this.sumSizeCFlag = c;
    }

    public boolean getC() {
        return sumSizeCFlag;
    }

    public void setSi(boolean si) {
        this.humanReadableSiFlag = si;
    }

    public boolean getSi() {
        return humanReadableSiFlag;
    }

    public List<File> getListOfFiles() {
        return listOfFiles;
    }

    public void checkInput(String[] args) {
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
                    getListOfFiles().add(new File(args[i]));
                    break;
                }
            }
        }
        if (listOfFiles.isEmpty()) throw  new NullPointerException("Вы не ввели имена файлов. Список имен пуст");
    }

    public String cFlagSize() {
        double res = 0;
        for (int g = 0; g < listOfFiles.size(); g++) {
            Size example = new Size(listOfFiles.get(g));
            res += example.getSize();
        }
        return necessarySizeOutput(res);
    }

    public String necessarySizeOutput(double size) {
        double newSize = size;
        String[] firstChar = new String[]{"Б", "K", "M", "Г"};
        int count = 0;
        int a = 1024;
        if (getH()) {
            if (getSi()) a = 1000;
            if (newSize >= a && count != 3) {
                newSize /= a;
                count++;
            }
            return String.format(Locale.US, "%.1f", newSize) + firstChar[count] + " ";
        }

        if (getSi()) {
            return String.format(Locale.US, "%.1f", newSize / 1000) + "K ";
        }
        return String.format(Locale.US, "%.1f", newSize / 1024) + "K ";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processing that = (Processing) o;
        return humanReadableHFlag == that.humanReadableHFlag &&
                sumSizeCFlag == that.sumSizeCFlag &&
                humanReadableSiFlag == that.humanReadableSiFlag &&
                Objects.equals(listOfFiles, that.listOfFiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(humanReadableHFlag, sumSizeCFlag, humanReadableSiFlag, listOfFiles);
    }

    @Override
    public String toString() {
        return "Processing{" +
                "humanReadableHFlag=" + humanReadableHFlag +
                ", sumSizeCFlag=" + sumSizeCFlag +
                ", humanReadableSiFlag=" + humanReadableSiFlag +
                ", listOfFiles=" + listOfFiles +
                '}';
    }
}

