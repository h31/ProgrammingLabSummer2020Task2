package com.example.project;

import java.io.File;
import java.io.IOException;

public class Tar {

    public static void main(String[] args) {
        CommandLineParser parser = new CommandLineParser();
        try {
            parser.parse(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.err.println("usage:\njava -jar Tar.jar tar -u filename.txt" +
                    "\nor\njava -jar Tar.jar tar file1.txt file2.txt … -out output.txt.");
            return;
        }

        try {
            if (parser.pack) Packager.filePack(parser.files, new File(parser.out));
            else Separator.fileUnpack(parser.out);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}