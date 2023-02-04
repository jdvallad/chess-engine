import java.util.Scanner;

public class Driver {

    public static void main(String[] args) throws Exception {
        boolean ascii = false;
        Chess game = new Chess();
        game.print(ascii);
        Scanner input = new Scanner(System.in);
        String move = input.next().toLowerCase();
        while (!move.equals("quit") && !move.equals("exit")) {
            switch(move){
                default:
            }
            if (game.move(move)) {
                int numLegalMoves = game.legalMoves.size();
                if (game.gameOver) {
                    System.out.println("\033c");
                    game.print(ascii);
                    System.out.println("Game over.");
                    input.close();
                    return;
                }
                int random = (int) (Math.random() * (double) numLegalMoves);
                game.move(game.legalMoves.get(random));
                if (game.gameOver) {
                    System.out.println("\033c");
                    game.print(ascii);
                    System.out.println("Game over.");
                    input.close();
                    return;
                }
                game.print(ascii);
            }
            move = input.next().toLowerCase();
        }
        input.close();
        return;
    }
}
