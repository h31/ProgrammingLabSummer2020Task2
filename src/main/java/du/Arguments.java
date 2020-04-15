package du;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Arguments {
    private Flags flags = new Flags(false, false, false);
    private List<String> files = new ArrayList<>();

    Arguments(String[] args) {
        for (int i = 0; i != args.length; i++) {
            switch (args[i]) {
                case ("-h"):
                    if (files.size() != 0) System.exit(160);
                    flags.setHFlag(true);
                    break;
                case ("-c"):
                    if (files.size() != 0) System.exit(160);
                    flags.setCFlag(true);
                    break;
                case ("-si"):
                    if (files.size() != 0) System.exit(160);
                    flags.setSiFlag(true);
                    break;
                default:
                    files.add(args[i]);
                    break;
            }
        }
        if (files.isEmpty()) System.exit(160);
    }

    Flags getFlags() {
        return flags;
    }

    List<String> getFiles() {
        return files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arguments arguments = (Arguments) o;
        return flags == arguments.flags &&
                Objects.equals(files, arguments.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flags, files);
    }

    @Override
    public String toString() {
        return "flags=" + flags + ", files=" + files;

    }
}