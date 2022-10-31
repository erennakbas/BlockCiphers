import java.util.ArrayList;
import java.util.Arrays;

public class OFB extends Mode{
    public ArrayList<byte[]> preEncryptedBlocks;
    public OFB(String algorithm){
        super(algorithm);
    }
    public void preEncrypt(byte[] input, byte[] key, byte[] IV)throws Exception{
        preEncryptedBlocks= new ArrayList<byte[]>();
        DES des = new DES();
        des.init(key, this.algorithm);
        int offset=0;
        while(offset<input.length){
            byte[] encryptedBlock = des.encrypt(IV);
            preEncryptedBlocks.add(encryptedBlock);
            IV = encryptedBlock;
            offset+=8;
        }
    }
    public void preEncrypt(ArrayList<byte[]> input, byte[] key, byte[] IV)throws Exception{
        preEncryptedBlocks= new ArrayList<byte[]>();
        DES des = new DES();
        des.init(key, this.algorithm);
        int counter=0;
        while(counter<input.size()){
            byte[] encryptedBlock = des.encrypt(IV);
            preEncryptedBlocks.add(encryptedBlock);
            IV = encryptedBlock;
            counter+=1;
        }
    }

    @Override
    public ArrayList<byte[]> encrypt(byte[] plainText, byte[] key, byte[] IV) throws Exception {
        this.preEncrypt(plainText, key, IV);
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        byte[] block;
        int offset=0;
        int counter=0;
        while(offset<plainText.length){
            block= Arrays.copyOfRange(plainText, offset, offset+8);
            byte[] encryptedBlock=preEncryptedBlocks.get(counter);
            byte[] finalEncryptedBlock = XORBytes(encryptedBlock, block);
            results.add(finalEncryptedBlock);
            offset+=8;
            counter+=1;
        }
        return results;
    }
    @Override
    public ArrayList<byte[]> decrypt(ArrayList<byte[]> cipherArrList, byte[] key,byte[] IV) throws Exception{
        this.preEncrypt(cipherArrList, key, IV);
        DES des = new DES();
        des.init(key, this.algorithm);
        byte[] Ci;
        byte[] block;
        ArrayList<byte[]> results = new ArrayList<byte[]>();

        for (int i=cipherArrList.size()-1; i>=0; i--){
            Ci = cipherArrList.get(i);
            block = preEncryptedBlocks.get(i);
            byte[] decryptedPlainBlock= XORBytes(block, Ci);
            results.add(0,decryptedPlainBlock);
        }
        return results;
    }
}
