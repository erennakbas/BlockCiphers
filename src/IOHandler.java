import javax.crypto.CipherOutputStream;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class IOHandler {
    private byte[] key; //getBytes
    private byte[] initilizationVector; //getBytes
    private byte[] counterVal; //getBytes
    private Path inputPath;
    private FileInputStream fin;
    private FileInputStream fkey;
    private FileWriter fout;
    private FileOutputStream flogger;
    public IOHandler(String inputFile, String outputFile, String logFile, String keyFile){
        try {
            this.inputPath = Paths.get(inputFile);
            this.fin = new FileInputStream(inputFile);
            this.fout = new FileWriter(outputFile);
            this.flogger = new FileOutputStream(logFile);
            this.fkey = new FileInputStream(keyFile);
            this.readKeyFile();

        }catch(Exception e){System.out.println(e);}
    }
    private void readKeyFile() throws IOException{
        int i=0;
        StringBuilder s = new StringBuilder();
        while((i=fkey.read())!=-1){
            char myChar = (char) i;
            if (myChar == ' ') continue;
            s.append(myChar);
        }
        String[] arr = s.toString().split("-");
        this.key = arr[0].substring(0,8).getBytes();
        this.initilizationVector = arr[1].substring(0,8).getBytes();
        this.counterVal = arr[2].substring(0,8).getBytes();
        fkey.close();
    }
    public void writeLogFile(String msg) throws IOException {
        flogger.write(msg.getBytes());
    }
    public byte[] readPlaintext() throws IOException{
        return Files.readAllBytes(this.inputPath);
    }
    public ArrayList<byte[]> readCipherText() throws IOException{
        String string = Files.readString(this.inputPath);
        byte[] bytes = Base64.getDecoder().decode(string);
        ArrayList<byte[]> cipherBlocks = new ArrayList<byte[]>();
        byte[] block = new byte[8];
        int k=0;
        for (int i=0; i<bytes.length; i+=8){
            k=0;
            block = new byte[8];
            for (int j=i; j<i+8; j++){
                if (j == bytes.length) break;
                block[k] = bytes[j];
                System.out.print(block[k]+",");
//                if (block[k]>=65408) {
//                    System.out.println("girdi");block[k]-=65536;}


                k++;
            }
            cipherBlocks.add(block);
//            System.out.print('/');
//        }
//        System.out.println("encrypted dosyayı okurken:");
//        for (byte[] asd: cipherBlocks){
//            for (byte z: asd){
//                System.out.print(z+",");
//            }
//            System.out.print("/");
        }
//        System.out.println();
        return cipherBlocks;
    }
    public void writeOutputFile(ArrayList<byte[]> encryptedBlocks ) throws IOException{
        System.out.println("OUTPUT DOSYASINA YAZDIĞIM DEĞERLER");
        byte[] bytes = new byte[8*encryptedBlocks.size()];
        int i=0;
        for (int j=0; j<encryptedBlocks.size(); j++){
            byte[] block = encryptedBlocks.get(j);
            for (byte by: block){
                System.out.print(by+",");
                if (j == encryptedBlocks.size() -1 && by==0) break;
                bytes[i] =by;
                i++;
            }
        }
        System.out.println();
        bytes = Arrays.copyOfRange(bytes,0,i);
        System.out.println();
        for (byte b: bytes){
            System.out.print(b+",");
        }
        fout.write(Base64.getEncoder().encodeToString(bytes));
        }

    public void writeOutputFile(String decryptedPlaintext) throws IOException{
        System.out.println(decryptedPlaintext);
        fout.write(decryptedPlaintext);
    }
    public void writeDecrypted(ArrayList<byte[]> decryptedBlocks ) throws IOException{
        System.out.println("OUTPUT DOSYASINA YAZDIĞIM DEĞERLER");
        byte[] bytes = new byte[8*decryptedBlocks.size()];
        int i=0;
        for (int j=0; j<decryptedBlocks.size(); j++){
            byte[] block = decryptedBlocks.get(j);
            for (byte by: block){
                System.out.print(by+",");
                if (j == decryptedBlocks.size() -1 && by==0) break;
                bytes[i] =by;
                i++;
            }
        }
        System.out.println();
        bytes = Arrays.copyOfRange(bytes,0,i);
        System.out.println();
        for (byte b: bytes){
            System.out.print(b+",");
        }
        fout.write(new String(bytes));
    }
    //                for (byte x: b) System.out.print(x+",");
//                for (byte x: b) System.out.print((char)x);
//
//

    public void closeFiles() throws IOException{
        fout.close();
        flogger.close();
    }

    public byte[] getKey() {
        return key;
    }
    public String getKeyAsString() throws IOException{
        InputStream inputStream = new ByteArrayInputStream(key);
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    public byte[] getIV() {
        return initilizationVector;
    }
    public byte[] getCounterVal() {
        return counterVal;
    }
}

