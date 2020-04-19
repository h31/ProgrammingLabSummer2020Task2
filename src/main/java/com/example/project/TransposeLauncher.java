package com.example.project;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class TransposeLauncher {
    @Option(name = "-o", metaVar = "OutputFileName", usage = "Output file name")
    private String outputFileName;

    @Option(name = "-a", metaVar = "AreaWords", usage = "Area selected for words")
    private String area;

    @Option(name = "-t", usage = "Truncate words")
    private boolean truncate;

    @Option(name = "-r", usage = "Right alignment ")
    private boolean rightAlign;

    @Argument(metaVar = "InputFileName", usage = "Input file name")
    private String inputFileName;


    public static void main(String[] args) {
        new TransposeLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage() + "\n");
            System.err.println("[-a num] [-t] [-r] [-o ofile] [file]");
            parser.printUsage(System.err);
            return;
        }

        Transpose transpose = new Transpose(inputFileName, outputFileName);
        int a;
        if (area == null) a = 10;
        else a = Integer.parseInt(area);
        try {
            transpose.transpose(a, truncate, rightAlign);
            System.out.println("transposed");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
