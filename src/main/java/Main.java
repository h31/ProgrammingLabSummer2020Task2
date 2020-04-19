import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Processing example = new Processing();
        example.checkInput(args);
        List<File> fileList = example.getListOfFiles();
        for (int j = 0; j < fileList.size(); j++) {
            Size element = new Size(fileList.get(j));
            System.out.print(example.necessarySizeOutput(element.getSize()) + element.getName());
            if (j != fileList.size() - 1) System.out.print("\n");
        }
        if (example.getC() & !example.getListOfFiles().isEmpty()) {
            System.out.print("\n");
            System.out.println(example.cFlagSize().trim());
        }
    }
}
