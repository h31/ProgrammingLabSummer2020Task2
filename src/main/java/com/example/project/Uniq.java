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
        BufferedReader input;
        BufferedWriter output;

        if (flags.inputName != null && flags.outputName != null) {
            input = new BufferedReader(new FileReader(flags.inputName));
            output = new BufferedWriter(new FileWriter(flags.outputName));
            if (flags.unique || flags.count) makeUniqueWithFlag(input, output);
            else makeUnique(input, output);
            input.close();
            output.close();
        }

        if (flags.outputName == null && flags.inputName == null) {
            input = new BufferedReader(new InputStreamReader(System.in));
            output = new BufferedWriter(new OutputStreamWriter(System.out));
            if (flags.unique || flags.count) makeUniqueWithFlag(input, output);
            else makeUnique(input, output);
            input.close();
            output.close();
        } else if (flags.inputName == null) {
            input = new BufferedReader(new InputStreamReader(System.in));
            output = new BufferedWriter(new FileWriter(flags.outputName));
            if (flags.unique || flags.count) makeUniqueWithFlag(input, output);
            else makeUnique(input, output);
            input.close();
            output.close();
        } else if (flags.outputName == null) {
            input = new BufferedReader(new FileReader(flags.inputName));
            output = new BufferedWriter(new OutputStreamWriter(System.out));
            if (flags.unique || flags.count) makeUniqueWithFlag(input, output);
            else makeUnique(input, output);
            input.close();
            output.close();
        }

    }

    private void makeUnique(BufferedReader input, BufferedWriter output) throws IOException {
        String prevLine = "";
        String line;
        while ((line = input.readLine()) != null) {
            if (
                    (prevLine.length() >= flags.num && line.length() >= flags.num) && (
                            flags.ignoreCase && !line
                                    .substring(flags.num)
                                    .toLowerCase()
                                    .equals(prevLine.substring(flags.num).toLowerCase())
                                    || !flags.ignoreCase && !line
                                    .substring(flags.num)
                                    .equals(prevLine.substring(flags.num))
                    ) || !(prevLine.length() >= flags.num && line.length() >= flags.num) && (
                            prevLine.length() < flags.num
                                    && line.length() >= flags.num
                                    || prevLine.length() >= flags.num
                                    || prevLine.equals("")
                    )
            ) {
                if (!prevLine.equals("")) output.write("\n");
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
            if (
                    (prevLine.length() >= flags.num && line.length() >= flags.num) && (
                            flags.ignoreCase && !line
                                    .substring(flags.num)
                                    .toLowerCase()
                                    .equals(prevLine.substring(flags.num).toLowerCase())
                                    || !flags.ignoreCase && !line
                                    .substring(flags.num)
                                    .equals(prevLine.substring(flags.num))
                    ) || !(prevLine.length() >= flags.num && line.length() >= flags.num) && (
                            prevLine.length() < flags.num
                                    && line.length() >= flags.num
                                    || prevLine.length() >= flags.num
                                    || prevLine.equals("")
                    )
            ) {
                if (!skip && !prevLine.equals("")) {
                    if (flags.count) output.write(times + " ");
                    if (times > 1) output.write(firstRepeated + "\n");
                    else output.write(prevLine + "\n");
                    times = 1;
                } else {
                    skip = false;
                }
            } else if (!prevLine.equals("")) {
                if (flags.count) {
                    if (times == 1) firstRepeated = prevLine;
                    times++;
                }
                if (flags.unique) skip = true;
            }
            prevLine = line;
        }
        if (!skip) {
            if (flags.count) output.write(times + " ");
            output.write(prevLine);
        }
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
