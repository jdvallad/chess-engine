
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess();
        String fen = game.getShredderFen();
        System.out.println(fen);
        for (long rank : Chess.RANKS) {
            for (long file : Chess.FILES) {
                game.remove(rank & file);
            }
        }
        game.print();
        game.setFromShredderFen(fen);
        game.print();
    }
}
