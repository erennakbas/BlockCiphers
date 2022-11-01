import java.util.ArrayList;
import java.util.Arrays;

public class CFB extends Mode{
    public CFB(String algorithm){
        super(algorithm);
    }
    //◦ Encryption: C0=IV, Ci= Ek[Ci-1] ⊕ Pi
    @Override
    public ArrayList<byte[]> encrypt( byte[] plainText, byte[] key, byte[] IV) throws Exception {
        DES des = new DES();
        des.init(key, this.algorithm);
        //ArrayList to store encrypted blocks.
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        byte[] block;
        int offset=0;
        while(offset<plainText.length){
            //Get the block which is 8 byte from the plainText byte array
            block= Arrays.copyOfRange(plainText, offset, offset+8);
            //First encrypt Ci-1 to get Ci then xor with Pi .
            byte[] encryptedBlock = des.encrypt(IV);
            byte[] result= XORBytes(block,encryptedBlock);
            results.add(result);
            IV = result;
            offset+=8;
        }
        return results;
    }
    //◦ Decryption: C0=IV, Pi= Ek[Ci-1] ⊕ Ci
    @Override
    public ArrayList<byte[]> decrypt(ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception{
        DES des = new DES();
        des.init(key, this.algorithm);
        //C0 = IV
        cipherArrList.add(0, IV);
        byte[] Ci;
        byte[] block;
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        for (int i=cipherArrList.size()-1; i>=1; i--){
            Ci = cipherArrList.get(i);
            ////First encrypt Ci-1 to get then xor with Ci .
            block = des.encrypt(cipherArrList.get(i-1));
            byte[] decryptedPlainBlock= XORBytes(block, Ci);
            results.add(0,decryptedPlainBlock);
        }
        return results;
    }
}
