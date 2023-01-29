
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess();
        game.move("e2e5");
        game.print();
        game.move("f7f5");
        game.print();
    }
}