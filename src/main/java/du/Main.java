package du;

import java.io.*;

class Main{
    public static void main(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        FileWeight weight = new FileWeight(arguments);
        System.out.println(arguments.getFlags().toString());
        System.out.println(weight.degree());
    }
}