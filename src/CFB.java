import java.util.ArrayList;
import java.util.Arrays;

public class CFB extends Mode{

    public CFB(String algorithm){
        super(algorithm);
    }
    public ArrayList<byte[]> encrypt( byte[] plainText, byte[] key, byte[] IV) throws Exception {
        DES des = new DES();
        des.init(key, this.algorithm);
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        byte[] block;
        int offset=0;
        while(offset<plainText.length){
            block= Arrays.copyOfRange(plainText, offset, offset+8);
            byte[] encryptedBlock = des.encrypt(IV);
            byte[] result= XORBytes(block,encryptedBlock);
            for (byte b: result) {
                System.out.print(b+",");
            }
            results.add(result);
            IV = result;
            offset+=8;
        }
        return results;
    }
    public String decrypt(ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception{
        DES des = new DES();
        des.init(key, this.algorithm);
        cipherArrList.add(0, IV);

        System.out.println("Cipher Arr List elemanları:");
        for (byte[] asd: cipherArrList){
            for (byte z: asd){
                System.out.print(z+",");
            }
            System.out.print("/");
        }
        byte[] Ci;
        byte[] block;
        ArrayList<String> results = new ArrayList<String>();
        for (int i=cipherArrList.size()-1; i>=1; i--){
            Ci = cipherArrList.get(i);
            block = des.encrypt(cipherArrList.get(i-1));
            String decryptedPlainBlock= des.encode(XORBytes(block, Ci));
            results.add(0,decryptedPlainBlock);
        }
        return String.join("", results);
    }
}
