import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
        byte[] encryptedBytes = encryptionCipher.doFinal(messageBlock);
        return encryptedBytes;
    }

    public byte[] decrypt(byte[] encryptedBlock) throws Exception {

        Cipher decryptionCipher = Cipher.getInstance("DES/ECB/NoPadding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = decryptionCipher.doFinal(encryptedBlock);
        return decryptedBytes;
    }

    public String encode(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (byte i: data) {
            if ((int) i!=0) builder.append((char)i);
        }
        return builder.toString();
    }

    public byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}