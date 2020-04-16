package com.example.project;

import java.io.IOException;

public class UniqMain {
    public static void main(String[] args) throws IOException {
        new Uniq(
                new UniqOptions(args)
        ).launch();
    }
}
