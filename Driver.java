
public class Driver {

    public static void main(String[] args) throws Exception {
        ChessNew game = new ChessNew();
        game.setFromFen("r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1");
        game.printLegalMoves();
        game.print();
    }
}
