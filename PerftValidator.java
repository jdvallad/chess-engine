public class PerftValidator {
    public static String[] fens = new String[] {
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq -",
            "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -",
            "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - -",
            "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq -",
            "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8",
            "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10",
            "1k6/1b6/8/8/7R/8/8/4K2R b K - 0 1",
            "3k4/3p4/8/K1P4r/8/8/8/8 b - - 0 1",
            "8/8/4k3/8/2p5/8/B2P2K1/8 w - - 0 1",
            "8/8/1k6/2b5/2pP4/8/5K2/8 b - d3 0 1",
            "5k2/8/8/8/8/8/8/4K2R w K - 0 1",
            "3k4/8/8/8/8/8/8/R3K3 w Q - 0 1",
            "r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1",
            "r3k2r/8/3Q4/8/8/5q2/8/R3K2R b KQkq - 0 1",
            "2K2r2/4P3/8/8/8/8/8/3k4 w - - 0 1",
            "8/8/1P2K3/8/2n5/1q6/8/5k2 b - - 0 1",
            "4k3/1P6/8/8/8/8/K7/8 w - - 0 1",
            "8/P1k5/K7/8/8/8/8/8 w - - 0 1",
            "K1k5/8/P7/8/8/8/8/8 w - - 0 1",
            "8/k1P5/8/1K6/8/8/8/8 w - - 0 1",
            "8/8/2k5/5q2/5n2/8/5K2/8 b - - 0 1"
    };

    static int depth = 4;

    public static void main(String[] arges) throws Exception {
        for (int i = 0; i < fens.length; i++) {
            boolean result = Stockfish.findBug(fens[i], depth);
            if (!result) {
                break;
            }
        }
        return;
    }
}
