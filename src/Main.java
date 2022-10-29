import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        try {
            IOHandler IOHandler = new IOHandler("input.txt", "output.txt", "myLogger.log", "keyFile.txt");
            byte[] data = IOHandler.readPlaintext();
            InputStream inputStream = new ByteArrayInputStream(data);
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
        }
    }
}