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

    private InputStream getInputStream() throws IOException {
        return (input == null) ? System.in : Files.newInputStream(input);
    }

    public BufferedInputStream getBufferedInputStream() throws IOException {
        return new BufferedInputStream((pack) ? getInputStream() : new DecodedInputStream(getInputStream()));
    }

    private OutputStream getOutputStream() throws IOException {
        return (output == null) ? System.out : Files.newOutputStream(output);
    }

    public BufferedOutputStream getBufferedOutputStream() throws IOException {
        return new BufferedOutputStream((pack) ? new EncodedOutputStream(getOutputStream()) : getOutputStream());
    }
}

