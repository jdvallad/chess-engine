
public class Chess {
    public static final int KING = 0;
    public static final int QUEEN = 1;
    public static final int KNIGHT = 2;
    public static final int BISHOP = 3;
    public static final int ROOK = 4;
    public static final int PAWN = 5;
    public static final int[] pieces = new int[] { KING, QUEEN, KNIGHT, BISHOP, ROOK, PAWN };
    public static final char[] pieceStrings = new char[] { 'K', 'Q', 'N', 'B', 'R', 'P' };
    boolean turn; // true = white's turn
    long[] whitePieces;
    long[] blackPieces;

    public Chess() {
        this.turn = true;
        this.whitePieces = new long[pieces.length]; // number of unique piece types
        this.blackPieces = new long[pieces.length];
        for (int piece : pieces) {
            this.whitePieces[piece] = 0l;
            this.blackPieces[piece] = 0l;
        }
    }

    public void print() {
        String output =  new String(new char[64]);
        for (int piece : pieces) {

        }
    }

}