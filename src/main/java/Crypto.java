import java.util.ArrayList;

public class Crypto {
    byte[] encode(String text, long key) {
        byte[] byteText = text.getBytes();
        byte[] data = new byte[byteText.length];
        ArrayList<Integer> keyNumber = new ArrayList<>();
        long temp = key;
        int j = 1;
        while (temp > 0) {
            keyNumber.add((int) (temp % (10 * j)));
            temp /= 10;
            j++;
        }
        for (int i = 0; i < byteText.length; i++)
            data[i] = (byte) (byteText[i] ^ keyNumber.get(i % keyNumber.size()));
        return data;
    }

    byte[] decode(byte[] data, long key) {
        byte[] text = new byte[data.length];
        ArrayList<Integer> keyNumber = new ArrayList<>();
        long temp = key;
        int j = 1;
        while (temp > 0) {
            keyNumber.add((int) (temp % (10 * j)));
            temp /= 10;
            j++;
        }
        for (int i = 0; i < data.length; i++)
            text[i] = (byte) (data[i] ^ keyNumber.get(i % keyNumber.size()));
        return text;
    }
}
