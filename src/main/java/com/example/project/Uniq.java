package com.example.project;

import java.io.*;
import java.util.Objects;

public class Uniq {
    private UniqOptions flags;

    private void setFlags(UniqOptions flags) {
        this.flags = flags;
    }

    public Uniq(UniqOptions flags) {
        setFlags(flags);
    }

    public void launch() throws IOException {
        try {
            BufferedReader input = (flags.inputName != null)
                    ? new BufferedReader(new FileReader(flags.inputName))
                    : new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter output = (flags.outputName != null)
                    ? new BufferedWriter(new FileWriter(flags.outputName))
                    : new BufferedWriter(new OutputStreamWriter(System.out));
            if (flags.unique || flags.count)
                makeUniqueWithFlag(input, output);
            else
                makeUnique(input, output);
            input.close();
            output.close();
        } catch (FileNotFoundException fnfEx) {
            System.out.print("Unable to find file \"" + flags.inputName + "\"\n");
        }
    }

    private void makeUnique(BufferedReader input, BufferedWriter output) throws IOException {
        String prevLine = "";
        String line;
        while ((line = input.readLine()) != null) {
            if (areDuplicates(prevLine, line)) {
                if (!prevLine.equals(""))
                    output.write("\n");
                output.write(line);
            }
            prevLine = line;
        }
    }

    private void makeUniqueWithFlag(BufferedReader input, BufferedWriter output) throws IOException {
        String prevLine = "";
        String firstRepeated = "";
        String line;
        boolean skip = false;
        int times = 1;
        while ((line = input.readLine()) != null) {
            if (areDuplicates(prevLine, line)) {
                if (!skip && !prevLine.equals("")) {
                    if (flags.count)
                        output.write(times + " ");
                    if (times > 1)
                        output.write(firstRepeated + "\n");
                    else
                        output.write(prevLine + "\n");
                    times = 1;
                } else {
                    skip = false;
                    times = 1;
                }
            } else if (!prevLine.equals("")) {
                if (flags.count) {
                    if (times == 1)
                        firstRepeated = prevLine;
                    times++;
                }
                if (flags.unique) skip = true;
            }
            prevLine = line;
        }
        if (!skip) {
            if (flags.count)
                output.write(times + " ");
            output.write(prevLine);
        }
    }

    private boolean areDuplicates(String firstLine, String secondLine) {
        final boolean indexesInbound = firstLine.length() >= flags.num && secondLine.length() >= flags.num;
        final boolean linesNotEqualFlagI =
                indexesInbound && flags.ignoreCase && !secondLine.substring(flags.num).toLowerCase()
                        .equals(firstLine.substring(flags.num).toLowerCase());
        final boolean linesNotEqual =
                indexesInbound && !flags.ignoreCase && !secondLine.substring(flags.num)
                        .equals(firstLine.substring(flags.num));
        final boolean oneOfLinesIndexInbound = firstLine.length() < flags.num
                && secondLine.length() >= flags.num
                || firstLine.length() >= flags.num
                || firstLine.equals("");
        return linesNotEqual || linesNotEqualFlagI || !indexesInbound && oneOfLinesIndexInbound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Uniq uniq = (Uniq) o;
        return flags.equals(uniq.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flags);
    }

    @Override
    public String toString() {
        return "Uniq{" +
                "flags=" + flags +
                '}';
    }
}
