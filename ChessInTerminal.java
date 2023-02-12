import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ChessInTerminal {
    public static void main(String[] args) throws Exception {
        boolean flip = false;
        boolean ascii = true;
        Chess game = new Chess();
        System.out.println("\033c");
        game.print(ascii, flip);
        game.printLegalMoves();
        Scanner input = new Scanner(System.in);
        String move = "";
        while (true) {
            move = input.next().toLowerCase();
            switch (move) {
                case "ascii":
                    ascii = true;
                    System.out.println("\033c");
                    game.print(ascii, flip);
                    game.printLegalMoves();
                    break;
                case "unicode":
                    ascii = false;
                    System.out.println("\033c");
                    game.print(ascii, flip);
                    game.printLegalMoves();
                    break;
                case "flip":
                    flip = !flip;
                    System.out.println("\033c");
                    game.print(ascii, flip);
                    game.printLegalMoves();
                    break;
                case "quit":
                    input.close();
                    return;
                case "exit":
                    input.close();
                    return;
                case "undo":
                    if (game.reversibleMoves.size() >= 1) {
                        game.undo();
                        System.out.println("\033c");
                        game.print(ascii, flip);
                        game.printLegalMoves();
                    }
                    break;
                case "random":
                    game.move(getRandomSetElement(game.legalMoves));
                    System.out.println("\033c");
                    game.print(ascii, flip);
                    game.printLegalMoves();
                    if (game.gameOver) {
                        System.out.println("Game over.");
                        input.close();
                        return;
                    }
                    break;
                default:
                    if (game.isLegalMove(move)) {
                        game.move(move);
                        System.out.println("\033c");
                        game.print(ascii, flip);
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
