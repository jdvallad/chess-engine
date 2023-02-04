import java.util.Scanner;

public class ChessInTerminal {
    public static void main(String[] args) throws Exception {
        boolean flip = false;
        Chess game = new Chess();
        System.out.println("\033c");
        game.print(flip);
        game.printLegalMoves();
        Scanner input = new Scanner(System.in);
        String move = "";
        while (true) {
            move = input.next().toLowerCase();
            switch (move) {
                case "flip":
                    flip = !flip;
                    System.out.println("\033c");
                    game.print(flip);
                    game.printLegalMoves();
                    break;
                case "quit":
                    input.close();
                    return;
                case "exit":
                    input.close();
                    return;
                case "undo":
                    if (game.getLength() >= 1) {
                        game.undo();
                        System.out.println("\033c");
                        game.print(flip);
                        game.printLegalMoves();
                    }
                    break;
                case "random":
                    int random = (int) (Math.random() * (double) game.legalMoves.size());
                    game.move(game.legalMoves.get(random));
                    System.out.println("\033c");
                    game.print(flip);
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
                        game.print(flip);
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
}
