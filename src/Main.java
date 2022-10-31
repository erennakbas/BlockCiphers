import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        try {
            IOHandler IOHandler = new IOHandler(args[2], args[4], args[7]);
            String enOrDecrypt = args[0];
            String encryptionType = args[5];
            if (encryptionType.equals("3DES")) encryptionType="TripleDES";
            String encryptionMode = args[6];
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
            Instant starts = Instant.now();
            if (enOrDecrypt.equals("-d")){
                ArrayList<byte[]> encryptedBlocks = IOHandler.readCipherText();
                ArrayList<byte[]> plainBlocks= mode.decrypt(encryptedBlocks, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeDecrypted(plainBlocks);
            }
            else{
                byte[] plaintextByBytes = IOHandler.readPlaintext();
                ArrayList<byte[]> encryptedBlocks = mode.encrypt(plaintextByBytes, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeEncrypted(encryptedBlocks);
            }
            Instant ends = Instant.now();
            long millis = Duration.between(starts, ends).toMillis();
            String log="input.txt "+"output.txt "+enOrDecrypt+" "+encryptionType+" "+encryptionMode+" "+millis+"\n";
            IOHandler.writeLogFile(log);
            IOHandler.closeFiles();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}