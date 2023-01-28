
public class Driver {

    public static void main(String[] args) throws Exception {
        Chess game = new Chess();
        game.print();
        game.move("e2","e4");
        game.print();
    }

}