import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class FileFinder {

    @Option(name = "-r")
    private boolean subdirectories;
    @Option(name = "-d")
    private File directoryName;
    @Argument(required = true)
    private File filename;

    private String search(File topDirectory, File fileName) {
        File[] list = topDirectory.listFiles();
        if (list == null) {
            return "Вы указали не существующий путь";
        }
        for (File file : list) {
            if (subdirectories && file.isDirectory()) {
                if (!("Файл " + fileName + " не найден.").equals(search(file, fileName))) {
                    return search(file, fileName);
                }
            } else if (file.getName().equals(fileName.getName())) {
                return "Файл " + file.getName() + " найден." + "\n" + "Путь: " + file.getAbsolutePath();
            }
        }
        return "Файл " + fileName + " не найден.";
    }

    public String start(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.out.println("Команда должна принимать следующий вид: -r -d directory filename.txt");
            System.exit(1);
        }
        if (directoryName != null) {
            return search(directoryName, filename);
        }
        File directory = new File(new File("").getAbsolutePath());
        return search(directory, filename);
    }
}