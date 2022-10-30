import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DES {
    private SecretKey key;
    private final int KEY_SIZE = 64;
    private Cipher encryptionCipher;

    public void init(byte[] secretKey) throws Exception {
        key=new SecretKeySpec(secretKey, 0, secretKey.length, "DES");
    }

    public byte[] encrypt(byte[] messageBlock, int inputOffset) throws Exception {
        encryptionCipher = Cipher.getInstance("DES/ECB/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.update(messageBlock);
        return encryptedBytes;
    }

    public byte[] decrypt(byte[] encryptedBlock) throws Exception {

        System.out.println("Decrypt edilecek byte değerleri:");
        for (byte b: encryptedBlock) System.out.print(b+",");
        System.out.println();
        Cipher decryptionCipher = Cipher.getInstance("DES/ECB/NoPadding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = decryptionCipher.update(encryptedBlock);
        System.out.println("Decrypt edilince byte değerleri:");
        for (byte x: decryptedBytes) System.out.print(x+",");
        System.out.println();
        return decryptedBytes;
    }

    public String encode(byte[] data) {
        StringBuilder builder = new StringBuilder();
        System.out.println("----");
        for (byte i: data) {
            if ((int) i!=0) builder.append((char)i);
            System.out.print((char)i +"");
        }
        System.out.println();
        for (byte i: data) {
            System.out.print(i +",");
        }
        System.out.println("----");
//        String utf8String = new String(data, StandardCharsets.UTF_8);
//        return utf8String;
        return builder.toString();
    }

    public byte[] decode(String data) {
        return data.getBytes(StandardCharsets.UTF_8);
    }
}