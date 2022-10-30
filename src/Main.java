import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        try {
            IOHandler IOHandler = new IOHandler("input.txt", "output.txt", "myLogger.log", "keyFile.txt");
            String encryptionType = "dec";
            String encryptionMode = "ofb";
            Mode mode;
            switch (encryptionMode) {
                case "cbc":
                    mode = new CBC();
                    break;
                case "ofb":
                    if(encryptionType.equals("enc")){
                        mode = new OFB(IOHandler.readPlaintext(), IOHandler.getKey(), IOHandler.getIV());
                    }
                    else {
                        mode = new OFB(IOHandler.readCipherText(), IOHandler.getKey(), IOHandler.getIV());
                    }
                    break;
                case "cfb":
                    mode = new CFB();
                    break;
                case "ctr":
                    mode = new CTR();
                    break;
                default:
                    mode = new CBC();

            }

            if (encryptionType.equals("dec")){
                ArrayList<byte[]> encryptedBlocks = IOHandler.readCipherText();
                String plaintext = mode.decrypt("DES",encryptedBlocks, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeOutputFile(plaintext);
            }
            else if (encryptionType.equals("test")){
                byte[] plaintextByBytes = IOHandler.readPlaintext();
                ArrayList<byte[]> encryptedBlocks = mode.encrypt("DES",plaintextByBytes, IOHandler.getKey(), IOHandler.getIV());
                String plaintext = mode.decrypt("DES",encryptedBlocks, IOHandler.getKey(), IOHandler.getIV());
                System.out.println(plaintext);
            }
            else{
                byte[] plaintextByBytes = IOHandler.readPlaintext();
                ArrayList<byte[]> encryptedBlocks = mode.encrypt("DES",plaintextByBytes, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeOutputFile(encryptedBlocks);

            }
            IOHandler.closeFiles();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}