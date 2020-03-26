package com.example.project;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ls {
    protected static final class ProgramFile {
        private final File file;

        protected ProgramFile(File file) {
            this.file = file;
        }

        private long getSize() {
            return file.length();
        }

        private String getName() {
            return file.getName();
        }

        protected String getLastModificate() {
            final long timeModified = file.lastModified();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            return sdf.format(new Date(timeModified));
        }

        protected String humanReadableSize() {
            long res = getSize();
            String[] unit = new String[]{"B", "Kb", "Mb", "Gb"};
            int cnt = 0;
            while (res >= 1024) {
                res /= 1024;
                cnt++;
            }
            return res + " " + unit[cnt];
        }

        protected String getFilePermissions() throws IOException {
            StringBuilder sb = new StringBuilder();
            if (file.canRead()) {
                sb.append("r");
            } else sb.append("-");

            if (file.canWrite()) {
                sb.append("w");
            } else sb.append("-");

            if (file.canExecute()) {
                sb.append("x");
            } else sb.append("-");

            return sb.toString();
        }

        /*System.out.println(file.toPath());
        final Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
            return //(file.isDirectory() ? "d" : "-") +
                    (perms.contains(PosixFilePermission.OWNER_READ) ? 'r' : '-') +
                    (perms.contains(PosixFilePermission.OWNER_WRITE) ? 'w' : '-') +
                    (perms.contains(PosixFilePermission.OWNER_EXECUTE) ? 'x' : '-') +
                    (perms.contains(PosixFilePermission.GROUP_READ) ? 'r' : '-') +
                    (perms.contains(PosixFilePermission.GROUP_WRITE) ? 'w' : '-') +
                    (perms.contains(PosixFilePermission.GROUP_EXECUTE) ? 'x' : '-') +
                    (perms.contains(PosixFilePermission.OTHERS_READ) ? 'r' : '-') +
                    (perms.contains(PosixFilePermission.OTHERS_WRITE) ? 'w' : '-') +
                    (perms.contains(PosixFilePermission.OTHERS_EXECUTE) ? 'x' : '-') + "";
    }*/
        protected String getPermBitMask() throws IOException {
            final byte canRead = 0b100;
            final byte canWrite = 0b010;
            final byte canExecute = 0b001;
            int permissions = 0b000;
            if (file.canRead()) permissions = permissions | canRead;
            if (file.canWrite()) permissions = permissions | canWrite;
            if (file.canExecute()) permissions = permissions | canExecute;
            return Integer.toBinaryString(permissions);
        }
            /*final Set<PosixFilePermission> perms = Files.getPosixFilePermissions(file.toPath());
            return //(file.isDirectory() ? "d" : "-") +
                    ((perms.contains(PosixFilePermission.OWNER_READ) ? 1 : 0) +
                            (perms.contains(PosixFilePermission.OWNER_WRITE) ? 2 : 0) +
                            (perms.contains(PosixFilePermission.OWNER_EXECUTE) ? 4 : 0))
                            * 100 +
                            ((perms.contains(PosixFilePermission.GROUP_READ) ? 1 : 0) +
                            (perms.contains(PosixFilePermission.GROUP_WRITE) ? 2 : 0) +
                            (perms.contains(PosixFilePermission.GROUP_EXECUTE) ? 4 : 0)) * 10 +
                            (perms.contains(PosixFilePermission.OTHERS_READ) ? 1 : 0) +
                            (perms.contains(PosixFilePermission.OTHERS_WRITE) ? 2 : 4) +
                            (perms.contains(PosixFilePermission.OTHERS_EXECUTE) ? 4 : 0) + "";
        }*/
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> flags = new ArrayList<>();
        String outputFileName = null;
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-l":
                    flags.add("-l");
                    break;
                case "-h":
                    flags.add("-h");
                    break;
                case "-r":
                    flags.add("-r");
                    break;
                case "-o":
                    flags.add("-o");
                    outputFileName = args[i + 1];
                    break;
            }
        }
        Map<String, String> lst = new HashMap();
        if (args.length == 0) {
            System.out.println("ls [-l] [-h] [-r] [-o output.file] directory_or_file");
            System.exit(0);
        }
        File directoryOrFile = new File(args[args.length - 1]);
        if (directoryOrFile.isDirectory()) { // если директория - вывожу список файлов
            for (File file : directoryOrFile.listFiles()) {
                if (file.isFile()) {
                    lst.put(file.getName(), file.getName());
                    if (flags.contains("-l")) {
                        ProgramFile pf = new ProgramFile(file);
                        lst.put(file.getName(), pf.getPermBitMask() + " " + pf.getLastModificate() + " " + pf.getSize());
                    } else if (flags.contains("-h")) {
                        ProgramFile pf = new ProgramFile(file);
                        lst.put(file.getName(), pf.humanReadableSize() + " " + pf.getFilePermissions());
                    }
                }
            }
            /*if (flags.contains("-r")) {
                treeMap = new TreeMap<String, String>(lst, Collections.reverseOrder()); //отсортировали по ключу
            }*/
            //else {
            treeMap = new TreeMap<String, String>(lst);
            //}
            //lst = new TreeMap<String, String>((flags.contains("-r")) ? Collections.reverseOrder() : Collections.sort());
            //Collections.sort(lst);
            //Collections.sort(lst, Collections.reverseOrder());

        } else {
            lst.put(directoryOrFile.getName(), directoryOrFile.getName());
            if (flags.contains("-l")) {
                ProgramFile pf = new ProgramFile(directoryOrFile);
                lst.put(directoryOrFile.getName(), pf.getPermBitMask() + " " + pf.getLastModificate() + " " + pf.getSize());
            } else if (flags.contains("-h")) {
                ProgramFile pf = new ProgramFile(directoryOrFile);
                lst.put(directoryOrFile.getName(), pf.humanReadableSize() + " " + pf.getFilePermissions());
            }
        }
        assert outputFileName != null;
        if ((flags.contains("o")) && (new File(outputFileName).isFile())) {
            File tempFile = new File(outputFileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            if (flags.contains("-r")) {
                for (String key : treeMap.descendingKeySet()) {
                    bw.write(key + " " + treeMap.get(key));
                    bw.newLine();
                }
            }
            else {
                for (Map.Entry<String, String> entry : treeMap.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                    bw.write(entry.getKey() + " " + entry.getValue());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            //System.out.println(tempFile);
        } else {
            if (flags.contains("-r")) {
                for (String key : treeMap.descendingKeySet()) {
                    System.out.println(key + " " + treeMap.get(key));
                }
            } else {
                for (Map.Entry<String, String> entry : treeMap.entrySet()) {
                    System.out.println(entry.getKey() + " " + entry.getValue());
                }
            /*for (Map.Entry<String, String > entry : treeMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }*/
            }
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Ls{}";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
