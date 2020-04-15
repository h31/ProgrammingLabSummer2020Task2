package du;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

class FileWeight {
    private List<BigDecimal> weights = new ArrayList<>();
    private Flags flags;

    FileWeight(Arguments args) {
        try {
            for (String fileName : args.getFiles()) {
                if (!new File(fileName).exists()) throw new IOException();
                File file = new File(fileName);
                if (file.isDirectory()) weights.add(dirSum(file));
                else weights.add(new BigDecimal(file.length()).setScale(1, RoundingMode.HALF_UP));
            }
        } catch (IOException e) {
            System.exit(2);
        }

        flags = args.getFlags();
    }

    private BigDecimal dirSum(File dir) {
        File[] files = dir.listFiles();
        BigDecimal sum = new BigDecimal(0).setScale(1, RoundingMode.HALF_UP);
        for (File file : files) {
            if (file.isDirectory()) {
                sum = sum.add(dirSum(file));
            } else {
                sum = sum.add(new BigDecimal(file.length()).setScale(1, RoundingMode.HALF_UP));
            }
        }
        return sum;
    }

    String getWeight() {
        if (flags.cFlag()) {
            return degree(this.sum()).trim();
        } else {
            StringBuilder str = new StringBuilder();
            for (BigDecimal weight: weights){
                str.append(degree(weight));
            }
            return str.toString().trim();
        }
    }

    private BigDecimal sum() {
        BigDecimal sum = new BigDecimal(0.0).setScale(1, RoundingMode.HALF_UP);
        for (BigDecimal weight : weights) {
            sum = sum.add(weight);
        }
        return sum;
    }

    private String degree(BigDecimal weight) {
        int divisor = (flags.siFlag() ? 1000 : 1024);
        BigDecimal bdDivisor = new BigDecimal(divisor).setScale(1, RoundingMode.HALF_UP);
        String[] degrees = {"B", "KB", "MB", "GB", "TB", "PB"};
        if (flags.hFlag()) {
            for (String degree: degrees){
                if (weight.compareTo(bdDivisor) < 0 ){
                    return weight.toString() + degree + " ";
                } else {
                    weight = weight.divide(bdDivisor, RoundingMode.HALF_UP);
                }
            }
            return weight.toString() + "PB ";
        } else {
            return weight.divide(bdDivisor, RoundingMode.HALF_UP).toString() + " ";
        }
    }

    @Override
    public String toString() {
        return "FileWeight{" +
                "weights=" + weights +
                ", flags=" + flags +
                '}';
    }
}
