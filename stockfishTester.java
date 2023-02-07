import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Scanner;

public class stockfishTester {

    public static void main(String[] args) throws IOException, InterruptedException {
        String executablePath = "/mnt/c/Users/joshu/Documents/stockfish.exe";
        ProcessBuilder builder = new ProcessBuilder(executablePath);
        builder.redirectOutput(Redirect.INHERIT);
        Process process = builder.start();
        Scanner scan = new Scanner(System.in);
        OutputStream stdin = process.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        String input = "";
        while (scan.hasNext()) {
           input = scan.nextLine();
            if (input.trim().equals("quit")) {
                writer.write("quit\r\n");
                break;
            } else {
                writer.write(input+"\r\n");
            }
            writer.flush();
        }
        scan.close();
    }
}
