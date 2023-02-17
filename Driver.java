import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Driver {

    public static void main(String[] args) throws Exception {
        FastChess game = new FastChess();
        List<String> moves = new ArrayList<>();
        while (!game.gameOver) {
            String move = getRandomSetElement(game.legalMoves);
            moves.add(move);
            game.move(move);
        }
        game.printEntireGame();
    }

    static <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }
}
