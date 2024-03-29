import java.util.ArrayList;

public abstract class Mode {
    String algorithm;
    public Mode(String algorithm){
        this.algorithm = algorithm;
    }

    //Helper method for XORing two byte arrays.
    public byte[] XORBytes(byte[] arr1, byte[] arr2) {
        byte[] out = new byte[arr1.length];
        for (int i = 0 ; i < arr1.length ; i++) {
            //Xor operation
            out[i] = (byte)(arr1[i] ^ arr2[i]);
        }
        return out;
    }
    //Abstract methods
    public abstract ArrayList<byte[]> encrypt(byte[] plainText, byte[] key, byte[] IV) throws Exception;
    public abstract ArrayList<byte[]> decrypt(ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception;
}
