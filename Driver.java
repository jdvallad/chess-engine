
public class Driver {

    public static void main(String[] args) throws Exception {
        ChessNew game = new ChessNew();
        System.out.println(game.inCheck);
        game.printLegalMoves();
        short move = game.legalMoves.get(3);
        game.makeMove(move);
        move = game.legalMoves.get(3);
        game.makeMove(move);
        game.printLegalMoves();
        game.print();
    }
}
