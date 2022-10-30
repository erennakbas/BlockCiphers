import java.util.ArrayList;
import java.util.Arrays;

public class OFB extends Mode{
    public ArrayList<byte[]> preEncryptedBlocks;
    public OFB(byte[] input, byte[] key, byte[] IV)throws Exception{
        preEncryptedBlocks= new ArrayList<byte[]>();
        DES des = new DES();
        des.init(key);
        byte[] block = new byte[8];
        int offset=0;
        while(offset<input.length){
            byte[] encryptedBlock = des.encrypt(IV);
            preEncryptedBlocks.add(encryptedBlock);
            IV = encryptedBlock;
            offset+=8;
        }
    }
    public OFB(ArrayList<byte[]> input, byte[] key, byte[] IV)throws Exception{
        preEncryptedBlocks= new ArrayList<byte[]>();
        DES des = new DES();
        des.init(key);
        byte[] block = new byte[8];
        int counter=0;
        while(counter<input.size()){
            byte[] encryptedBlock = des.encrypt(IV);
            preEncryptedBlocks.add(encryptedBlock);
            IV = encryptedBlock;
            counter+=1;
        }
    }

    @Override
    public ArrayList<byte[]> encrypt(String algorithm, byte[] plainText, byte[] key, byte[] IV) throws Exception {
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        byte[] block = new byte[8];
        int offset=0;
        int counter=0;
        while(offset<plainText.length){
            block= Arrays.copyOfRange(plainText, offset, offset+8);
            byte[] encryptedBlock=preEncryptedBlocks.get(counter);
            byte[] finalEncriptedBlock = XORBytes(encryptedBlock, block);
            for (byte b: finalEncriptedBlock) {
                System.out.print(b+",");
            }
            results.add(finalEncriptedBlock);
            offset+=8;
            counter+=1;
        }
        return results;
    }
    @Override
    public String decrypt(String algorithm,ArrayList<byte[]> cipherArrList, byte[] key,byte[] IV) throws Exception{
        DES des = new DES();
        des.init(key);
        System.out.println("Cipher Arr List elemanları:");
        for (byte[] asd: cipherArrList){
            for (byte z: asd){
                System.out.print(z+",");
            }
            System.out.print("/");
        }
        byte[] Ci = new byte[8];
        byte[] block = new byte[8];
        ArrayList<String> results = new ArrayList<String>();

        for (int i=cipherArrList.size()-1; i>=0; i--){
            Ci = cipherArrList.get(i);
            block = preEncryptedBlocks.get(i);
            for (byte z: block){
                System.out.print(z+",");
            }
            System.out.println("////");
            String decryptedPlainBlock= des.encode(XORBytes(block, Ci));
            results.add(0,decryptedPlainBlock);
        }
        return String.join("", results);
    }
}
