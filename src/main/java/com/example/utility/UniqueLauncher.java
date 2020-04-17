package com.example.utility;

//библиотека args4j для обработки аргументов командной строки
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.io.File;
import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import java.util.Objects;

public class UniqueLauncher {
    //Опции для ключенй
    @Option(name = "-i", metaVar = "IgnoreCase", usage = "Equals with IgnoreCase")
    private boolean flagIisSet;

    @Option(name = "-o", metaVar = "OutputFile", usage = "name of output file")
    private String nameOfOutput;

    @Option(name = "-s", metaVar = "IgnoreNumString", usage = "Ignore begin 'num' string")
    private int num;

    @Option(name = "-c", metaVar = "CountString", usage = "Write counted string")
    private boolean flagCisSet;

    @Option(name = "-u", metaVar = "OnlyUnique", usage = "Write only uniques string")
    private boolean flagUisSet;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String nameOfInput;

    public boolean isFlagIisSet() {
        return flagIisSet;
    }

    public int getNum() {
        return num;
    }

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
                unique.equalsCombined("i",0);
            } else if (num > 0) {
                unique.equalsCombined("s",num);
            } else {
                unique.equalsCombined("default",0);
            }


            if (nameOfOutput != null) {
                File file = new File(nameOfOutput);
                if (file.exists()) {//Проверка существованмя файла
                    System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(nameOfOutput)), true));
                    System.out.write(unique.getDataForOutFile().toString().getBytes());
                } else {
                    System.err.print("File not found" + ": " + nameOfOutput);
                }
            } else {
                System.out.write(unique.getDataForOutFile().toString().getBytes());
            }
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
