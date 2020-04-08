package du;

import java.io.IOException;

class Main{
    public static void main(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        FileWeight weight = new FileWeight(arguments);
        System.out.println(weight.degree());
    }
}