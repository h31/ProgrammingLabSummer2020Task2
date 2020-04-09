import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlagsTest {
    Flags simple = new Flags();

    @Test
    void setH() {
        assertTrue(() -> {
            simple.setH(true);
            return simple.getH();
        });
    }

    @Test
    void getH() {
        assertFalse(() -> simple.getH());
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
        assertFalse(() -> simple.getC());
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
        assertFalse(() -> simple.getSi());
    }

    Flags simple() {
        String[] args = {"-h", "--si", "-c", "src\\test\\resources"};
        simple.checkFlags(args);
        return simple;
    }

    @Test
    void getLisOfFiles() {
        assertEquals("[src\\test\\resources]", simple().getLisOfFiles().toString());
    }

    String hepler() {
        ArrayList<File> example = new ArrayList<>();
        example.add(new File("src\\test\\resources"));
        simple.setFile(example);
        return simple.getLisOfFiles().toString();
    }

    @Test
    void setFile() {
        assertEquals("[src\\test\\resources]",
                hepler());

    }

    boolean allRight() {
        return simple().getH() && simple().getSi() && simple().getC()
                && (simple().getLisOfFiles().toString().equals("[src\\test\\resources]"));
    }

    @Test
    void checkFlags() {
        assertTrue(
                allRight());
    }
}