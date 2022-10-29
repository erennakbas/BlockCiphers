import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
            int count=0;
            int j=0;
            for (int i = 0; i < data.length; i++) {
                if (data[i] == 0) {
                    count++;
                }
            }
            byte[] newData=new byte[data.length-count];
            for (int i = 0; i < data.length; i++) {
                if (data[i] == 0) {
                    continue;
                }else{
                    newData[j]=data[i];
                    j++;
                }
            }

            return new String(newData, StandardCharsets.UTF_8);
        }

        public byte[] decode(String data) {
            return Base64.getDecoder().decode(data);
        }
    }
