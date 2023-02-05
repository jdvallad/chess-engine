public class PerftTimer {
    static String startpos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    static String densepos = "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1";

    // current time to beat is 17.026382704 seconds for startpos and depth 4.
    public static void main(String[] arges) throws Exception {
        String testFen = startpos;
        int testDepth = 4;

        System.out.println("Timing with:");
        System.out.println("Fen: " + testFen);
        System.out.println("Depth: " + testDepth);
        // Lets time the method!
        long startTime = System.nanoTime();
        Chess game = new Chess();
        game.setFromFen(testFen);
        game.perft(testDepth, false);
        long endTime = System.nanoTime();
        // Lets print the duration to console!

        long duration = (endTime - startTime);
        System.out.println("This took " + formatNanoIntoSeconds(duration) + " seconds.");
    }

    public static String formatNanoIntoSeconds(long duration) {
        StringBuilder seconds = new StringBuilder("000000000000" + duration);
        seconds.insert(seconds.length() - 9, ".");
        while (seconds.charAt(0) == '0') {
            seconds.deleteCharAt(0);
        }
        return seconds.toString();
    }
}