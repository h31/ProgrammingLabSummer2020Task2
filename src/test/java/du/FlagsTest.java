package du;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlagsTest {
    Flags createFlags(){
        return new Flags(true, true, false);
    }

    @Test
    void hFlag() {
        assertTrue(createFlags().hFlag());
    }

    @Test
    void cFlag() {
        assertTrue(createFlags().cFlag());
    }

    @Test
    void siFlag() {
        assertFalse(createFlags().siFlag());
    }

    @Test
    void setHFlag() {
        assertFalse(createFlags().setHFlag(true));
    }

    @Test
    void setCFlag() {
        assertTrue(createFlags().setCFlag(false));
    }

    @Test
    void setSiFlag() {
        assertFalse(createFlags().setSiFlag(false));
    }

}
