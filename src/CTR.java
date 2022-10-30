import java.util.ArrayList;
import java.util.Arrays;

public class CTR extends Mode{
    public ArrayList<byte[]> preEncryptedBlocks;

    public CTR(byte[] plainText, byte[] key, byte[] counter) throws Exception{
        DES des = new DES();
        des.init(key);
        int offset=0;
        byte[] block = new byte[8];
        while(offset<plainText.length){
            block= Arrays.copyOfRange(plainText, offset, offset+8);
            long lng =byteToLOng(counter);
            lng =lng++;
            byte[] bl =longToByte(lng);
            counter=bl;
            byte[] encryptedBlock = des.encrypt(counter);
            for (byte b: encryptedBlock) {
                System.out.print(b+",");
            }
            preEncryptedBlocks.add(encryptedBlock);
            offset+=8;
        }
    }
    public long byteToLOng(byte[] IV){
        long lng = ((long) IV[7] << 56)
                | ((long) IV[6] & 0xff) << 48
                | ((long) IV[5] & 0xff) << 40
                | ((long) IV[4] & 0xff) << 32
                | ((long) IV[3] & 0xff) << 24
                | ((long) IV[2] & 0xff) << 16
                | ((long) IV[1] & 0xff) << 8
                | ((long) IV[0] & 0xff);
        return lng;
    }
    public byte[] longToByte(long lng){
        byte[] bl = new byte[] {
                (byte) lng,
                (byte) (lng >> 8),
                (byte) (lng >> 16),
                (byte) (lng >> 24),
                (byte) (lng >> 32),
                (byte) (lng >> 40),
                (byte) (lng >> 48),
                (byte) (lng >> 56)};
        return bl;
    }
    @Override
    public ArrayList<byte[]> encrypt(String algorithm, byte[] plainText, byte[] key, byte[] IV) throws Exception {
        DES des = new DES();
        des.init(key);
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        byte[] block = new byte[8];

        for (int i=0;i<preEncryptedBlocks.size();i++) {
            byte[] result = XORBytes(Arrays.copyOfRange(plainText, i*8, i*8+8), preEncryptedBlocks.get(i));
            results.add(result);
        }
        return results;
    }
    @Override
    public String decrypt(String algorithm,ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception{
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
        for (int i=0; i<cipherArrList.size(); i++){
            Ci = cipherArrList.get(i);
            block = preEncryptedBlocks.get(i);
            String decryptedPlainBlock= des.encode(XORBytes(block, Ci));
            results.add(decryptedPlainBlock);
        }
        return String.join("", results);
    }
}
