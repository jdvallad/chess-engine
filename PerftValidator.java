import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PerftValidator {
    public static String[] fens = {
            "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq -",
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -",
            "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -",
            "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8",
            "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10",
            "1k6/1b6/8/8/7R/8/8/4K2R b K -",
            "3k4/3p4/8/K1P4r/8/8/8/8 b - -",
            "8/8/4k3/8/2p5/8/B2P2K1/8 w - -",
            "8/8/1k6/2b5/2pP4/8/5K2/8 b - d3",
            "5k2/8/8/8/8/8/8/4K2R w K -",
            "3k4/8/8/8/8/8/8/R3K3 w Q -",
            "r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq -",
            "r3k2r/8/3Q4/8/8/5q2/8/R3K2R b KQkq -",
            "2K2r2/4P3/8/8/8/8/8/3k4 w - -",
            "8/8/1P2K3/8/2n5/1q6/8/5k2 b - -",
            "4k3/1P6/8/8/8/8/K7/8 w - -",
            "8/P1k5/K7/8/8/8/8/8 w - -",
            "K1k5/8/P7/8/8/8/8/8 w - -",
            "8/k1P5/8/1K6/8/8/8/8 w - -",
            "8/8/2k5/5q2/5n2/8/5K2/8 b - -",
            "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -"
    };

    static int depth = 4;

    public static void main(String[] args) throws Exception {
        Chess game = new FastChess();
        Chess gold = new GoldChess();
        long startTime = System.nanoTime();
        for (int i = 0; i < fens.length; i++) {
            if (bugExists(game, gold, fens[i], depth)) {
                return;
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("This took " + formatNanoIntoSeconds(duration) + " seconds.");
        return;
    }

    public static String formatNanoIntoSeconds(long duration) {
        StringBuilder seconds = new StringBuilder("000000000000" + duration);
        seconds.insert(seconds.length() - 9, ".");
        while (seconds.charAt(0) == '0') {
            seconds.deleteCharAt(0);
        }
        return seconds.toString();
    }

    public static boolean bugExists(Chess game, Chess gold, String fen, int depth, String... moves) throws Exception {
        game.setFromFen(fen);
        gold.setFromFen(fen);
        game.move(moves);
        gold.move(moves);
        Map<String, Integer> myMap = game.perftMap(depth);
        Map<String, Integer> trueMap = gold.perftMap(depth);
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
            return false;
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
                    game.printBoard(FastChess.WHITE);

                    game.printLegalMoves();
                    return true;
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
                    game.printBoard(FastChess.WHITE);
                    game.printLegalMoves();
                    return true;
                }
                if (moveWithoutMatch.length() == 0) {
                    throw new Exception("Something went wrong.");
                }
                moveList.add(moveWithoutMatch);
                game.move(moveWithoutMatch);
                gold.move(moveWithoutMatch);
                depth--;
                if (depth == 0) {
                    throw new Exception("Something went wrong.");
                }
                myMap = game.perftMap(depth);
                trueMap = gold.perftMap(depth);
            }
        }
    }
}