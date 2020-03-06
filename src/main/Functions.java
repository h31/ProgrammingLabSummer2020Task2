import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

class Functions {
    static void printDirectory() throws IOException {
        StringBuilder sb = new StringBuilder();
        File[] filesArray = arrayPreparation();
        if (Flags.lFlag) {
            for (int i = 0; i != filesArray.length; i++) {
                sb.append(new FileInformation(filesArray[i]).toString());
                if (i != filesArray.length - 1) sb.append("\n");
            }
        } else {
            for (int i = 0; i != filesArray.length; i++) {
                sb.append(filesArray[i].getName());
                if (i != filesArray.length - 1) sb.append("\n");
            }
        }
        if (Flags.oFlag) {
            BufferedWriter writer = new BufferedWriter(Flags.output);
            writer.write(sb.toString());
            writer.close();
        } else {
            System.out.println(sb.toString());
        }
    }

    private static File[] arrayPreparation() { // Обработка массива в зависимости от наличия флага -r
        File[] filesArray = Flags.directory.listFiles();
        if (Objects.requireNonNull(filesArray).length != 0) {
            Arrays.sort(filesArray);
            if (Flags.rFlag) Collections.reverse(Arrays.asList(filesArray));
        }
        return filesArray;
    }
}
