import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class crypto {
    byte[] encode(String data, String key) {
        //return data + key;
        byte[] dataByte = data.getBytes();
        byte[] keyByte = key.getBytes();
        System.out.println("data " + Arrays.toString(dataByte));
        byte[] result = new byte[dataByte.length];
        String res = "";
        for(int i = 0; i < dataByte.length; i++) {
            result[i] = (byte) (dataByte[i] ^ keyByte[i % keyByte.length]);
            //res += result[i];
        }
        System.out.println("result " + result);
        return result;

    }

    String decode(byte[] data, String key) {
        byte[] result = new byte[data.length];
        byte[] bkey = key.getBytes();

        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ bkey[i % bkey.length]);
        }
        return new String(result);

    }
}
