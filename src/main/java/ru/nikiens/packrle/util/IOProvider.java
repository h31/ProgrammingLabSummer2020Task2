package ru.nikiens.packrle.util;

import ru.nikiens.packrle.io.DecodedInputStream;
import ru.nikiens.packrle.io.EncodedOutputStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOProvider {
    private boolean pack;
    private Path input;
    private Path output;

    public IOProvider(boolean pack, Path input, Path output) {
        this.pack = pack;
        this.input = input;
        this.output = output;
    }

    private BufferedInputStream getBufferedInputStream() throws IOException {
        return new BufferedInputStream((input == null) ? System.in : Files.newInputStream(input));
    }

    public InputStream getInputStream() throws IOException {
        return (pack) ? getBufferedInputStream() : new DecodedInputStream(getBufferedInputStream());
    }

    private BufferedOutputStream getBufferedOutputStream() throws IOException {
        return new BufferedOutputStream((output == null) ? System.out : Files.newOutputStream(output));
    }

    public OutputStream getOutputStream() throws IOException {
        return (pack) ? new EncodedOutputStream(getBufferedOutputStream()) : getBufferedOutputStream();
    }
}

