package com.example.project;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UniqMain {

    @Option(name = "file", usage = "input file name", metaVar = "INPUT")
    private String inputFile;

    @Option(name = "-o", usage = "output file name", metaVar = "OUTPUT")
    private String outputFile;

    @Option(name = "-i", usage = "ignore symbols' uppercase")
    private boolean ignoreCase;

    @Option(name = "-s", usage = "ignore first N symbols of string", metaVar = "N")
    private int num;

    @Option(name = "-u", usage = "outputs only unique strings")
    private boolean unique;

    @Option(name = "-c", usage = "outputs number of duplicates before string")
    private boolean count;

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public static void main(String[] args) {
        new UniqMain().doMain(args);
    }

    private void doMain(String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);

        if (args.length < 1) {
            parser.printUsage(System.out);
            System.exit(-1);
        }

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.out.println("Unable to parse command-line options:" + e);
            parser.printUsage(System.out);
        }


    }
}
