package com.example.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandLineParser {

    List<File> files = new ArrayList<>();
    String out;
    Boolean pack;

    public void parse(String[] args) {
        if (!args[0].equals("tar")) throw new IllegalArgumentException("command line should begins with tar");
        if (args[1].equals("-u")) {
            if (args.length != 3)
                throw new IllegalArgumentException("wrong command, should be 3 parts: tar -u file.txt");
            if (!args[2].matches("^.+\\.txt$"))
                throw new IllegalArgumentException("" + args[2] + " is not a file.txt name");
            out = args[2];
            pack = false;
        } else {
            for (int i = 1; i < args.length; i++) {
                if (!args[i].matches("^.+\\.txt$") && !args[i].equals("-out"))
                    throw new IllegalArgumentException("" + args[i] + " is a wrong element");
                if (args[i - 1].equals("-out")) continue;
                if (args[i].equals("-out") && i != args.length - 1) out = args[i + 1];
                else files.add(new File(args[i]));
            }
            if (files.isEmpty()) throw new IllegalArgumentException("There is no file to pack");
            if (out == null) throw new IllegalArgumentException("There is no output file");
            pack = true;
        }
    }
}
