
public class Driver {

    public static void main(String[] args) throws Exception {
        long board = ~0;
        board = Chess.e(board, 2);
        Chess.print(board);
    }
}
