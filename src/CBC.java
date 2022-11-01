import java.util.ArrayList;
import java.util.Arrays;

public class CBC extends Mode{
    public CBC(String algorithm){
        super(algorithm);
    }
    //◦ Encryption: Ci= Ek [Pi⊕Ci-1], with C0=IV
    @Override
    public ArrayList<byte[]> encrypt(byte[] plainText,byte[] key,byte[] IV) throws Exception {
        DES des = new DES();
        des.init(key, this.algorithm);
        //ArrayList to store encrypted blocks.
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        byte[] block;
        int offset=0;
        while(offset<plainText.length){
            //Get the block which is 8 byte from the plainText byte array
            block=Arrays.copyOfRange(plainText, offset, offset+8);
            //First execute xor then encrypt.
            byte[] encryptedBlock = des.encrypt(XORBytes(block, IV));
            results.add(encryptedBlock);
            //IV was C0 in the beginning, it is updated in each step it goes like this: C1,C2,C3...
            IV = encryptedBlock;
            offset+=8;
        }
        return results;
    }
    //◦ Decryption: Pi= Ci-1⊕Dk[Ci] with C0=IV
    @Override
    public ArrayList<byte[]> decrypt(ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception{
        DES des = new DES();
        //add C0 = IV
        cipherArrList.add(0, IV);
        des.init(key, this.algorithm);
        byte[] Ci;
        byte[] block;
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        for (int i=cipherArrList.size()-1; i>=1; i--){
            Ci = cipherArrList.get(i);
            block = des.decrypt(Ci);
            byte[] decryptedBlock= XORBytes(block, cipherArrList.get(i-1));
            results.add(0,decryptedBlock);
        }
        return results;
    }}