import javax.crypto.CipherOutputStream;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        //this.key = Base64.getDecoder().decode(arr[0].substring(0,8));
        this.key = arr[0].substring(0,8).getBytes();
        this.initilizationVector = arr[1].substring(0,8).getBytes();
        this.counterVal = arr[2].substring(0,8).getBytes();
        fkey.close();
    }
    public void writeLogFile(String msg) throws IOException {
        flogger.write(msg.getBytes());
    }
    public byte[] readPlaintext() throws IOException{
        return Files.readAllBytes(inputPath);
    }
    public ArrayList<byte[]> readCipherText() throws IOException{
        String string = Files.readString(this.inputPath);
        char[] chars = string.toCharArray();
        int toAdd=   8 - (chars.length % 8);
        byte[] bytes = new byte[chars.length+toAdd];
        int x=0;
        for (x=0; x<chars.length; x++){
            bytes[x] = (byte) chars[x];
        }
        for (x=x; x<bytes.length; x++) bytes[x] =0;
        ArrayList<byte[]> cipherBlocks = new ArrayList<byte[]>();
        byte[] block = new byte[8];
        int k=0;
        for (int i=0; i<string.length(); i+=8){
            k=0;
            for (int j=i; j<i+8; j++){
                if (j == string.length()) break;
                block[k] = bytes[j];
                if (block[k]>=65408) block[k]-=65536;
                System.out.print(block[k]+",");

                k++;
            }
            System.out.print('/');
            cipherBlocks.add(block);
        }
        System.out.println();
        return cipherBlocks;
    }
    public void writeOutputFile(ArrayList<byte[]> encryptedBlocks ) throws IOException{
        for (byte[] block:encryptedBlocks) {
            StringBuilder s = new StringBuilder();
            for (byte b: block) s.append((char)b);
//            fout.write(new String(block, StandardCharsets.UTF_8));
            fout.write(s.toString());
        }
        }

    public void writeOutputFile(String decryptedPlaintext) throws IOException{
        System.out.println(decryptedPlaintext);
        fout.write(decryptedPlaintext);
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
    public String getIVAsString() throws IOException{
        InputStream inputStream = new ByteArrayInputStream(initilizationVector);
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    public byte[] getCounterVal() {
        return counterVal;
    }
    public String getCounterValAsString() throws IOException{
        InputStream inputStream = new ByteArrayInputStream(counterVal);
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
    public String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}

