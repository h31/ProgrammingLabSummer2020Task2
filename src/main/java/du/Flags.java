package du;

import java.util.Objects;

class Flags {
    private boolean hFlag = false;
    private boolean cFlag = false;
    private boolean siFlag = false;

    Flags(boolean hFlag, boolean cFlag, boolean siFlag) {
        this.hFlag = hFlag;
        this.cFlag = cFlag;
        this.siFlag = siFlag;
    }

    boolean hFlag() {
        return hFlag;
    }

    boolean cFlag() {
        return cFlag;
    }

    boolean siFlag() {
        return siFlag;
    }

    boolean setHFlag(boolean newHFlag) {
        boolean change = !(hFlag == newHFlag);
        hFlag = newHFlag;
        return change;
    }

    boolean setCFlag(boolean newCFlag) {
        boolean change = !(cFlag == newCFlag);
        cFlag = newCFlag;
        return change;
    }

    boolean setSiFlag(boolean newSiFlag) {
        boolean change = !(siFlag == newSiFlag);
        siFlag = newSiFlag;
        return change;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flags flags = (Flags) o;
        return hFlag == flags.hFlag &&
                cFlag == flags.cFlag &&
                siFlag == flags.siFlag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hFlag, cFlag, siFlag);
    }

    @Override
    public String toString() {
        return "hFlag=" + hFlag +
                ", cFlag=" + cFlag +
                ", siFlag=" + siFlag;
    }
}
