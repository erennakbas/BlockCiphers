import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES {
    private SecretKey key;
    private String algorithm;
    private Cipher encryptionCipher;

    public void init(byte[] secretKey, String algorithm) throws Exception {
        this.algorithm = algorithm;
        if (algorithm.equals("TripleDES")) {
            byte[] newSecretKey = new byte[24];
            for (int i = 1; i <= 3; i++) {
                for (int j = 0; j < 8; j++) {
                    newSecretKey[j * i] = secretKey[i];
                }
            }
            secretKey = newSecretKey;
        }
        key = new SecretKeySpec(secretKey, 0, secretKey.length, algorithm);
    }

    public byte[] encrypt(byte[] messageBlock) throws Exception {
        encryptionCipher = Cipher.getInstance(this.algorithm + "/ECB/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = encryptionCipher.update(messageBlock);
        return encryptedBytes;
    }

    public byte[] decrypt(byte[] encryptedBlock) throws Exception {
        Cipher decryptionCipher = Cipher.getInstance(this.algorithm + "/ECB/NoPadding");
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = decryptionCipher.update(encryptedBlock);
        return decryptedBytes;
    }
}