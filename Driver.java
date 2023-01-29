
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess();
        game.print();
        game.move("f1f4");
        game.move("g1g4");
        game.print();
        long start = Chess.create("e1");
        long end = Chess.create("h1");
        byte promotion = Chess.QUEEN;
        byte flag = Chess.FLAG_CASTLE;
        short move = game.encodeMove(start, end, promotion, flag);
        game.makeMove(move);
        game.print();

    }
}
