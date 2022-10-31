import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        try {
            IOHandler IOHandler = new IOHandler("input.txt", "output.txt", "myLogger.log", "keyFile.txt");
            String enOrDecrypt = "DEC";
            String encryptionType = "DES";
            if (encryptionType.equals("3DES")) encryptionType="TripleDES";
            String encryptionMode = "CTR";
            Mode mode;
            switch (encryptionMode) {
                case "CBC":
                    mode = new CBC(encryptionType);
                    break;
                case "OFB":
                    mode = new OFB(encryptionType);
                    break;
                case "CFB":
                    mode = new CFB(encryptionType);
                    break;
                case "CTR":
                    mode = new CTR(encryptionType,IOHandler.readPlaintext(), IOHandler.getKey(), IOHandler.getCounterVal());
                    break;
                default:
                    mode = new CBC(encryptionType);
            }

            if (enOrDecrypt.equals("DEC")){
                ArrayList<byte[]> encryptedBlocks = IOHandler.readCipherText();
                String plaintext = mode.decrypt(encryptedBlocks, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeOutputFile(plaintext);
            }
            else if (enOrDecrypt.equals("TEST")){
                byte[] plaintextByBytes = IOHandler.readPlaintext();
                ArrayList<byte[]> encryptedBlocks = mode.encrypt(plaintextByBytes, IOHandler.getKey(), IOHandler.getIV());
                String plaintext = mode.decrypt(encryptedBlocks, IOHandler.getKey(), IOHandler.getIV());
                System.out.println(plaintext);
            }
            else{
                byte[] plaintextByBytes = IOHandler.readPlaintext();
                ArrayList<byte[]> encryptedBlocks = mode.encrypt(plaintextByBytes, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeOutputFile(encryptedBlocks);

            }
            IOHandler.closeFiles();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}