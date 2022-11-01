import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class IOHandler {
    private byte[] key;
    private byte[] initilizationVector;
    private byte[] counterVal;
    private Path inputPath;
    private FileInputStream fkey;
    private FileWriter fout;
    private FileOutputStream flogger;
    public IOHandler(String inputFile, String outputFile, String keyFile){
        try {
            this.inputPath = Paths.get(inputFile);
            this.fout = new FileWriter(outputFile);
            this.flogger = new FileOutputStream("run.log", true);
            this.fkey = new FileInputStream(keyFile);
            this.readKeyFile();

        }catch(Exception e){System.out.println(e);}
    }
    //Method for reading key file.
    //We used first 8 characters of key, iv and nonce values to get an 64 bit meaningful block.
    private void readKeyFile() throws IOException{
        int i;
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
    //Method for reading plainText to get an utilizable arraylist for encrypting
    public byte[] readPlaintext() throws IOException{
        return Files.readAllBytes(this.inputPath);
    }
    //Method for reading cipherText to get an utilizable arraylist for decrypting
    public ArrayList<byte[]> readCipherText() throws IOException{
        String string = String.join("",Files.readAllLines(inputPath));
        byte[] bytes = Base64.getDecoder().decode(string);
        ArrayList<byte[]> cipherBlocks = new ArrayList<byte[]>();
        byte[] block;
        int k;
        for (int i=0; i<bytes.length; i+=8){
            k=0;
            block = new byte[8];
            for (int j=i; j<i+8; j++){
                if (j == bytes.length) break;
                block[k] = bytes[j];
                k++;
            }
            cipherBlocks.add(block);
        }
        return cipherBlocks;
    }
    public void writeEncrypted(ArrayList<byte[]> encryptedBlocks ) throws IOException{
        byte[] bytes = unpadBlockList(encryptedBlocks);
        fout.write(Base64.getEncoder().encodeToString(bytes));
    }
    public void writeDecrypted(ArrayList<byte[]> decryptedBlocks ) throws IOException{
        byte[] bytes = unpadBlockList(decryptedBlocks);
        fout.write(new String(bytes));
    }
    //helper method to clear null values.
    private byte[] unpadBlockList(ArrayList<byte[]> blocks){
            byte[] bytes = new byte[8*blocks.size()];
            int i=0;
            for (int j=0; j<blocks.size(); j++){
                byte[] block = blocks.get(j);
                for (byte by: block){
                    if (j == blocks.size() -1 && by==0) break;
                    bytes[i] =by;
                    i++;
                }
            }
            bytes = Arrays.copyOfRange(bytes,0,i);
        return bytes;
    }
    public void closeFiles() throws IOException{
        fout.close();
        flogger.close();
    }
    //getters
    public byte[] getKey() {
        return key;
    }

    public byte[] getIV() {
        return initilizationVector;
    }
    public byte[] getCounterVal() {
        return counterVal;
    }
}

