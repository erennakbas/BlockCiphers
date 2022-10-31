import java.util.ArrayList;
import java.util.Arrays;

public class CTR extends Mode{
    public ArrayList<byte[]> preEncryptedBlocks = new ArrayList<>();

    public CTR(String algorithm, byte[] plainText, byte[] key, byte[] counter) throws Exception{
        super(algorithm);
        DES des = new DES();
        des.init(key, this.algorithm);
        int offset=0;
        while(offset<plainText.length){
            long lng =byteToLong(counter);
            lng++;
            byte[] bl =longToByte(lng);
            counter=bl;
            byte[] encryptedBlock = des.encrypt(counter);
            preEncryptedBlocks.add(encryptedBlock);
            offset+=8;
        }
    }
    public long byteToLong(byte[] IV){
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
    public ArrayList<byte[]> encrypt(byte[] plainText, byte[] key, byte[] IV) throws Exception {
        DES des = new DES();
        des.init(key, this.algorithm);
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        for (int i=0;i<preEncryptedBlocks.size();i++) {
            byte[] result = XORBytes(Arrays.copyOfRange(plainText, i*8, i*8+8), preEncryptedBlocks.get(i));
            results.add(result);
        }
        return results;
    }
    @Override
    public ArrayList<byte[]> decrypt(ArrayList<byte[]> cipherArrList, byte[] key, byte[] IV) throws Exception{
        DES des = new DES();
        des.init(key, this.algorithm);
        byte[] Ci;
        byte[] block;
        ArrayList<byte[]> results = new ArrayList<byte[]>();
        for (int i=0; i<cipherArrList.size(); i++){
            Ci = cipherArrList.get(i);
            block = preEncryptedBlocks.get(i);
            byte[] decryptedPlainBlock= XORBytes(block, Ci);
            results.add(decryptedPlainBlock);
        }
        return results;
    }
}
