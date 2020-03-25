import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class crypto {
    byte[] encode(String pText, String pKey) {
        System.out.println(pText);
        byte[] txt = pText.getBytes();
        byte[] key = pKey.getBytes();
        byte[] res = new byte[txt.length];

        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ key[i % key.length]);
        }

        return res;
    }

    String decode(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();

        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }

        return new String(res);
    }
}
