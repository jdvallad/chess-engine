public class Driver {

    public static void main(String[] args) throws Exception {
    Chess game = new Chess();
    game.move("a2a4");
    game.printLegalMoves();
}
}
