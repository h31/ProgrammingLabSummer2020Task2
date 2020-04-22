package ru.nikiens.packrle.util;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.IExecutionExceptionHandler;

import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionHandler implements IExecutionExceptionHandler {
    public int handleExecutionException(Exception ex,
                                        CommandLine commandLine,
                                        ParseResult parseResult) {
        ResourceBundle rb = ResourceBundle.getBundle("Messages", Locale.getDefault());
        PrintWriter w = commandLine.getErr();

        w.println("pack-rle: ");
        if (ex instanceof NoSuchFileException) {
            // В getMessage() у NoSuchFileException содержится путь
            w.print(rb.getString("err.noSuchFile") + " " + ex.getMessage());
        } else {
            w.print(rb.getString("err.terminate"));
            w.println(ex.getMessage());
        }

        return commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
