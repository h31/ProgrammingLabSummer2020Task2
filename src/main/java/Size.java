import java.io.File;
import java.util.Objects;

public class Size {
    private String name;
    private long size;

    Size(File file) {
        name = file.getPath();
        size = file.isDirectory() ? getDirectorySize(file) : file.length();
    }

    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public static long getDirectorySize(File file) {
        long size = 0;
        int a = 0;
        if (file.isDirectory() & a != 30) {
            for (File element : Objects.requireNonNull(file.listFiles())) {
                size += getDirectorySize(element);
                a++;
            }
        } else {
            size += file.length();
        }
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Size size1 = (Size) o;
        return size == size1.size &&
                name.equals(size1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }

    @Override
    public String toString() {
        return "Size{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}

