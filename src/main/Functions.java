import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

class Functions {
    static String printDirectory(Flags flags) {
        if (flags.getDirectory().isFile()) {
            return new FileInformation(flags.getDirectory()).toString(flags.getHFlag());
        }
        StringBuilder sb = new StringBuilder();
        File[] filesArray = arrayPreparation(flags.getDirectory().listFiles(), flags.getRFlag());
        if (filesArray == null) return "";
        if (flags.getLFlag()) {
            for (int i = 0; i != filesArray.length; i++) {
                sb.append(new FileInformation(filesArray[i]).toString(flags.getHFlag()));
                if (i != filesArray.length - 1) sb.append("\n");
            }
        } else {
            for (int i = 0; i != filesArray.length; i++) {
                sb.append(filesArray[i].getName());
                if (i != filesArray.length - 1) sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static File[] arrayPreparation(File[] filesArray, boolean rFlag) { // Обработка массива в зависимости от наличия флага -r
        if (Objects.requireNonNull(filesArray).length != 0) {
            Arrays.sort(filesArray);
            if (rFlag) Collections.reverse(Arrays.asList(filesArray));
        }
        return filesArray;
    }
}
