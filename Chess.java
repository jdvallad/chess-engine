import java.util.ArrayList;
import java.util.List;

public class Chess {
    public static final int QUEEN = 0;
    public static final int KNIGHT = 1;
    public static final int BISHOP = 2;
    public static final int ROOK = 3;
    public static final int KING = 4;
    public static final int PAWN = 5;
    public static final int EMPTY = 6;
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public static final int KINGSIDE = 0;
    public static final int QUEENSIDE = 1;
    public static final long A_FILE = 72340172838076673l;
    public static final long B_FILE = 144680345676153346l;
    public static final long C_FILE = 289360691352306692l;
    public static final long D_FILE = 578721382704613384l;
    public static final long E_FILE = 1157442765409226768l;
    public static final long F_FILE = 2314885530818453536l;
    public static final long G_FILE = 4629771061636907072l;
    public static final long H_FILE = -9187201950435737472l;
    public static final long RANK_1 = 255l;
    public static final long RANK_2 = 65280l;
    public static final long RANK_3 = 16711680l;
    public static final long RANK_4 = 4278190080l;
    public static final long RANK_5 = 1095216660480l;
    public static final long RANK_6 = 280375465082880l;
    public static final long RANK_7 = 71776119061217280l;
    public static final long RANK_8 = -72057594037927936l;
    public static final byte FLAG_EMPTY = 0;
    public static final byte FLAG_CASTLE = 1;
    public static final byte FLAG_EN_PASSANT = 2;
    public static final byte FLAG_PROMOTION = 3;
    public static final int[] PROMOTION_PIECES = { QUEEN, KNIGHT, BISHOP, ROOK };
    public static final byte[] FLAGS = new byte[] { FLAG_EMPTY, FLAG_CASTLE, FLAG_EN_PASSANT, FLAG_PROMOTION };
    public static final int[] PIECES = new int[] { QUEEN, KNIGHT, BISHOP, ROOK, KING, PAWN, EMPTY };
    public static final long[] FILES = new long[] { A_FILE, B_FILE, C_FILE, D_FILE, E_FILE, F_FILE, G_FILE, H_FILE };
    public static final long[] FILES_REVERSED = new long[] { H_FILE, G_FILE, F_FILE, E_FILE, D_FILE, C_FILE, B_FILE,
            A_FILE };
    public static final long[] RANKS = new long[] { RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8 };
    public static final long[] RANKS_REVERSED = new long[] { RANK_8, RANK_7, RANK_6, RANK_5, RANK_4, RANK_3, RANK_2,
            RANK_1 };
    public static final char[][] PIECE_CHARS = new char[][] { { '♛', '♞', '♝', '♜', '♚', '♟', ' ' },
            { '♕', '♘', '♗', '♖', '♔', '♙', '⚠' } }; // All empty squares will be white, ⚠ should never print
    public static final int[] COLORS = new int[] { WHITE, BLACK };
    public static final int[] SIDES = new int[] { KINGSIDE, QUEENSIDE };
    public static final long[][] DEFAULT_PIECEBOARDS = new long[][] {
            { 8l, 66l, 36l, 129l, 16l, 65280l, 281474976645120l },
            { 1152921504606846976l, 4755801206503243776l, 2594073385365405696l,
                    -9151314442816847872l, 576460752303423488l,
                    71776119061217280l, 0l }
    };
    // Object instance variables
    int turn;
    long[][] pieceBoards;
    long[] combinedBoards;
    boolean[][] castleRights;
    long enPassantSquare;
    int halfMoveCount;
    int fullMoveCount;
    List<Short> movesMade;
    List<Short> pseudoLegalMoves;
    List<Short> legalMoves;

    public Chess() {
        turn = WHITE;
        enPassantSquare = 0l;
        halfMoveCount = 0;
        fullMoveCount = 1;
        movesMade = new ArrayList<>();
        pseudoLegalMoves = new ArrayList<>();
        legalMoves = new ArrayList<>();
        pieceBoards = new long[COLORS.length][PIECES.length];
        combinedBoards = new long[COLORS.length];
        castleRights = new boolean[COLORS.length][SIDES.length];
        for (int color : COLORS) {
            for (int piece : PIECES) {
                pieceBoards[color][piece] = DEFAULT_PIECEBOARDS[color][piece];
                if (piece != EMPTY) {
                    combinedBoards[color] |= pieceBoards[color][piece];
                }
            }
            for (int side : SIDES) {
                castleRights[color][side] = true;
            }
        }
    }

    public void print() {
        boolean flag = false;
        System.out.print("\r\n╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻\r\n│");
        for (long rank : RANKS_REVERSED) {
            if (flag) {
                System.out.print("\r\n│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│\r\n│");
            }
            flag = true;
            foundPiece: for (long file : FILES) {
                long square = rank & file;
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if ((pieceBoards[color][piece] & square) == square) {
                            System.out.print(" " + PIECE_CHARS[color][piece] + " │");
                            continue foundPiece;
                        }
                    }
                }
            }
        }
        System.out.println("\r\n╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹");
    }

    public void makeMove(short move) {
        if (legalMoves.contains(move)) {
            return;
        }
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        byte promotion = getPromotion(move);
        byte flag = getFlag(move);
        int startColor = ((combinedBoards[BLACK] & start) == start) ? BLACK : WHITE;
        int endColor = ((combinedBoards[BLACK] & end) == end) ? BLACK : WHITE;
        if (flag == FLAG_PROMOTION) {
            remove(start, startColor, startPiece);
            add(start, startColor, promotion);
            move(start, end);
            enPassantSquare = 0l;
            halfMoveCount = -1;
        }
        if (flag == FLAG_EN_PASSANT) {
            long captureSquare = Chess.pushDown(end, end - start > 0 ? 1 : -1);
            int capturePiece = getPiece(captureSquare);
            int captureColor = ((combinedBoards[BLACK] & captureSquare) == captureSquare) ? BLACK : WHITE;
            remove(captureSquare, captureColor, capturePiece);
            move(start, end);
            enPassantSquare = 0l;
            halfMoveCount = -1;
        }
        if (flag == FLAG_CASTLE) {
            long mainStartSquare = start;
            long helperStartSquare = end;
            long rank = (startColor == BLACK) ? RANK_8 : RANK_1;
            long mainEndSquare = -1;
            long helperEndSquare = -1;
            if (getFileIndex(end) - getFileIndex(start) > 0) {// kingside castle if castling to the left
                mainEndSquare = rank & G_FILE;
                helperEndSquare = rank & F_FILE;
            } else {
                mainEndSquare = rank & C_FILE;
                helperEndSquare = rank & D_FILE;
            }
            move(mainStartSquare, mainEndSquare);
            move(helperStartSquare, helperEndSquare);
            enPassantSquare = 0l;
        }
        if (turn == BLACK) {
            fullMoveCount += 1;
        }
        halfMoveCount += 1;
        this.turn ^= 1;
        updateLegalMoves();
    }

    public void updateLegalMoves() {
        return;
    }

    public void move(long start, long end) {
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = ((combinedBoards[BLACK] & start) == start) ? BLACK : WHITE;
        int endColor = ((combinedBoards[BLACK] & end) == end) ? BLACK : WHITE;
        remove(start, startColor, startPiece);
        remove(end, endColor, endPiece);
        add(end, startColor, startPiece);
    }

    public void move(String start, String end) {
        move(create(start), create(end));
    }

    public void move(String move) {
        move(move.substring(0, 2), move.substring(2));
    }

    public void swap(long start, long end) {
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = ((combinedBoards[BLACK] & start) == start) ? BLACK : WHITE;
        int endColor = ((combinedBoards[BLACK] & end) == end) ? BLACK : WHITE;
        remove(start, startColor, startPiece);
        remove(end, endColor, endPiece);
        add(start, endColor, endPiece);
        add(end, startColor, startPiece);
    }

    public void swap(String start, String end) {
        swap(create(start), create(end));
    }

    public void add(long square, int color, int piece) {
        pieceBoards[color][piece] |= square;
        if (piece != EMPTY) {
            combinedBoards[color] |= square;
            pieceBoards[WHITE][EMPTY] &= ~square;
        }
    }

    public void remove(long square, int color, int piece) {
        pieceBoards[color][piece] &= ~square;
        if (piece != EMPTY) {
            combinedBoards[color] &= ~square;
            pieceBoards[WHITE][EMPTY] |= square;
        }
    }

    public void remove(long square) {
        int piece = getPiece(square);
        int color = ((combinedBoards[BLACK] & square) == square) ? BLACK : WHITE;
        remove(square, color, piece);
    }

    public int getPiece(long square) {
        for (int color : COLORS) {
            for (int piece : PIECES) {
                if ((pieceBoards[color][piece] & square) == square) {
                    return piece;
                }
            }
        }
        return -1; // This shouldn't occur
    }

    public short encodeMove(long start, long end, int promotion, int flag) {
        byte origin = (byte) Long.numberOfTrailingZeros(start);
        byte destination = (byte) Long.numberOfTrailingZeros(end);
        return (short) (((63 & origin) << 10) | ((63 & destination) << 4) | ((3 & promotion) << 2) | (3 & flag));
    }

    public long getStartingSquare(short move) {
        byte start = (byte) (63 & (move >>> 10));
        return 1l << start;
    }

    public long getEndingSquare(short move) {
        byte end = (byte) (63 & (move >>> 4));
        return 1l << end;
    }

    public byte getPromotion(short move) {
        return (byte) (3 & (move >>> 2));
    }

    public byte getFlag(short move) {
        return (byte) (3 & move);
    }

    // Long Board Methods
    public static int getRankIndex(long square) {
        int zeroes = Long.numberOfTrailingZeros(square);
        return (zeroes / 8);
    }

    public static int getFileIndex(long square) {
        int zeroes = Long.numberOfTrailingZeros(square);
        return (zeroes % 8);
    }

    public static void print(long input) {
        for (long i = 63; i >= 0; i--) {
            if (i % 8 == 7) {
                System.out.println();
            }
            long index = 8 * (i / 8) + (7 - (i % 8));
            System.out.print(" " + ((input >>> index) & 1l));
        }
        System.out.println();
        System.out.println();

    }

    public static long pushLeft(long input, int shift) {
        if (shift > 63) {
            return 0l;
        }
        if (shift < 0) {
            return pushRight(input, -shift);
        }
        return (input >>> shift);
    }

    public static long pushRight(long input, int shift) {
        if (shift > 63) {
            return 0l;
        }
        if (shift < 0) {
            return pushLeft(input, -shift);
        }
        return (input << shift);
    }

    public static long pushUp(long input, int shift) {
        return pushRight(input, 8 * shift);
    }

    public static long pushDown(long input, int shift) {
        return pushLeft(input, 8 * shift);
    }

    public static long push(long input, int rightShift, int upShift) {
        input = pushRight(input, rightShift);
        input = pushUp(input, upShift);
        return input;
    }

    public static long create(int x, int y) {
        return FILES[x] & RANKS[y];
    }

    public static long create(String square) {
        return FILES[square.charAt(0) - 'a'] & RANKS[square.charAt(1) - '1'];
    }

    public static long get(long input, int x, int y) {
        input = pushLeft(input, x);
        input = pushDown(input, y);
        return 1l & input;
    }

    public static long set(long input, int x, int y) {
        return input | create(x, y);
    }

    public static long unset(long input, int x, int y) {
        return input & (~create(x, y));
    }

    public static long toggle(long input, int x, int y) {
        return (get(input, x, y) == 1l) ? unset(input, x, y) : set(input, x, y);
    }
}