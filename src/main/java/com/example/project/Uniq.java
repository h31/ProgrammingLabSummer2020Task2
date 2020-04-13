package com.example.project;

import java.io.*;

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
        } else {
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
            if (!line
                    .substring(flags.num)
                    .equals(prevLine.substring(flags.num)) || (flags.ignoreCase && !line
                    .substring(flags.num)
                    .toLowerCase()
                    .equals(prevLine.substring(flags.num).toLowerCase()))
            ) {
                output.write(line + "\n");
            }
            prevLine = line;
        }
    }

    private void makeUniqueWithFlag(BufferedReader input, BufferedWriter output) throws IOException {
        String prevLine = "";
        String line;
        boolean skip = false;
        int times = 1;
        while ((line = input.readLine()) != null) {
            if ((!line
                    .substring(flags.num)
                    .equals(prevLine.substring(flags.num)) || (flags.ignoreCase && !line
                    .substring(flags.num)
                    .toLowerCase()
                    .equals(prevLine.substring(flags.num).toLowerCase()))) && !prevLine.equals("")
            ) {
                if (!skip) {
                    if (flags.count) output.write(times + " ");
                    output.write(prevLine + "\n");
                    times = 1;
                } else {
                    skip = false;
                }
            } else {
                if (flags.count) times++;
                if (flags.unique) skip = true;
            }
            prevLine = line;
            if (input.readLine() == null && !skip) output.write(line);
        }
    }
}
