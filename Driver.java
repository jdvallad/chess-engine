import java.util.HashMap;
import java.util.Map;

public class Driver {

    public static void main(String[] args) throws Exception {
        ChessNew game = new ChessNew();
        game.setFromFen("r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1");
        game.Perft(4, true);
    }
}
