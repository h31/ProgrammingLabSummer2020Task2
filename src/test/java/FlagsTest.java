import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlagsTest {
    String[] args = {"-h", "-si", "-c", "src\\test\\resources"};

    @Test
    void setH() {
        assertTrue(() -> {
            Flags simple = new Flags();
            simple.setH(true);
            return simple.getH();
        });
    }

    @Test
    void getH() {
        assertFalse(() -> {
            Flags simple = new Flags();
            return simple.getH();
        });
    }

    @Test
    void setC() {
        assertTrue(() -> {
            Flags simple = new Flags();
            simple.setC(true);
            return simple.getC();
        });
    }

    @Test
    void getC() {
        assertFalse(() -> {
            Flags simple = new Flags();
            return simple.getC();
        });
    }

    @Test
    void setSi() {
        assertTrue(() -> {
            Flags simple = new Flags();
            simple.setSi(true);
            return simple.getSi();
        });
    }

    @Test
    void getSi() {
        assertFalse(() -> {
            Flags simple = new Flags();
            return simple.getSi();
        });
    }

    Flags simple() {
        Flags flag = new Flags();
        flag.checkFlags(args);
        return flag;
    }

    @Test
    void getLisOfFiles() {
        assertEquals("[src\\test\\resources]", simple().getLisOfFiles().toString());
    }

    String hepler() {
        Flags flag = new Flags();
        ArrayList<File> example = new ArrayList<>();
        example.add(new File("src\\test\\resources"));
        flag.setFile(example);
        return flag.getLisOfFiles().toString();
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