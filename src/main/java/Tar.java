import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.StandardOpenOption.*;

class Parse {
    public static void main(String[] args) throws IOException {
        for (String arg : args) {
            ArrayList<String> newargs = new ArrayList<>();
            Collections.addAll(newargs, args);
            if (arg.equals("-out")) {
                newargs.remove("-out");
                Path out = Paths.get(newargs.get(newargs.size() - 1));
                Tar.out(newargs, out);
                break;
            }
            if (arg.equals("-u")) {
                newargs.remove("-u");
                Path out = Paths.get(newargs.get(newargs.size() - 1));
                Tar.u(out);
                break;
            }
        }
    }

}

public class Tar {
    public static void out(ArrayList<String> args, Path out) throws IOException {
        try (BufferedWriter fin = newBufferedWriter(out, WRITE, CREATE, TRUNCATE_EXISTING)) {
            int[] a = new int[args.size() - 1];
            for (int i = 0; i < args.size() - 1; i++) {
                for (String line : readAllLines(Paths.get(args.get(i)))) {
                    fin.write(line + "\n");
                    a[i]++;
                }
            }
            for (int i = 0; i < args.size() - 1; i++) {
                fin.write(args.get(i) + " " + a[i] + "\n");
            }
            fin.write(Integer.toString(args.size() - 1));
        }
    }

    public static void u(Path out) throws IOException {
        List<String> fin = readAllLines(out);
        int j = Integer.parseInt(fin.get(fin.size() - 1));
        int start = 0;
        for (int i = j; i >= 1; i--) {
            String[] a = fin.get(fin.size() - 1 - i).split(" ");
            try (BufferedWriter f = newBufferedWriter(Paths.get(a[0]), WRITE, CREATE, TRUNCATE_EXISTING);) {
                int parse = Integer.parseInt(a[1]);
                for (int l = start; l <= start + parse - 1; l++) {
                    f.write(fin.get(l) + "\n");
                }
                start += Integer.parseInt(a[1]);
            }
        }
    }
}