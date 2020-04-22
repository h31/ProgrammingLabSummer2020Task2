package xor;

public class Main {

    public static void main(String[] args) {
        // проверка количества аргументов
        if (args.length != 3 && args.length != 5) {
            System.out.println("Illegal count of arguments");
            return;
        }

        // проверка флага
        if (!args[0].equals("-c") && !args[0].equals("-d")) {
            System.out.println("First argument must be -c or -d");
            return;
        }

        // получение и проверка ключа
        String key = args[1];
        if (key.matches("^0-9a-eA-E")) {
            System.out.println("Key must be in HEX");
            return;
        }

        // получение имени исходного файла
        String inputFile = args[2];

        // получение имени выходного файла
        String outputFile;
        if (args.length == 5) {
            if (!args[3].equals("-o")) {
                System.out.println("Illegal argument, try -o");
                return;
            }
            outputFile = args[4];
        } else {
            outputFile = "out".concat(inputFile);
        }

        // шифрование/дешифрование
        XOR xor = new XOR();
        xor.encodeDecode(inputFile, outputFile, key);
    }
}
