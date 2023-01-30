
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess();
        game.print();
        game.move("f1f4");
        game.move("g1g4");
        game.move("d1d4");
        game.move("c1c4");
        game.move("b1b4");
        game.print();
        long start = Chess.create("e1");
        long end = Chess.create("h1");
        byte promotion = Chess.QUEEN;
        byte flag = Chess.FLAG_CASTLE;
        short move = game.encodeMove(start, end, promotion, flag);
        game.makeMove(move);
        game.print();
        long temp = 63l;
        Chess.print(temp);
    }
}
