import java.util.HashMap;
import java.util.Map;

public class Driver {

    public static void main(String[] args) throws Exception {
        ChessNew game = new ChessNew();
        short move = 0;
        for (short temp : game.legalMoves) {
            if (game.getStringMove(temp).equals("a2a3")) {
                move = temp;
                break;
            }
        }
        game.makeMove(move);
        game.print();
        Map<Short, Long> wow = new HashMap<>();
        game.Perft(3, true, wow);
        for (short key : wow.keySet()) {
            System.out.println("" + game.getStringMove(key) + ": " + wow.get(key));
        }
    }
}
