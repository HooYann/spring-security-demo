package cn.beautybase.authorization.third.wechat.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PKCS7Encoder {
    private static final Charset CHARSET;
    private static final int BLOCK_SIZE = 32;

    public PKCS7Encoder() {
    }

    public static byte[] encode(int count) {
        int amountToPad = 32 - count % 32;
        char padChr = chr(amountToPad);
        StringBuilder tmp = new StringBuilder();

        for(int index = 0; index < amountToPad; ++index) {
            tmp.append(padChr);
        }

        return tmp.toString().getBytes(CHARSET);
    }

    public static byte[] decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }

        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    private static char chr(int a) {
        byte target = (byte)(a & 255);
        return (char)target;
    }

    static {
        CHARSET = StandardCharsets.UTF_8;
    }
}