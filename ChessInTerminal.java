import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ChessInTerminal {
    public static void main(String[] args) throws Exception {
        FastChess game = new FastChess();
        int perspective = FastChess.WHITE;
        System.out.println("\033c");
        game.printBoard(perspective);
        game.printLegalMoves();
        Scanner input = new Scanner(System.in);
        String move = "";
        while (true) {
            move = input.next().toLowerCase();
            switch (move) {
                case "flip":
                    perspective ^=1;
                    System.out.println("\033c");
                    game.printBoard(perspective);
                    game.printLegalMoves();
                    break;
                case "quit":
                    input.close();
                    return;
                case "exit":
                    input.close();
                    return;
                case "undo":
                        game.undo();
                        System.out.println("\033c");
                        game.printBoard(perspective);
                        game.printLegalMoves();
                    break;
                case "random":
                    game.move(getRandomSetElement(game.getLegalMoves()));
                    System.out.println("\033c");
                    game.printBoard(perspective);
                    game.printLegalMoves();
                    if (game.gameOver) {
                        System.out.println("Game over.");
                        input.close();
                        return;
                    }
                    break;
                default:
                    if (game.legalMoves.contains(move)) {
                        game.move(move);
                        System.out.println("\033c");
                        game.printBoard(perspective);
                        game.printLegalMoves();
                        if (game.gameOver) {
                            System.out.println("Game over.");
                            input.close();
                            return;
                        }
                    } else {
                        System.out.println("Not a legal move.");
                    }
                    break;
            }
        }
    }

    static <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }
}
