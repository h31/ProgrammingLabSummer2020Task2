package xor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XORTest {

    @Test
    void encodeDecode_1() {
        XOR xor = new XOR();
        String text = "123456";
        String key = "key";
        assertEquals(text, xor.xorMethod(xor.xorMethod(text, key), key));
    }

    @Test
    void encodeDecode_2() {
        XOR xor = new XOR();
        String text = "123456";
        String key = "key";
        String encodeText = xor.xorMethod(text, key);
        assertNotEquals(encodeText, key);
        assertEquals(text, xor.xorMethod(encodeText, key));
    }

    @Test
    void encodeDecode_3() {
        XOR xor = new XOR();
        String text = "123456";
        String key1 = "key1";
        String key2 = "key2";
        String encodeText = xor.xorMethod(text, key1);
        assertNotEquals(encodeText, key1);
        assertNotEquals(text, xor.xorMethod(encodeText, key2));
    }

    @Test
    void encodeDecode_eng() {
        XOR xor = new XOR();
        String text = "hello";
        String key = "key";
        assertEquals(text, xor.xorMethod(xor.xorMethod(text, key), key));
    }

    @Test
    void encodeDecode_rus() {
        XOR xor = new XOR();
        String text = "привет";
        String key = "key";
        assertEquals(text, xor.xorMethod(xor.xorMethod(text, key), key));
    }

    @Test
    void encodeDecode_lines() {
        XOR xor = new XOR();
        String text = "привет\nкак дела\n";
        String key = "key";
        assertEquals(text, xor.xorMethod(xor.xorMethod(text, key), key));
    }
}