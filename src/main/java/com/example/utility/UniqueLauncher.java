package com.example.utility;

//библиотека args4j для обработки аргументов командной строки
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.Objects;

public class UniqueLauncher {
    //Опции для ключенй
    @Option(name = "-i", metaVar = "IgnoreCase", usage = "Equals with IgnoreCase")
    private boolean flagIisSet;

    @Option(name = "-o", metaVar = "OutputFile", usage = "name of output file")
    private String nameOfOutput;

    @Option(name = "-s", metaVar = "IgnoreNumString", usage = "Ignore begin 'num' string")
    private int num = -1;

    @Option(name = "-c", metaVar = "CountString", usage = "Write counted string")
    private boolean flagCisSet;

    @Option(name = "-u", metaVar = "OnlyUnique", usage = "Write only uniques string")
    private boolean flagUisSet;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String nameOfInput;

    public static void main(String[] args) throws IOException {
        new UniqueLauncher().launch(args);
    }

    /**
     * Обработка элементов командной строки
     */
    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());

            parser.printUsage(System.err);
            return;
        }

        Unique unique = new Unique();

        if (nameOfInput == null)
            unique.inputFileDataConsole();
        else
            unique.inputFileData(nameOfInput);

        if (!unique.getAllLines().isEmpty()) {
            if (flagCisSet) {
                unique.equalsWithCountString();
            } else if (flagUisSet) {
                unique.equalsUnique();
            } else if (flagIisSet) {
                unique.equalsIgnoreSomeChars(0);
            } else if (num > 0) {
                unique.equalsIgnoreSomeChars(num);
            } else {
                System.err.print("No flags was found or invalid argument");
            }

            if (nameOfOutput == null)
                System.out.write(unique.getDataForOutFile().toString().getBytes());
            else
                unique.outputFileData(nameOfOutput);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniqueLauncher that = (UniqueLauncher) o;
        return flagIisSet == that.flagIisSet &&
                num == that.num &&
                flagCisSet == that.flagCisSet &&
                flagUisSet == that.flagUisSet &&
                Objects.equals(nameOfOutput, that.nameOfOutput) &&
                Objects.equals(nameOfInput, that.nameOfInput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flagIisSet, nameOfOutput, num, flagCisSet, flagUisSet, nameOfInput);
    }

    @Override
    public String toString() {
        return "UniqueLauncher{" +
                "flagIisSet=" + flagIisSet +
                ", nameOfOutput='" + nameOfOutput + '\'' +
                ", num=" + num +
                ", flagCisSet=" + flagCisSet +
                ", flagUisSet=" + flagUisSet +
                ", nameOfInput='" + nameOfInput + '\'' +
                '}';
    }
}
