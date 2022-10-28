public class Main {
    public static void main(String[] args) {
        try {
            IOHandler IOHandler = new IOHandler("input.txt", "output.txt", "myLogger.log");
            byte[] data = IOHandler.readAllBytes();
            IOHandler.writeOutputFile(data);
            IOHandler.writeLogFile("inputfile.txt encrypted.txt enc DES CBC 64");
            IOHandler.closeFiles();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}