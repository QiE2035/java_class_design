package moe.qie2035.market.utils;

import lombok.SneakyThrows;
import moe.qie2035.market.Const;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Crypto {
    public static final String KEY = "QiE2035CryptoKey";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private Crypto() {
    }

    @SneakyThrows
    public static String enc(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            byte[] bytes = key.getBytes();
            byte[] keyBytes = new byte[16];
            for (int i = 0; i < 16; i++) {
                keyBytes[i] = bytes[i % bytes.length];
            }
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
            return new String(Base64.getEncoder().encode(cipher.doFinal(data.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("加密" + Const.FAILED);
        }
    }


    public static String enc(String data) {
        return enc(data, KEY);
    }

    @SneakyThrows
    public static String dec(String data, String key) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("解密" + Const.FAILED);
        }
    }

    public static String dec(String data) {
        return dec(data, KEY);
    }
}
