
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq -");
        game.move("d2d4");
        game.print();
        game.printPseudoLegalMoves();
    }
}
