package ru.nikiens.packrle.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class DecodedInputStream extends FilterInputStream {
    private int count, previous = -1;
    private boolean hasRun;

    public DecodedInputStream(InputStream in) {
        super(in);
    }

    public int read() throws IOException {
        if (count > 0) {
            count--;
            if (count == 0) {
                int b = previous;
                previous = -1;
                return b;
            }
            return previous;
        }

        int b = super.read();

        if (b == previous) {
            if (hasRun) {
                count = b - 1;
                hasRun = false;

                if (count == -1) {
                    count = 0;
                    return read();
                }
            } else {
                hasRun = true;
            }
        } else {
            if (hasRun) {
                count = b - 1;
                hasRun = false;

                if (count == -1) {
                    count = 0;
                    return read();
                }

                return previous;
            }
            previous = b;
        }
        return b;
    }

    public int read(byte[] buf, int off, int len) throws IOException {
        Objects.checkFromIndexSize(off, len, buf.length);
        int i = 0;

        for (; i < len; i++) {
            int b = read();
            if (b == -1) {
                return (i > 0) ? i : -1;
            }
            buf[off + i] = (byte) b;
        }
        return i;
    }
}
