import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class CBC {
    public static byte[] XORBytes(byte[] arr1, byte[] arr2) {
        byte[] out = new byte[arr1.length];
        for (int i = 0 ; i < arr1.length ; i++) {
            out[i] = (byte)((arr1[i] ^ arr2[i]) & 0xff);
        }
        System.out.println("Xor length"+out.length);
        return out;
    }

    public ArrayList<byte[]> encrypt(String algorithm,byte[] plainText,byte[] key,byte[] IV) throws Exception {
            DES des = new DES();
            des.init(key);
            ArrayList<byte[]> results = new ArrayList<byte[]>();
            byte[] block = new byte[8];
            results.add(IV);
            int offset=0;
            while(offset<plainText.length){
                block=Arrays.copyOfRange(plainText, offset, offset+8);
                System.out.println("block size"+block.length+" "+"iv size"+IV.length);
                byte[] encryptedBlock = des.encrypt(XORBytes(block, IV), offset);
                results.add(encryptedBlock);
                IV = encryptedBlock;
                offset+=8;
            }
            return results;
    }
    public String decrypt(String algorithm,ArrayList<byte[]> cipherArrList, byte[] key) throws Exception{
        DES des = new DES();
        des.init(key);
        byte[] Ci = new byte[8];
        byte[] block = new byte[8];
        ArrayList<String> results = new ArrayList<String>();
        for (int i=cipherArrList.size()-1; i>=1; i--){
            Ci = cipherArrList.get(i);
            block = des.decrypt(Ci);
            String decryptedPlainBlock= des.encode(XORBytes(block, cipherArrList.get(i-1)));
            results.add(0,decryptedPlainBlock);
        }
        return String.join("", results);
    }

}