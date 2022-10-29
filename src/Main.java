import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        /*try {
            IOHandler IOHandler = new IOHandler("input.txt", "output.txt", "myLogger.log", "keyFile.txt");
            byte[] data = IOHandler.readPlaintext();
            InputStream inputStream = new ByteArrayInputStream(data);
            System.out.println(inputStream.readAllBytes());
            String myPlaintext = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println(myPlaintext);
            IOHandler.writeOutputFile(data);
            IOHandler.writeLogFile("inputfile.txt encrypted.txt enc DES CBC 64");
            System.out.println(IOHandler.getKeyAsString()+" "+ IOHandler.getIVAsString()+" "+ IOHandler.getCounterValAsString());
            IOHandler.closeFiles();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }*/
        try {
            IOHandler IOHandler = new IOHandler("input.txt", "output.txt", "myLogger.log", "keyFile.txt");
            //byte[] plaintextByBytes = IOHandler.readPlaintext();
            ArrayList<byte[]> encryptedBlocks = IOHandler.readCipherText();
//            System.out.println((int) plaintextByBytes[0]);
//            System.out.println(plaintextByBytes.length);
            CBC encryptionMode = new CBC();
//            ArrayList<byte[]> encryptedBlocks = encryptionMode.encrypt("DES",plaintextByBytes, IOHandler.getKey(), IOHandler.getIV());
            System.out.println(encryptionMode.decrypt("DES",encryptedBlocks, IOHandler.getKey()));
//            encryptedBlocks.remove(0);
//            for (byte[] b:encryptedBlocks) {
//                IOHandler.writeOutputFile(b);
//                for (byte x: b) System.out.print(x+",");
//                for (byte x: b) System.out.print((char)x);
//
//            }

            IOHandler.closeFiles();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}