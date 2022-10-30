import java.sql.SQLOutput;
import java.util.ArrayList;

public abstract class Mode {
    public byte[] XORBytes(byte[] arr1, byte[] arr2) {
        byte[] out = new byte[arr1.length];
        for (int i = 0 ; i < arr1.length ; i++) {
            out[i] = (byte)(arr1[i] ^ arr2[i]);
        }
        System.out.println("AFTER XOR");
        for (byte z: out){
            System.out.print(z+",");
        }
        System.out.println("/");
        return out;
    }
    public abstract ArrayList<byte[]> encrypt(String algorithm, byte[] plainText, byte[] key, byte[] IV) throws Exception;
    public abstract String decrypt(String algorithm,ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception;
}
