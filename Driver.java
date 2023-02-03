import java.util.HashMap;
import java.util.Map;

public class Driver {

    public static void main(String[] args) throws Exception {
        ChessNew game = new ChessNew();
        game.setFromFen("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1");
        game.print();
        short move = game.getMoveShort("a2a4");
        game.makeMove("a2a4");

        game.print();
      //  game.makeMove("b4b3");
        //game.printLegalMoves();
        //game.Perft(1, true);
    }
}
