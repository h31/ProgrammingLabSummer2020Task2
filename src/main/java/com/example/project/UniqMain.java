package com.example.project;

public class UniqMain {
    public static void main(String[] args) {
        UniqFlags flags = new UniqFlags(args);
        Uniq uniq = new Uniq(flags);
        uniq.launch();
    }
}
