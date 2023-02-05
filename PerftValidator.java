public class PerftValidator {
    public static String[] fens = new String[] {
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -",
            "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -",
            "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -",
            "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq -",
            "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8",
            "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10"

    };
    public static int[] perft4Results = new int[] {
            197281,
            4085603,
            43238,
            422333,
            2103487,
            3894594
    };

    public static void main(String[] arges) throws Exception {
        Chess game = new Chess();
        for (int i = 0; i < fens.length; i++) {
            game.setFromFen(fens[i]);
            game.perft(4);
            System.out.println("Nodes expected: " + perft4Results[i]);
            System.out.println("Results above from fen: " + fens[i]);
            System.out.println();
        }
    }
}
