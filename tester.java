public class tester {

    public static void main(String[] args) throws Exception {
        ChessNew game = new ChessNew();
        game.setFromFen("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
      Thread.sleep(1000);
        boolean[] what = game.castleRights[0];
        System.out.println("" + what[0] + "" + what[1]);
        game.makeMove("d2d4");
        game.makeMove("c7c5");
        game.makeMove("g2g4");
        short move = game.encodeMove(ChessNew.create("b2"), ChessNew.create("a1"), ChessNew.BISHOP, ChessNew.FLAG_PROMOTION);
        game.makeShallowMove(move);
        game.print();
    }
}