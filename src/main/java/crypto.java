public class crypto {
    byte[] encode(String text, String key) {
        System.out.println(text);
        byte[] byteText = text.getBytes();
        byte[] byteKey = key.getBytes();
        byte[] data = new byte[byteText.length];
        for (int i = 0; i < byteText.length; i++) {
            data[i] = (byte) (byteText[i] ^ byteKey[i % byteKey.length]);
        }
        return data;
    }

    String decode(byte[] data, String key) {
        byte[] text = new byte[data.length];
        byte[] byteKey = key.getBytes();
        for (int i = 0; i < data.length; i++) {
            text[i] = (byte) (data[i] ^ byteKey[i % byteKey.length]);
        }
        return new String(text);
    }
}
