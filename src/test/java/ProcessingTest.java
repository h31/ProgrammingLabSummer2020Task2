import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessingTest {
    Processing simple = new Processing();

    @Test
    void setH() {
        assertTrue(() -> {
            simple.setH(true);
            return simple.getH();
        });
    }

    @Test
    void getH() {
        assertFalse(simple.getH());
    }

    @Test
    void setC() {
        assertTrue(() -> {
            simple.setC(true);
            return simple.getC();
        });
    }

    @Test
    void getC() {
        assertFalse(simple.getC());
    }

    @Test
    void setSi() {
        assertTrue(() -> {
            simple.setSi(true);
            return simple.getSi();
        });
    }

    @Test
    void getSi() {
        assertFalse(simple.getSi());
    }

    Processing createProcessing(String[] args){
        Processing element = new Processing();
        element.checkInput(args);
        return element;
    }

    @Test
    void getListOfFiles() {
        assertEquals(Arrays.asList(new File("src/test\\resources")),
                createProcessing(new String[]{"-h", "--si", "-c", "src/test/resources", "src/test/resources/erttet.txt"}).getListOfFiles());
        assertEquals(Arrays.asList(new File("src/test/resources"), new File("src/test/resources/cat.txt")),
                createProcessing(new String[]{"-h", "--si", "-c", "src/test/resources", "src/test/resources/cat.txt"}).getListOfFiles());

    }
    boolean putArgs(String[] args) {
        Processing element = createProcessing(args);
        return element.getSi() & element.getH() & element.getC() &
                Arrays.asList(new File("src\\test\\resources")).equals(element.getListOfFiles());
    }

    @Test
    void checkInput() {
        assertTrue(putArgs(new String[]{"-h", "--si", "-c", "src/test/resources"}));
        assertFalse(putArgs(new String[]{"--si", "-c", "src/test/resources"}));
        assertFalse(putArgs(new String[]{"-h", "-c", "src/test/resources"}));
        assertFalse(putArgs(new String[]{"-h", "src/test/resources"}));
        assertFalse(putArgs(new String[]{"--si", "src/test/resources"}));
        assertFalse(putArgs(new String[]{"-c", "src/test/resources"}));
        assertFalse(putArgs(new String[]{"-h", "src/test/resources"}));
        NullPointerException thrown = assertThrows(NullPointerException.class, () ->
           createProcessing(new String[]{"-h", "--si"}));
        assertTrue(thrown.getMessage().contains("Вы не ввели имена файлов. Список имен пуст"));
    }

    @Test
    void cFlagSize() {
        assertTrue(createProcessing(new String[]{"-h", "--si", "-c", "src/test/resources", "src/test/resources/cat.txt"}).cFlagSize().equals("13.8K "));
        assertTrue(createProcessing(new String[]{"-c", "src/test/resources", "src/test/resources/cat.txt"}).cFlagSize().equals("13.5K "));
        assertTrue(createProcessing(new String[]{"-h", "-c", "src/test/resources", "src/test/resources/cat.txt"}).cFlagSize().equals("13.5K "));
        assertTrue(createProcessing(new String[]{"--si", "-c", "src/test/resources", "src/test/resources/cat.txt"}).cFlagSize().equals("13.8K "));
    }

    List<String> sizeList(String[] args) {
        List<String> listOfSize = new ArrayList<>();
        Processing example = new Processing();
        example.checkInput(args);
        List<File> fileList = example.getListOfFiles();
        for (int j = 0; j < fileList.size(); j++) {
            Size element = new Size(fileList.get(j));
            listOfSize.add(example.necessarySizeOutput(element.getSize()));
        }
        return listOfSize;

    }

    @Test
    void necessarySizeOutput() {
        assertEquals(sizeList(new String[]{"-h", "--si", "-c", "src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("5.0K ", "8.8K "));
        assertEquals(sizeList(new String[]{"--si", "-c", "src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("5.0K ", "8.8K "));
        assertEquals(sizeList(new String[]{"-c", "src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("4.9K ", "8.6K "));
        assertEquals(sizeList(new String[]{"-h", "-c", "src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("4.9K ", "8.6K "));
        assertEquals(sizeList(new String[]{"-h", "--si", "src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("5.0K ", "8.8K "));
        assertEquals(sizeList(new String[]{"-h", "src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("4.9K ", "8.6K "));
        assertEquals(sizeList(new String[]{"--si", "src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("5.0K ", "8.8K "));
        assertEquals(sizeList(new String[]{"src/test/resources/cat.txt", "src/test/resources"}), Arrays.asList("4.9K ", "8.6K "));
        assertEquals(sizeList(new String[]{"src/test/resources/regina.txt", "src/test/resources"}), Arrays.asList("8.6K "));

    }
}