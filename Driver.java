
public class Driver {

    public static void main(String[] args) throws Exception {
       // Chess game = new Chess("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        //game.move("h1g1");
       // game.move("d7d6");
       // game.move("e2b5");
       // game.print();
       // game.printLegalMoves();
      //  System.out.println(game.inCheck());
        Stockfish.findBug("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -", 4);
    }
}
