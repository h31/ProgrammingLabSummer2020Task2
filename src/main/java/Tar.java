import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.Files.readAllLines;
import static java.nio.file.StandardOpenOption.*;


public class Tar {
    public static void main(String[] args) throws IOException {
        Path out = Paths.get(args[args.length - 1]);
        if (args[args.length - 2].equals("-out")) {
            BufferedWriter fin = newBufferedWriter(out, WRITE, CREATE, TRUNCATE_EXISTING);
            fin.write(Integer.toString(args.length - 2));
            for (int i = 0; i < args.length - 2; i++) {
                fin.write("\n" + args[i] + " " + readAllLines(Paths.get(args[i])).size());
            }
            for (int i = 0; i < args.length - 2; i++) {
                for (String line : readAllLines(Paths.get(args[i]))) {
                    fin.write("\n" + line);
                }
            }
            fin.close();
        }
        if (args[0].equals("-u")) {
            List<String> fin = readAllLines(out);
            int j = Integer.parseInt(fin.get(0));
            int start = j+1;
            for (int i = 1; i <= j; i++) {
                String[] a = fin.get(i).split(" ");
                BufferedWriter f = newBufferedWriter(Paths.get(a[0]), WRITE, CREATE, TRUNCATE_EXISTING);
                for (int l = start; l<=start+Integer.parseInt(a[1])-1;l++ ) {
                    f.write(fin.get(l)+"\n");
                }
                start+=Integer.parseInt(a[1]);
                f.close();
            }
        }
    }
}
