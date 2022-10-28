import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileHandler {
    FileInputStream fin;
    FileOutputStream fout;
    public FileHandler(String inputFile, String outputFile){
        try {
            fin = new FileInputStream(inputFile);
            fout = new FileOutputStream(outputFile);

        }catch(Exception e){System.out.println(e);}
    }
    public void readByLine(){
        try{
            int i=0;
            while((i=fin.read())!=-1){
                System.out.print((char)i);
            }
            fin.close();
        }catch(Exception e){System.out.println(e);}
    }
    public void writeLine(){

    }
}

