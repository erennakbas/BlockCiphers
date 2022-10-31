import java.util.ArrayList;
import java.util.Arrays;

public class CBC extends Mode{
    public CBC(String algorithm){
        super(algorithm);
    }
    @Override
    public ArrayList<byte[]> encrypt(byte[] plainText,byte[] key,byte[] IV) throws Exception {
        DES des = new DES();
        des.init(key, this.algorithm);
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        byte[] block = new byte[8];
        int offset=0;
        while(offset<plainText.length){
            block=Arrays.copyOfRange(plainText, offset, offset+8);
            byte[] encryptedBlock = des.encrypt(XORBytes(block, IV));//byte[] encryptedBlock = des.encrypt(XORBytes(block, IV), offset);
//            for (int i=0; i<encryptedBlock.length;i++){
//                System.out.println(encryptedBlock[i]);
//                if (encryptedBlock[i] < 0) encryptedBlock[i] = (byte) (encryptedBlock[i]+256);
//                System.out.println(encryptedBlock);
//            }
            for (byte b: encryptedBlock) {
                System.out.print(b+",");
            }
            results.add(encryptedBlock);
            IV = encryptedBlock;
            offset+=8;
        }
        return results;
    }
    @Override
    public ArrayList<byte[]> decrypt(ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception{
        DES des = new DES();
        cipherArrList.add(0, IV);
        des.init(key, this.algorithm);
        System.out.println("Cipher Arr List elemanları:");
        for (byte[] asd: cipherArrList){
            for (byte z: asd){
                System.out.print(z+",");
            }
            System.out.print("/");
        }
        byte[] Ci = new byte[8];
        byte[] block = new byte[8];
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        for (int i=cipherArrList.size()-1; i>=1; i--){
            Ci = cipherArrList.get(i);
            block = des.decrypt(Ci);
            byte[] decryptedBlock= XORBytes(block, cipherArrList.get(i-1));
            results.add(0,decryptedBlock);
        }
        return results;
    }}