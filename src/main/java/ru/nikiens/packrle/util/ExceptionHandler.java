package ru.nikiens.packrle.util;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.IExecutionExceptionHandler;

import java.nio.file.NoSuchFileException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ExceptionHandler implements IExecutionExceptionHandler {
    public int handleExecutionException(Exception ex,
                                        CommandLine commandLine,
                                        ParseResult parseResult) {
        ResourceBundle rb = ResourceBundle.getBundle("Messages", Locale.getDefault());

        if (ex instanceof NoSuchFileException) {
            commandLine.getErr().println(rb.getString("err.noSuchFile") + ex.getMessage());
        } else {
            commandLine.getErr().println(ex.getMessage());
            commandLine.getErr().println(rb.getString("err.terminate"));
        }

        return commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
