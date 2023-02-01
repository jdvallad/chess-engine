
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess("r3k2r/1b4bq/8/8/8/8/7B/R3K2R w KQkq - 0 1");
        game.print();
        long output = game.Perft(4,false,null);
        System.out.println(output);
    }
}
