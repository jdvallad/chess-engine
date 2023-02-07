public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess("r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq -");
        game.move("f3f5");
        game.print();
        game.perft(3,true);
    }
}
