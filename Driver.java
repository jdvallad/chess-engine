
public class Driver {

    public static void main(String[] args) throws Exception {
        long board = ~0;
        board = Chess.compass(board, -2,-1);
        board = Chess.compass(board,2,1);
       Chess.print(board);
    }
}
