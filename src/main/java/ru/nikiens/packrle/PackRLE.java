package ru.nikiens.packrle;

import ru.nikiens.packrle.util.ExceptionHandler;
import ru.nikiens.packrle.util.ExtensionUtil;
import ru.nikiens.packrle.util.IOProvider;

import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(
        name = "pack-rle",
        version = "1.0",
        mixinStandardHelpOptions = true,
        resourceBundle = "Messages"
)
public class PackRLE implements Callable<Integer> {
    @ArgGroup
    Incompatible incompatible = new Incompatible();

    static class Incompatible {
        @Option(names = "-z", descriptionKey = "pack")
        boolean pack;

        @Option(names = "-u", descriptionKey = "unpack")
        boolean unpack;
    }

    @Option(names = "-out", descriptionKey = "out")
    Path output;

    @Parameters(arity = "0..1", descriptionKey = "file")
    Path input;

    private void setDefaults() {
        if (input == null) {
            incompatible.pack = !incompatible.unpack;
            return;
        }
        ExtensionUtil eu = new ExtensionUtil("rlz");

        if (!incompatible.pack && eu.hasExtension(input)) {
            incompatible.unpack = true;
            output = (output == null) ? eu.truncate(input) : output;
            return;
        }
        if (!incompatible.unpack) {
            incompatible.pack = true;
            output = (output == null) ? eu.append(input) : output;
        }
    }

    public Integer call() throws IOException {
        setDefaults();
        IOProvider ioProvider = new IOProvider(incompatible.pack, input, output);

        try (BufferedInputStream r = ioProvider.getBufferedInputStream();
             BufferedOutputStream w = ioProvider.getBufferedOutputStream()) {
            r.transferTo(w);
        }
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new PackRLE())
                .setExecutionExceptionHandler(new ExceptionHandler()).execute(args);
        System.exit(exitCode);
    }
}