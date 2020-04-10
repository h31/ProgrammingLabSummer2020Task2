package com.example.project;

import java.io.*;

public class Uniq {
    private UniqFlags flags;

    private void setFlags(UniqFlags flags) {
        this.flags = flags;
    }

    public Uniq(UniqFlags flags) {
        setFlags(flags);
    }

    public void launch() {

        if (flags.inputName != null && flags.outputName != null) try {
            BufferedReader input = new BufferedReader(new FileReader(flags.inputName));
            BufferedWriter output = new BufferedWriter(new FileWriter(flags.outputName));
            makeUnique(input, output);
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }

        if (flags.outputName == null && flags.inputName == null) try {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
            makeUnique(input, output);
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        } else {
            if (flags.inputName == null) try {
                BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                BufferedWriter output = new BufferedWriter(new FileWriter(flags.outputName));
                makeUnique(input, output);
            } catch (IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }

            if (flags.outputName == null) try {
                BufferedReader input = new BufferedReader(new FileReader(flags.inputName));
                BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out));
                makeUnique(input, output);
            } catch (IOException ioEx) {
                System.out.println(ioEx.getMessage());
            }
        }
    }

    private void makeUnique(BufferedReader input, BufferedWriter output) throws IOException {
        String prevLine = "";
        String line;
        boolean prevUnique = false;
        while ((line = input.readLine()) != null) {
            if (flags.unique && prevUnique) output.write(prevLine + "\n");
            if (
                    !line.substring(flags.num).equals(prevLine.substring(flags.num))
                            || (flags.ignoreCase && !line.substring(flags.num).toLowerCase().equals(prevLine.substring(flags.num).toLowerCase()))
            ) {
                if (!flags.unique) output.write(line + "\n");
                else prevUnique = true;
            }
            prevLine = line;
        }
        input.close();
        output.close();
    }
}
