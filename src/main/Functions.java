import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Collections;

class Functions {
    static OutputStream printDirectory(Flags flags, OutputStream stream) throws IOException {
        if (flags.getDirectory().isFile()) {
            stream.write(new FileInformation(flags.getDirectory()).toString(flags.isHumanType()).getBytes());
            return stream;
        }
        File[] filesArray = arrayPreparation(flags.getDirectory().listFiles(), flags.isReversedType());
        if (flags.isLongType()) {
            for (int i = 0; i != filesArray.length; i++) {
                stream.write(new FileInformation(filesArray[i]).toString(flags.isHumanType()).getBytes());
                if (i != filesArray.length - 1) stream.write("\n".getBytes());
            }
        } else {
            for (int i = 0; i != filesArray.length; i++) {
                stream.write(filesArray[i].getName().getBytes());
                if (i != filesArray.length - 1) stream.write("\n".getBytes());
            }
        }
        return stream;
    }

    private static File[] arrayPreparation(File[] filesArray, boolean rFlag) throws NoSuchFileException { // Обработка массива в зависимости от наличия флага -r
        if (filesArray == null) throw new NoSuchFileException("Directory has not been found."); // Локализация будет тут
        Arrays.sort(filesArray);
        if (rFlag) Collections.reverse(Arrays.asList(filesArray));
        return filesArray;
    }
}
