import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Stockfish {

    public static void terminal() throws Exception {
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
                writer.write(input + "\r\n");
            }
            writer.flush();
        }
        scan.close();
    }

    public static Map<String, Long> perft(String fen, int depth, String... moves) throws Exception {
        Map<String, Long> map = new TreeMap<>();
        String executablePath = "/mnt/c/Users/joshu/Documents/stockfish.exe";
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
            map.put(pair[0], Long.parseLong(pair[1]));
        }
        return map;
    }

    public static boolean findBug(String fen, int depth, String... moves) throws Exception {
        Chess game = new Chess(fen);
        for (String move : moves) {
            game.move(move);
        }
        Map<String, Long> myMap = game.perftMap(depth);
        Map<String, Long> trueMap = Stockfish.perft(fen, depth, moves);
        if (myMap.get("total").equals(trueMap.get("total"))) {
            System.out.println("Fen: " + fen);
            if (moves.length > 0) {
                System.out.print("Moves :");
                for (String move : moves) {
                    System.out.print(move + " ");
                }
                System.out.println();
            }
            System.out.println("Output matches.");
            System.out.println();
            return true;
        } else {
            List<String> moveList = new ArrayList<>();
            for (String move : moves) {
                moveList.add(move);
            }
            while (true) {
                HashSet<String> sharedKeys = new HashSet<>(myMap.keySet());
                sharedKeys.retainAll(trueMap.keySet());
                String moveThatShouldBeLegal = "";
                String moveThatShouldBeIllegal = "";
                String moveWithoutMatch = "";
                for (String key : myMap.keySet()) {
                    if (!trueMap.keySet().contains(key)) {
                        moveThatShouldBeIllegal = key;
                        break;
                    }
                }
                for (String key : trueMap.keySet()) {
                    if (!myMap.keySet().contains(key)) {
                        moveThatShouldBeLegal = key;
                        break;
                    }
                }
                for (String key : sharedKeys) {
                    if (!trueMap.get(key).equals(myMap.get(key)) && !key.equals("total")) {
                        moveWithoutMatch = key;
                        break;
                    }
                }
                if (moveThatShouldBeIllegal.length() > 0) {
                    System.out.println("Fen: " + fen);
                    if (moveList.size() > 0) {
                        System.out.print("Moves :");
                        for (String move : moveList) {
                            System.out.print(move + " ");
                        }
                        System.out.println();
                    }
                    System.out.println(moveThatShouldBeIllegal + " should be illegal in this position.");
                    game.print();
                    game.printLegalMoves();
                    return false;
                }
                if (moveThatShouldBeLegal.length() > 0) {
                    System.out.println("Fen: " + fen);
                    if (moveList.size() > 0) {
                        System.out.print("Moves :");
                        for (String move : moveList) {
                            System.out.print(move + " ");
                        }
                        System.out.println();
                    }
                    System.out.println(moveThatShouldBeLegal + " should be legal in this position.");
                    game.print();
                    game.printLegalMoves();
                    return false;
                }
                moveList.add(moveWithoutMatch);
                game.move(moveWithoutMatch);
                depth--;
                if (depth == 0) {
                    throw new Exception("Something went wrong.");
                }
                myMap = game.perftMap(depth);
                trueMap = Stockfish.perft(game.getFen(), depth, moveList.toArray(String[]::new));
            }
        }
    }
}
