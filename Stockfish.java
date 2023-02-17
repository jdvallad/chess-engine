import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Stockfish {

    private static String executablePath = "/home/joshua/Repositories/chess-engine/stockfish.exe";

    public static void terminal() throws Exception {
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
                writer.write(input + "\r\n");
            }
            writer.flush();
        }
        scan.close();
    }

    public static int perft(String fen, int depth, boolean verbose, String... moves) throws Exception {
        Map<String, Integer> map = perftMap(fen, depth, moves);
        if (verbose) {
            for (String key : map.keySet()) {
                System.out.println(key + ": " + map.get(key));
            }
        }
        return map.get("total");
    }

    public static Map<String, Integer> perftMap(String fen, int depth, String... moves) throws Exception {
        Map<String, Integer> map = new TreeMap<>();
        ProcessBuilder builder = new ProcessBuilder(executablePath);
        Process process = builder.start();
        OutputStream stdin = process.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
        String input = "position fen " + fen;
        if (moves.length > 0) {
            input += " moves";
            for (String move : moves) {
                input += " " + move;
            }
        }
        input += "\r\n";
        writer.write(input);
        writer.write("go perft " + depth);
        writer.flush();
        writer.close();
        String result = new String(process.getInputStream().readAllBytes());
        String[] split = result.split("\r\n");
        for (int i = 1; i < split.length; i++) {
            if (split[i].equals("")) {
                continue;
            }
            String[] pair = split[i].split(": ");
            if (pair[0].equals("Nodes searched")) {
                pair[0] = "total";
            }
            map.put(pair[0], Integer.parseInt(pair[1]));
        }
        return map;
    }

}
