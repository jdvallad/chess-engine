import java.util.HashMap;
import java.util.Map;

public class Driver {

    public static void main(String[] args) throws Exception {
        ChessNew game = new ChessNew();
        game.setFromFen("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        game.makeMove("a2a4");
        game.makeMove("b4a3");
        game.makeMove("e2b5");
        Map<Short, Long> wow = new HashMap<>();
        // game.Perft(1, true, wow);
        // for (short key : wow.keySet()) {
        // System.out.println("" + game.getStringMove(key) + ": " + wow.get(key));
        // }
        game.print();
        game.printLegalMoves();
    }
}
