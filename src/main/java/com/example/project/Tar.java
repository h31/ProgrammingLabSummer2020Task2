package com.example.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tar {

    public static void main(String[] args) throws IOException {
        List<File> filesToArchive = new ArrayList<>();
        String out = "";
        if (!args[0].equals("tar")) throw new IllegalArgumentException();
        if (args[1].equals("-u")) {
            if (args.length != 3 || !args[2].matches("^.+\\.txt$")) throw new IllegalArgumentException();
            Separator.fileReader(args[2]);
        } else {
            for (int i = 1; i < args.length; i++) {
                if (!args[i].matches("^.+\\.txt$") && !args[i].equals("-out"))
                    throw new IllegalArgumentException();
                if (args[i - 1].equals("-out")) continue;
                if (args[i].equals("-out") && i != args.length - 1) out = args[i + 1];
                else filesToArchive.add(new File(args[i]));
            }
            if (filesToArchive.isEmpty() || out.isEmpty()) throw new IllegalArgumentException();
            Compressor.fileWriter(filesToArchive, new File(out));
        }
    }
}