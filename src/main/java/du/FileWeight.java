package du;

import java.math.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

class FileWeight {
    private List<BigDecimal> weights = new ArrayList<>();
    private Flags flags;
    FileWeight(Arguments args) {
        try {
            for (int i = 0; i != args.getFiles().size(); i++) {
                if (!new File(args.getFiles().get(i)).exists()) throw new IOException();
                File file = new File(args.getFiles().get(i));
                if (file.isDirectory()) weights.add(dirSum(file));
                else weights.add(new BigDecimal(file.length()).setScale(1, RoundingMode.HALF_UP));
            }
        } catch (IOException e) {
            System.exit(2);
        }
        flags = args.getFlags();
    }
    private BigDecimal dirSum(File dir) throws IOException {
        File[] elements = dir.listFiles();
        BigDecimal sum = new BigDecimal(0).setScale(1, RoundingMode.HALF_UP);
        for (int j = 0; j!= elements.length; j++){
            File file = elements[j];
            if (file.isDirectory()) {
                sum = sum.add(dirSum(file));
            }else {
                BigDecimal test = new BigDecimal(file.length()).setScale(1,RoundingMode.HALF_UP);
                sum = sum.add(new BigDecimal(file.length()).setScale(1,RoundingMode.HALF_UP));
            }
        }
        return sum;
    }

    private BigDecimal sum(){
        BigDecimal sum = new BigDecimal(0.0).setScale(1,RoundingMode.HALF_UP);
        for (int i = 0; i != weights.size(); i++){
            sum = sum.add(weights.get(i));
        }
        return sum;
    }

    String degree(){
        int divisor = (flags.siFlag()? 1000:1024);
        BigDecimal bdDivisor = new BigDecimal(divisor).setScale(1,RoundingMode.HALF_UP);
        String[] degrees = { "B", "KB", "MB", "GB","TB","PB"};
        if (flags.cFlag()){
            BigDecimal sum = this.sum();
            if (flags.hFlag()){
                int i = 0;
                while (sum.compareTo(bdDivisor)>0 && i != degrees.length - 1){
                    sum = sum.divide(bdDivisor, RoundingMode.HALF_UP);
                    i++; //Протестировать
                }
                return sum.toString()+degrees[i];
            } else {
                return sum.divide(bdDivisor, RoundingMode.HALF_UP).toString();
            }
        } else {
            StringBuilder str = new StringBuilder();
            if(flags.hFlag()){
                for (int j = 0; j != weights.size(); j++){
                    int i = 0;
                    while (weights.get(j).compareTo(bdDivisor)>0 && i != degrees.length - 1){
                        weights.set(j, weights.get(j).divide(bdDivisor, RoundingMode.HALF_UP));
                        i++;
                    }
                    str.append(weights.get(j).toString()).append(degrees[i]).append(" ");
                }
                return str.toString();
            } else {
                for (int j = 0; j != weights.size(); j++){

                    str.append(weights.get(j).divide(bdDivisor, RoundingMode.HALF_UP).toString()).append(" ");
                }
                return str.toString();
            }
        }
     }


}
