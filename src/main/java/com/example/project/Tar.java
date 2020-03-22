package com.example.project;

import java.io.File;
import java.io.IOException;

public class Tar {

    public static void main(String[] args) throws IOException {
        CommandLineParser parser = new CommandLineParser();
        parser.parse(args);
        if (parser.pack) Packager.filePack(parser.files, new File(parser.out));
        else Separator.fileUnpack(parser.out);
    }
}