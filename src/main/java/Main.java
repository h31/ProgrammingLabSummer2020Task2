import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        Flags flag = new Flags();
        flag.checkFlags(args);
        ArrayList<File> fileList = flag.getLisOfFiles();
        BufferedWriter writer = new BufferedWriter(new FileWriter("output"));
        for (int j = 0; j < fileList.size(); j++) {
            writer.write((new Data(fileList.get(j))).toStringOutput(flag));
            if (j != fileList.size() - 1) writer.newLine();
        }
        if (flag.getC()) {
            writer.newLine();
            writer.write(Data.sumSize(flag));
        }
        writer.close();
    }
}
