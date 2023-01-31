
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess();
        short move = game.encodeMove(Chess.create("e2"),Chess.create("e4"),Chess.QUEEN,Chess.FLAG_STANDARD);
        long temp = game.encodeReversibleMove(move);
        game.print(temp);
    }
}
