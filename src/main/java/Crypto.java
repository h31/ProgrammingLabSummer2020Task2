public class Crypto {
    byte[] encode(String text, int key) {
        byte[] byteText = text.getBytes();
        byte byteKey = (byte)key;
        byte[] data = new byte[byteText.length];
        for (int i = 0; i < byteText.length; i++)
            data[i] = (byte) (byteText[i] ^ byteKey);
        return data;
    }

    String decode(byte[] data, int key) {
        byte[] text = new byte[data.length];
        byte byteKey = (byte)key;
        for (int i = 0; i < data.length; i++)
            text[i] = (byte) (data[i] ^ byteKey);
        return new String(text);
    }
}
