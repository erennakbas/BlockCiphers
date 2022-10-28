import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOHandler {
    private Path inputPath;
    private FileOutputStream fout;
    private FileOutputStream flogger;
    public IOHandler(String inputFile, String outputFile, String logFile){
        try {
            this.fout = new FileOutputStream(outputFile);
            this.flogger = new FileOutputStream(logFile);
            this.inputPath = Paths.get(inputFile);
        }catch(Exception e){System.out.println(e);}
    }
    public void writeLogFile(String msg) throws IOException {
        flogger.write(msg.getBytes());
    }
    public byte[] readAllBytes() throws IOException{
        return Files.readAllBytes(this.inputPath);
    }
    public void writeOutputFile(byte[] data) throws IOException{
        fout.write(data);
    }
    public void closeFiles() throws IOException{
        fout.close();
        flogger.close();
    }
}

