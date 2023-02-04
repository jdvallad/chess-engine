public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess();
        game.move("e2e4");
        game.print();
        game.printPseudoLegalMoves(game.turn);
        game.printPseudoLegalMoves(game.turn^1);
        //game.perft(4);
    }
}
