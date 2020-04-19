package com.example.project;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class TransposeLauncher {
    @Option(name = "-a", metaVar = "num", usage = "Space allocated for the word")
    private String allocatedSpace;

    @Option(name = "-t", usage = "Trim the word if it's length is greater then space allocated for it")
    private boolean trim;

    @Option(name = "-r", usage = "Align the word right if it's length is less then space allocated for it")
    private boolean right;

    @Option(name = "-o", metaVar = "ofile", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "file", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) {
        new TransposeLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar transpose.jar [-a num] [-t] [-r] [-o ofile] file");
            parser.printUsage(System.err);
            return;
        }

        Transpose transpose = new Transpose(allocatedSpace, trim, right);
        try {
            transpose.transposes(inputFileName, outputFileName);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
