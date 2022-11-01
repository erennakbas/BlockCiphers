import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
public class FileCipher {
    public static void main(String[] args) {
        try {
            IOHandler IOHandler = new IOHandler(args[2], args[4], args[7]);
            //encrypt or decrypt
            String enOrDecrypt = args[0].equals("-e") ? "enc" : "dec";
            String encryptionType = args[5].equals("DES") ? "DES" : "TripleDES"; //algorithm
            String encryptionMode = args[6]; //CBC, OFB, CFB CTR
            Mode mode;
            Instant starts = Instant.now();
            //Initialize the mode variable according to mode input
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
            //encrypt
            if (enOrDecrypt.equals("enc")){
                byte[] plaintextByBytes = IOHandler.readPlaintext();
                ArrayList<byte[]> encryptedBlocks = mode.encrypt(plaintextByBytes, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeEncrypted(encryptedBlocks);
            }
            //decrypt
            else{
                ArrayList<byte[]> encryptedBlocks = IOHandler.readCipherText();
                ArrayList<byte[]> plainBlocks= mode.decrypt(encryptedBlocks, IOHandler.getKey(), IOHandler.getIV());
                IOHandler.writeDecrypted(plainBlocks);
            }
            Instant ends = Instant.now();
            //Calculate the spent time
            long millis = Duration.between(starts, ends).toMillis();
            //Log the message
            String log=args[2]+" "+args[4]+" "+enOrDecrypt+" "+args[5]+" "+encryptionMode+" "+millis+"\n";
            IOHandler.writeLogFile(log);
            IOHandler.closeFiles();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}