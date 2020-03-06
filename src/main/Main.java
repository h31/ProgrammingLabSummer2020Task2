import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) throw new IllegalArgumentException();
        for (int i = 0; i != args.length; i++) {
            switch (args[i]) {
                case ("-l"):
                    Flags.lFlag = true;
                    break;
                case ("-h"):
                    Flags.hFlag = true;
                    break;
                case ("-r"):
                    Flags.rFlag = true;
                    break;
                case ("-o"):
                    if (i + 1 != args.length - 1) {
                        Flags.setOutputFile(args[i + 1]);
                        Flags.oFlag = true;
                        i++;
                    }
                    break;
                default:
                    if (i == args.length - 1) Flags.setDirectory(args[i]);
                    else throw new IllegalArgumentException();
                    break;
            }
        }
    }
}
