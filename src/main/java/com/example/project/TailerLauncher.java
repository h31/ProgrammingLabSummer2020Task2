package com.example.project;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.*;
import java.util.ArrayList;

public class TailerLauncher {
    @Option(name = "-c", metaVar = "LastSymbols", usage = "Extract last N symbols")
    private int numOfLastSymbols;

    @Option(name = "-n", metaVar = "LastStrings", usage = "Extract last N strings")
    private int numOfLastStrings;

    @Option(name = "-o", metaVar = "OutputName", usage = "Output file name")
    private String outputFileName;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private ArrayList<String> inputFileNames;

    public static void main(String[] args) {
        new TailerLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Tailer.jar [-c num |-n num] [-o ofile] file0 file1 file2 ...");
            parser.printUsage(System.err);
            return;
        }

        if (numOfLastStrings != 0 && numOfLastSymbols != 0) {
            throw new Error("The simultaneous use of the flags \"-c\" and \"-n\" ");
        } else if (numOfLastSymbols == 0 && numOfLastStrings == 0) {
            numOfLastStrings = 10;
        }

        Tailer tailer = new Tailer(numOfLastSymbols, numOfLastStrings);
        try {
            int result = tailer.tail(inputFileNames, outputFileName);
            System.out.println("Total of " + result + " symbols/strings extracted");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
