import java.util.ArrayList;
import java.util.Collections;
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
    public static final int QUEENSIDE = 0;
    public static final int KINGSIDE = 1;
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
    public static final byte FLAG_STANDARD = 0;
    public static final byte FLAG_CASTLE = 1;
    public static final byte FLAG_EN_PASSANT = 2;
    public static final byte FLAG_PROMOTION = 3;
    public static final int N_OFFSET = 1;
    public static final int S_OFFSET = 129;
    public static final int E_OFFSET = 4;
    public static final int W_OFFSET = 260;
    public static final int NE_OFFSET = 5;
    public static final int NW_OFFSET = 261;
    public static final int SE_OFFSET = 133;
    public static final int SW_OFFSET = 389;
    public static final int NNE_OFFSET = 6;
    public static final int NNW_OFFSET = 262;
    public static final int NEE_OFFSET = 9;
    public static final int NWW_OFFSET = 265;
    public static final int SEE_OFFSET = 137;
    public static final int SWW_OFFSET = 393;
    public static final int SSW_OFFSET = 390;
    public static final int SSE_OFFSET = 134;
    public static final int[] KNIGHT_OFFSETS = new int[] { NEE_OFFSET, NNE_OFFSET, NNW_OFFSET, NWW_OFFSET, SWW_OFFSET,
            SSW_OFFSET, SSE_OFFSET, SEE_OFFSET };
    public static final int[] ROOK_OFFSETS = new int[] { N_OFFSET, S_OFFSET, E_OFFSET, W_OFFSET };
    public static final int[] BISHOP_OFFSETS = new int[] { NE_OFFSET, NW_OFFSET, SE_OFFSET, SW_OFFSET };
    public static final int[] PROMOTION_PIECES = { QUEEN, KNIGHT, BISHOP, ROOK };
    public static final byte[] FLAGS = new byte[] { FLAG_STANDARD, FLAG_CASTLE, FLAG_EN_PASSANT, FLAG_PROMOTION };
    public static final int[] PIECES = new int[] { QUEEN, KNIGHT, BISHOP, ROOK, KING, PAWN, EMPTY };
    public static final long[] FILES = new long[] { A_FILE, B_FILE, C_FILE, D_FILE, E_FILE, F_FILE, G_FILE, H_FILE };
    public static final long[] FILES_REVERSED = new long[] { H_FILE, G_FILE, F_FILE, E_FILE, D_FILE, C_FILE, B_FILE,
            A_FILE };
    public static final long[] RANKS = new long[] { RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8 };
    public static final long[] RANKS_REVERSED = new long[] { RANK_8, RANK_7, RANK_6, RANK_5, RANK_4, RANK_3, RANK_2,
            RANK_1 };
    public static final char[][] PIECE_CHARS_UNICODE = new char[][] { { '♛', '♞', '♝', '♜', '♚', '♟', ' ' },
            { '♕', '♘', '♗', '♖', '♔', '♙', '⚠' } }; // All empty squares will be white, ⚠ should never print
    public static final char[][] PIECE_CHARS_ASCII = new char[][] { { 'Q', 'N', 'B', 'R', 'K', 'P', ' ' },
            { 'q', 'n', 'b', 'r', 'k', 'p', ' ' } }; // All empty squares will be white, ⚠ should never print
    public static final int[] COLORS = new int[] { WHITE, BLACK };
    public static final int[] COLORS_REVERSED = new int[] { BLACK, WHITE };
    public static final int[] SIDES = new int[] { QUEENSIDE, KINGSIDE };
    public static final int[] SIDES_REVERSED = new int[] { KINGSIDE, QUEENSIDE };
    public static final long[][] DEFAULT_PIECEBOARDS = new long[][] {
            { 8l, 66l, 36l, 129l, 16l, 65280l, 281474976645120l },
            { 576460752303423488l, 4755801206503243776l, 2594073385365405696l,
                    -9151314442816847872l, 1152921504606846976l,
                    71776119061217280l, 0l }
    };
    public static final long[] ROOK_STARTING_FILES = new long[] { A_FILE, H_FILE };
    // Object instance variables
    int turn;
    long[][] pieceBoards;
    long[] combinedBoards;
    boolean[][] castleRights;
    long enPassantSquare;
    int halfMoveCount;
    int fullMoveCount;
    List<Short> legalMoves;
    List<Long> reversibleMoves;
    List<Short>[] pseudoLegalMoves;
    List<String> shortenedFenList;
    boolean inCheck;
    boolean gameOver;

    @SuppressWarnings("unchecked")

    public Chess() throws Exception {
        turn = WHITE;
        inCheck = false;
        gameOver = false;
        enPassantSquare = 0l;
        halfMoveCount = 0;
        fullMoveCount = 1;
        reversibleMoves = new ArrayList<>();
        legalMoves = new ArrayList<>();
        shortenedFenList = new ArrayList<>();
        pseudoLegalMoves = (List<Short>[]) new List[COLORS.length];
        pieceBoards = new long[COLORS.length][PIECES.length];
        combinedBoards = new long[COLORS.length];
        castleRights = new boolean[COLORS.length][SIDES.length];
        for (int color : COLORS) {
            pseudoLegalMoves[color] = new ArrayList<>();
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
        updateLegalMoves();
    }

    public void print(boolean ascii, boolean flipped) {
        if (ascii) {
            printSimple(flipped);
        } else {
            print(flipped);
        }
    }

    public void print(){
        print(false);
    }
    
    public void print(boolean flipped) {
        boolean flag = false;
        System.out.print("                                ");
        if (!flipped) {
            System.out.println("  a   b   c   d   e   f   g   h");
        } else {
            System.out.println("  h   g   f   e   d   c   b   a");

        }
        System.out.print("                                ");
        System.out.println("╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻⎯⎯⎯╻");
        System.out.print("                              ");
        if (!flipped) {
            System.out.print("8 ");
        } else {
            System.out.print("1 ");

        }
        System.out.print("│");
        for (long rank : (flipped ? RANKS : RANKS_REVERSED)) {
            if (flag) {
                System.out.print("\r\n                                ");
                System.out.println("│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│⎯⎯⎯│");
                System.out.print("                              ");
                System.out.print("" + (1 + getRankIndex(rank & A_FILE)) + " ");
                System.out.print("│");
            }
            flag = true;
            foundPiece: for (long file : (flipped ? FILES_REVERSED : FILES)) {
                long square = rank & file;
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if ((pieceBoards[color][piece] & square) == square) {
                            System.out.print(" " + PIECE_CHARS_UNICODE[color][piece] + " │");
                            if (file == (flipped ? A_FILE : H_FILE)) {
                                System.out.print(" " + (1 + getRankIndex(rank & file)));
                            }
                            continue foundPiece;
                        }
                    }
                }
            }
        }
        System.out.print("\r\n                                ");
        System.out.println("╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹⎯⎯⎯╹");
        System.out.print("                                ");
        System.out.println("  a   b   c   d   e   f   g   h\r\n");
    }

    public String getMoveString(short move) {
        long startingSquare = getStartingSquare(move);
        long endingSquare = getEndingSquare(move);
        int flag = getFlag(move);
        int piece = getPromotion(move);
        char startingFile = (char) ('a' + getFileIndex(startingSquare));
        char startingRank = (char) ('1' + getRankIndex(startingSquare));
        char endingFile = (char) ('a' + getFileIndex(endingSquare));
        char endingRank = (char) ('1' + getRankIndex(endingSquare));
        String output = "" + startingFile + startingRank + endingFile + endingRank;
        if (flag == FLAG_PROMOTION) {
            output += PIECE_CHARS_ASCII[BLACK][piece];
        }
        return output;
    }

    public void printLegalMoves() {
        System.out.print("[");
        boolean flag = false;
        for (short move : legalMoves) {
            if (flag) {
                System.out.print(", ");
            }
            flag = true;
            System.out.print(getMoveString(move) + "");
        }

        System.out.println("]");
    }

    public void printPseudoLegalMoves(int turn) {
        System.out.print("[");
        boolean flag = false;
        for (short move : pseudoLegalMoves[turn]) {
            if (flag) {
                System.out.print(", ");
            }
            flag = true;
            long startingSquare = getStartingSquare(move);
            long endingSquare = getEndingSquare(move);
            char startingFile = (char) ('a' + getFileIndex(startingSquare));
            char startingRank = (char) ('1' + getRankIndex(startingSquare));
            char endingFile = (char) ('a' + getFileIndex(endingSquare));
            char endingRank = (char) ('1' + getRankIndex(endingSquare));
            System.out.print("" + startingFile + startingRank + endingFile + endingRank);
        }
        System.out.println("]");
    }

    public boolean isLegalMove(String move) {
        for (short temp : legalMoves) {
            if (getMoveString(temp).equals(move)) {
                return true;
            }
        }
        return false;
    }

    public void printSimple(boolean flipped) {
        boolean flag = false;
        System.out.print("                                ");
        if (!flipped) {
            System.out.println("  a   b   c   d   e   f   g   h");
        } else {
            System.out.println("  h   g   f   e   d   c   b   a");

        }
        System.out.print("                                ");
        System.out.println("|---|---|---|---|---|---|---|---|");
        System.out.print("                              ");
        if (!flipped) {
            System.out.print("8 ");
        } else {
            System.out.print("1 ");

        }
        System.out.print("│");
        for (long rank : (flipped ? RANKS : RANKS_REVERSED)) {
            if (flag) {
                System.out.print("\r\n                                ");
                System.out.println("|---|---|---|---|---|---|---|---|");
                System.out.print("                              ");
                System.out.print("" + (1 + getRankIndex(rank & A_FILE)) + " ");
                System.out.print("|");
            }
            flag = true;
            foundPiece: for (long file : (flipped ? FILES_REVERSED : FILES)) {
                long square = rank & file;
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if ((pieceBoards[color][piece] & square) == square) {
                            System.out.print(" " + PIECE_CHARS_ASCII[color][piece] + " |");
                            if (file == (flipped ? A_FILE : H_FILE)) {
                                System.out.print(" " + (1 + getRankIndex(rank & file)));
                            }
                            continue foundPiece;
                        }
                    }
                }
            }
        }
        System.out.print("\r\n                                ");
        System.out.println("|---|---|---|---|---|---|---|---|");
        System.out.print("                                ");
        System.out.println("  a   b   c   d   e   f   g   h\r\n");
    }

    public void printSimple() {
        boolean flag = false;
        System.out.print("\r\n|---|---|---|---|---|---|---|---|\r\n|");
        for (long rank : RANKS_REVERSED) {
            if (flag) {
                System.out.print("\r\n|---|---|---|---|---|---|---|---|\r\n|");
            }
            flag = true;
            foundPiece: for (long file : FILES) {
                long square = rank & file;
                for (int color : COLORS) {
                    for (int piece : PIECES) {
                        if ((pieceBoards[color][piece] & square) == square) {
                            System.out.print(" " + PIECE_CHARS_ASCII[color][piece] + " |");
                            if (file == H_FILE) {
                                System.out.print(" " + (1 + getRankIndex(rank & file)));
                            }
                            continue foundPiece;
                        }
                    }
                }
            }
        }
        System.out.println("\r\n|---|---|---|---|---|---|---|---|\r\n");
        System.out.println("  a   b   c   d   e   f   g   h");
    }

    public boolean move(String moveString) throws Exception {
        short move = 0;
        for (short temp : legalMoves) {
            if (getMoveString(temp).equals(moveString)) {
                move = temp;
                break;
            }
        }
        if (move == 0) {
            System.out.println("Not a valid move.");
            return false;
        } else {
            makeMove(move);
            return true;
        }
    }

    public boolean move(short moveShort) throws Exception {
        if (legalMoves.contains(moveShort)) {
            makeMove(moveShort);
            return true;
        } else {
            System.out.println("Not a valid move.");
            return false;
        }
    }

    public void makeMove(short move) throws Exception {
        if (!legalMoves.contains(move)) {
            throw new Exception(getMoveString(move) + " is not a valid move!");
        }
        makeShallowMove(move);
        updateLegalMoves();
    }

    public short getMoveShort(String moveString) {
        short move = 0;
        for (short temp : legalMoves) {
            if (getMoveString(temp).equals(moveString)) {
                move = temp;
                break;
            }
        }
        return move;
    }

    public void makeMove(String moveString) throws Exception {
        short move = 0;
        for (short temp : legalMoves) {
            if (getMoveString(temp).equals(moveString)) {
                move = temp;
                break;
            }
        }
        if (move == 0) {
            throw new Exception("Move not found");
        } else {
            makeMove(move);
        }
    }

    public void makeShallowMove(short move) throws Exception {
        if (!pseudoLegalMoves[turn].contains(move)) {
            throw new Exception();
        }
        reversibleMoves.add(encodeReversibleMove(move));
        enPassantSquare = 0;
        switch (getFlag(move)) {
            case FLAG_PROMOTION:
                makePromotionMove(move);
                break;
            case FLAG_CASTLE:
                makeCastleMove(move);
                break;
            case FLAG_EN_PASSANT:
                makeEnPassantMove(move);
                break;
            case FLAG_STANDARD:
                makeStandardMove(move);
                break;
            default:
                makeStandardMove(move);
                break;
        }
        if (turn == BLACK) {
            fullMoveCount += 1;
        }
        halfMoveCount += 1;
        this.turn ^= 1;
        shortenedFenList.add(getShortenedFen());
    }

    public long encodeReversibleMove(short move) {
        // Info needed to encode reversible move:
        // starting and ending square, starting and ending piece,
        // starting and ending piece color, enPassantSquare
        // castleRights, move flag, and half move count.
        // starting piece and starting color can be inferred from board state
        // so they do not need to be encoded. But they will just for ease.

        long startingSquareLong = getStartingSquare(move);
        int startingSquareInt = Long.numberOfTrailingZeros(startingSquareLong); // 6 bits
        int startingPiece = getPiece(startingSquareLong); // 3 bits
        boolean isInCheck = inCheck; // 1 bit
        long endingSquareLong = getEndingSquare(move);
        int endingSquareInt = Long.numberOfTrailingZeros(endingSquareLong); // 6 bits
        int endingPiece = getPiece(endingSquareLong); // 3 bits
        int endingColor = getColor(endingSquareLong); // 1 bit
        int enPassantSquareOffset = Long.numberOfTrailingZeros(enPassantSquare); // 6 bits
        int flag = getFlag(move); // (2 bits)
        // halfMoveCount (32 bits)
        // castleRights (4 bits)
        // 64 bits, a long! (niceee)
        if (flag == FLAG_EN_PASSANT) {
            long captureSquare = RANKS[getRankIndex(startingSquareLong)] & FILES[getFileIndex(endingSquareLong)];
            endingPiece = getPiece(captureSquare);
            endingColor = getColor(captureSquare);
        }
        long output = 0;
        long push = pushRight(1l, 6 - 1);
        while (push != 0) {
            output |= push & startingSquareInt;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 3);
        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            output |= push & startingPiece;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 1);
        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            output |= push & (isInCheck ? 1 : 0);
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 6);
        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            output |= push & endingSquareInt;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 3);
        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            output |= push & endingPiece;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 1);
        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            output |= push & endingColor;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 6);
        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            output |= push & enPassantSquareOffset;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 2);
        push = pushRight(1l, 2 - 1);
        while (push != 0) {
            output |= push & flag;
            push = pushLeft(push, 1);
        }

        output = pushRight(output, 32);
        push = pushRight(1l, 32 - 1);
        while (push != 0) {
            output |= push & halfMoveCount;
            push = pushLeft(push, 1);
        }

        for (int color : COLORS) {
            for (int side : SIDES) {
                output = pushRight(output, 1);
                push = pushRight(1l, 1 - 1);
                while (push != 0) {
                    output |= push & (castleRights[color][side] ? 1l : 0l);
                    push = pushLeft(push, 1);
                }
            }
        }

        return output;
    }

    public String getMoveStringReversible(long input) {
        int endingSquareOffset = 0;
        int startingSquareOffset = 0;
        long endingSquare = 0l;
        long startingSquare = 0l;
        long push = 0;

        input = pushLeft(input, 48);
        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            endingSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        endingSquare = pushRight(1l, endingSquareOffset);
        input = pushLeft(input, 10);
        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            startingSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        startingSquare = pushRight(1l, startingSquareOffset);
        char startingFile = (char) ('a' + getFileIndex(startingSquare));
        char startingRank = (char) ('1' + getRankIndex(startingSquare));
        char endingFile = (char) ('a' + getFileIndex(endingSquare));
        char endingRank = (char) ('1' + getRankIndex(endingSquare));
        return "" + startingFile + startingRank + endingFile + endingRank;
    }

    public void printPastMoves() {
        System.out.print("[");
        for (long reversibleMove : reversibleMoves) {
            System.out.print(getMoveStringReversible(reversibleMove) + ",");
        }
        System.out.println("]");

    }

    public void simpleUndoReversibleMove() throws InterruptedException {
        // castleRights (4 bits)
        halfMoveCount = 0; // (32 bits)
        int flag = 0; // (2 bits)
        int enPassantSquareOffset = 0; // 6 bits
        int endingColor = 0; // 1 bit
        int endingPiece = 0; // 3 bits
        int endingSquareOffset = 0; // 6 bits
        boolean isInCheck = false; // 1 bit
        int startingPiece = 0; // 3 bits
        int startingSquareOffset = 0; // 6 bits
        long endingSquare = 0l;
        long startingSquare = 0l;
        long input = reversibleMoves.remove(reversibleMoves.size() - 1);
        long push = 0;
        for (int color : COLORS_REVERSED) {
            for (int side : SIDES_REVERSED) {
                push = pushRight(1l, 1 - 1);
                while (push != 0) {
                    castleRights[color][side] = (push & input) != 0;
                    push = pushLeft(push, 1);
                }
                input = pushLeft(input, 1);
            }
        }

        push = pushRight(1l, 32 - 1);
        while (push != 0) {
            halfMoveCount |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 32);

        push = pushRight(1l, 2 - 1);
        while (push != 0) {
            flag |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 2);

        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            enPassantSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        enPassantSquare = pushRight(1l, enPassantSquareOffset);
        input = pushLeft(input, 6);

        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            endingColor |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 1);

        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            endingPiece |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 3);

        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            endingSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        endingSquare = pushRight(1l, endingSquareOffset);
        input = pushLeft(input, 6);

        push = pushRight(1l, 1 - 1);
        while (push != 0) {
            isInCheck = (push & input) == 1;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 1);

        push = pushRight(1l, 3 - 1);
        while (push != 0) {
            startingPiece |= push & input;
            push = pushLeft(push, 1);
        }
        input = pushLeft(input, 3);

        push = pushRight(1l, 6 - 1);
        while (push != 0) {
            startingSquareOffset |= push & input;
            push = pushLeft(push, 1);
        }
        startingSquare = pushRight(1l, startingSquareOffset);
        input = pushLeft(input, 6);
        int startingColor = turn ^ 1;
        this.inCheck = isInCheck;
        switch (flag) {
            case FLAG_CASTLE:
                long back = getBackRank(startingColor);
                if ((ROOK_STARTING_FILES[QUEENSIDE] & back) == endingSquare) {
                    swap(back & C_FILE, startingSquare);
                    swap(back & D_FILE, endingSquare);
                } else {
                    swap(back & G_FILE, startingSquare);
                    swap(back & F_FILE, endingSquare);
                }
                break;
            case FLAG_PROMOTION:
                replace(startingSquare, startingColor, startingPiece);
                replace(endingSquare, endingColor, endingPiece);
                break;
            case FLAG_EN_PASSANT:
                long addPawnSquare = RANKS[getRankIndex(startingSquare)] & FILES[getFileIndex(endingSquare)];
                replace(addPawnSquare, endingColor, endingPiece);
                remove(endingSquare);
                add(startingSquare, startingColor, startingPiece);
                break;
            case FLAG_STANDARD:
                replace(startingSquare, startingColor, startingPiece);
                replace(endingSquare, endingColor, endingPiece);
                break;
        }
        turn ^= 1;
        shortenedFenList.remove(shortenedFenList.size() - 1);
        this.gameOver = false;
        return;
    }

    public void undo() throws Exception {
        simpleUndoReversibleMove();
        updateLegalMoves();
    }

    public int getLength() {
        return reversibleMoves.size();
    }

    public void makePromotionMove(short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        byte promotion = getPromotion(move);
        int startColor = getColor(start);
        int endColor = getColor(end);
        add(start, startColor, promotion);
        move(start, end);
        for (int side : SIDES) {
            if (castleRights[endColor][side]
                    && (ROOK_STARTING_FILES[side] & getBackRank(endColor)) == end) {
                castleRights[endColor][side] = false;
            }
        }
        halfMoveCount = -1;
        return;
    }

    public long getBackRank(int color) {
        return (color == BLACK) ? RANK_8 : RANK_1;
    }

    public void makeCastleMove(short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        int startColor = getColor(start);
        int endColor = getColor(end);
        long mainStartSquare = start;
        long helperStartSquare = end;
        long mainRank = getBackRank(startColor);
        long helperRank = getBackRank(endColor);
        long mainEndSquare = 0;
        long helperEndSquare = 0;
        if (getFileIndex(end) - getFileIndex(start) > 0) {// kingside castle if castling to the right
            mainEndSquare = mainRank & G_FILE;
            helperEndSquare = helperRank & F_FILE;
        } else {
            mainEndSquare = mainRank & C_FILE;
            helperEndSquare = helperRank & D_FILE;
        }
        move(mainStartSquare, mainEndSquare);
        move(helperStartSquare, helperEndSquare);
        for (int side : SIDES) {
            castleRights[startColor][side] = false;
        }
        return;
    }

    public void makeEnPassantMove(short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        long captureSquare = Chess.pushDown(end, end - start > 0 ? 1 : -1);
        remove(captureSquare);
        move(start, end);
        halfMoveCount = -1;
    }

    public void makeStandardMove(Short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = getColor(start);
        int endColor = getColor(end);
        move(start, end);
        if (startPiece == PAWN || endPiece != EMPTY) { // pawn move or capture, reset halfMoveCount
            halfMoveCount = -1;
        }
        if (startPiece == PAWN) { // update enPassantSquare
            int rankDifference = getRankIndex(end) - getRankIndex(start);
            if (Math.abs(rankDifference) == 2) {
                enPassantSquare = pushUp(start, rankDifference / 2);
            }
        }
        for (int side : SIDES) { // update castling rights
            if (castleRights[startColor][side] // update if moving king
                    && startPiece == KING) {
                castleRights[startColor][side] = false;
            }
            if (castleRights[startColor][side] // update if moving an unmoved rook
                    && (ROOK_STARTING_FILES[side] & getBackRank(startColor)) == start) {
                castleRights[startColor][side] = false;
            }
            if (castleRights[endColor][side] // update if capturing an unmoved rook
                    && (ROOK_STARTING_FILES[side] & getBackRank(endColor)) == end) {
                castleRights[endColor][side] = false;
            }
        }
    }

    public void updateLegalMoves() throws Exception {
        if (gameOver) {
            return;
        }
        pseudoLegalMoves[0].clear();
        pseudoLegalMoves[1].clear();
        legalMoves.clear();
        inCheck = calculateInCheck();
        if (shortenedFenList.size() > 0
                && Collections.frequency(shortenedFenList, shortenedFenList.get(shortenedFenList.size() - 1)) == 5) {
            gameOver = true; // 5 fold repetition
            return;
        }
        if (halfMoveCount == 75) {
            gameOver = true;
            return;
        }
        updatePseudoLegalMoves();
        for (short move : pseudoLegalMoves[this.turn]) {
            if (getFlag(move) != FLAG_CASTLE) {
                this.makeShallowMove(move);
                turn ^= 1;
                if (!calculateInCheck()) {
                    legalMoves.add(move);
                }
                turn ^= 1;
                simpleUndoReversibleMove();
            } else {
                if (inCheck) {
                    continue;
                }
                boolean kingSideCastle = ((getEndingSquare(move) & ROOK_STARTING_FILES[KINGSIDE]) != 0);
                long king = pieceBoards[turn][KING];
                if (kingSideCastle) {
                    long swapSquare = e(king);
                    swap(king, swapSquare);
                    if (calculateInCheck()) {
                        swap(king, swapSquare);
                        continue;
                    }
                    swap(king, swapSquare);
                    swapSquare = e(king);
                    swap(king, swapSquare);
                    if (calculateInCheck()) {
                        swap(king, swapSquare);
                        continue;
                    }
                    swap(king, swapSquare);
                    this.makeShallowMove(move);
                    turn ^= 1;
                    if (!calculateInCheck()) {
                        legalMoves.add(move);
                    }
                    turn ^= 1;
                    simpleUndoReversibleMove();
                } else {
                    long swapSquare = w(king);
                    swap(king, swapSquare);
                    if (calculateInCheck()) {
                        swap(king, swapSquare);
                        continue;
                    }
                    swap(king, swapSquare);
                    swapSquare = w(king);
                    swap(king, swapSquare);
                    if (calculateInCheck()) {
                        swap(king, swapSquare);
                        continue;
                    }
                    swap(king, swapSquare);
                    this.makeShallowMove(move);
                    turn ^= 1;
                    if (!calculateInCheck()) {
                        legalMoves.add(move);
                    }
                    turn ^= 1;
                    simpleUndoReversibleMove();
                }
            }
        }
        if (legalMoves.size() == 0) {
            gameOver = true;
        }
        return;
    }

    public boolean calculateInCheck() {
        long king = pieceBoards[turn][KING];
        turn ^= 1; // switch turn
        updatePseudoLegalMoves();
        for (short move : pseudoLegalMoves[this.turn]) { // moves your opponent could make if it was their turn
            if (getEndingSquare(move) == king) { // any of your opponents move capture your king
                turn ^= 1;
                return true;
            }
        }
        turn ^= 1;
        return false;
    }

    public long perft(int depth) throws Exception {
        return perft(depth, true);
    }

    public long perft(int depth, boolean topCall) throws Exception {
        List<Short> moves = new ArrayList<>(legalMoves);
        int n_moves, i;
        int nodes = 0;
        if (depth == 0)
            return 1;
        n_moves = moves.size();
        for (i = 0; i < n_moves; i++) {
            makeMove(moves.get(i));
            long l = perft(depth - 1, false);
            if (topCall) {
                System.out.println("" + getMoveString(moves.get(i)) + ": " + l);
            }
            nodes += l;
            undo();
        }
        if (topCall) {
            System.out.println("Nodes searched: " + nodes);
            System.out.println();
        }

        return nodes;
    }

    public void updatePseudoLegalMoves() {
        pseudoLegalMoves[turn].clear();
        addPseudoLegalPawnMoves();
        addPseudoLegalKnightMoves();
        addPseudoLegalBishopMoves();
        addPseudoLegalRookMoves();
        addPseudoLegalQueenMoves();
        addPseudoLegalKingMoves();
        addPseudoLegalCastleMoves();
        return;
    }

    public void addPseudoLegalPawnMoves() {
        int offset = (turn == BLACK) ? -1 : 1;
        long startingPawns = pieceBoards[turn][PAWN] & ((turn == BLACK) ? RANK_7 : RANK_2); // all pawns on starting
                                                                                            // square
        long emptySquares = pieceBoards[WHITE][EMPTY];

        startingPawns = n(startingPawns, offset) & emptySquares; // advance the pawns one square
        startingPawns = n(startingPawns, offset) & emptySquares; // advance the pawns one square
        long endingSquare = 0l;
        long startingSquare = 0l;
        short move = 0;
        for (long file : FILES) { // this loop adds double move pawns
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = s(endingSquare, 2 * offset);
                move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                if (!pseudoLegalMoves[turn].contains(move)) {
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }

        startingPawns = pieceBoards[turn][PAWN];
        emptySquares = pieceBoards[WHITE][EMPTY];
        startingPawns = n(startingPawns, offset) & emptySquares;
        for (long file : FILES) { // this loop adds single move pawns
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = s(endingSquare, offset);
                if ((endingSquare & getBackRank(turn ^ 1)) != 0) {
                    for (int piece : PROMOTION_PIECES) {
                        move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                } else {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                    if (!pseudoLegalMoves[turn].contains(move)) {
                        pseudoLegalMoves[turn].add(move);
                    }
                }
            }
        }

        startingPawns = pieceBoards[turn][PAWN];
        emptySquares = pieceBoards[WHITE][EMPTY];
        startingPawns = n(startingPawns, offset);
        startingPawns = e(startingPawns, 1) & (combinedBoards[turn ^ 1] | enPassantSquare);
        for (long file : FILES) { // this loop adds pawn captures towards the H-file
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = s(endingSquare, offset);
                startingSquare = w(startingSquare, 1);
                if ((endingSquare & getBackRank(turn ^ 1)) != 0) {
                    for (int piece : PROMOTION_PIECES) {
                        move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                } else if (endingSquare == enPassantSquare) {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_EN_PASSANT);
                    if (!pseudoLegalMoves[turn].contains(move)) {
                        pseudoLegalMoves[turn].add(move);
                    }
                } else {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                    if (!pseudoLegalMoves[turn].contains(move)) {
                        pseudoLegalMoves[turn].add(move);
                    }
                }
            }
        }

        startingPawns = pieceBoards[turn][PAWN];
        emptySquares = pieceBoards[WHITE][EMPTY];
        startingPawns = n(startingPawns, offset);
        startingPawns = w(startingPawns, 1) & (combinedBoards[turn ^ 1] | enPassantSquare);
        for (long file : FILES) { // this loop adds pawn captures towards the A-file
            for (long rank : RANKS) {
                endingSquare = file & rank & startingPawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = s(endingSquare, offset);
                startingSquare = e(startingSquare, 1);
                if ((endingSquare & getBackRank(turn ^ 1)) != 0) {
                    for (int piece : PROMOTION_PIECES) {
                        move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                } else if (endingSquare == enPassantSquare) {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_EN_PASSANT);
                    if (!pseudoLegalMoves[turn].contains(move)) {
                        pseudoLegalMoves[turn].add(move);
                    }
                } else {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                    if (!pseudoLegalMoves[turn].contains(move)) {
                        pseudoLegalMoves[turn].add(move);
                    }
                }
            }
        }
        return;
    }

    public void addPseudoLegalRookMoves() {
        long rooks = pieceBoards[turn][ROOK];
        long enemies = combinedBoards[turn ^ 1];
        long combined = combinedBoards[turn] | enemies;
        for (long rank : RANKS) {
            for (long file : FILES) {
                long startingSquare = rooks & file & rank;
                if (startingSquare == 0) {
                    continue;
                }
                for (int offset : ROOK_OFFSETS) {
                    long destinationSquare = compass(startingSquare, offset);
                    while (destinationSquare != 0 && (destinationSquare & combined) == 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                        destinationSquare = compass(destinationSquare, offset);
                    }
                    if ((destinationSquare & combinedBoards[turn ^ 1]) != 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                }
            }
        }
    }

    public void addPseudoLegalBishopMoves() {
        long bishops = pieceBoards[turn][BISHOP];
        long enemies = combinedBoards[turn ^ 1];
        long combined = combinedBoards[turn] | enemies;
        for (long rank : RANKS) {
            for (long file : FILES) {
                long startingSquare = bishops & file & rank;
                if (startingSquare == 0) {
                    continue;
                }
                for (int offset : BISHOP_OFFSETS) {
                    long destinationSquare = compass(startingSquare, offset);
                    while (destinationSquare != 0 && (destinationSquare & combined) == 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {

                            pseudoLegalMoves[turn].add(move);
                        }
                        destinationSquare = compass(destinationSquare, offset);
                    }
                    if ((destinationSquare & combinedBoards[turn ^ 1]) != 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                }
            }
        }
    }

    public void addPseudoLegalQueenMoves() {
        long queens = pieceBoards[turn][QUEEN];
        long enemies = combinedBoards[turn ^ 1];
        long combined = combinedBoards[turn] | enemies;
        for (long rank : RANKS) {
            for (long file : FILES) {
                long startingSquare = queens & file & rank;
                if (startingSquare == 0) {
                    continue;
                }
                for (int offset : BISHOP_OFFSETS) {
                    long destinationSquare = compass(startingSquare, offset);
                    while (destinationSquare != 0 && (destinationSquare & combined) == 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                        destinationSquare = compass(destinationSquare, offset);
                    }
                    if ((destinationSquare & combinedBoards[turn ^ 1]) != 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                }
                for (int offset : ROOK_OFFSETS) {
                    long destinationSquare = compass(startingSquare, offset);
                    while (destinationSquare != 0 && (destinationSquare & combined) == 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                        destinationSquare = compass(destinationSquare, offset);
                    }
                    if ((destinationSquare & combinedBoards[turn ^ 1]) != 0) {
                        short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                }
            }
        }
    }

    public void addPseudoLegalKnightMoves() {
        long knights = pieceBoards[turn][KNIGHT];
        long emptySquares = pieceBoards[WHITE][EMPTY];
        long enemies = combinedBoards[turn ^ 1];
        long destinationSquares = 0;
        for (int offset : KNIGHT_OFFSETS) {
            destinationSquares |= compass(knights, offset) & (emptySquares | enemies);
        }
        long endingSquare;
        long startingSquare;
        short move;
        for (long file : FILES) {
            for (long rank : RANKS) {
                endingSquare = file & rank & destinationSquares;
                if (endingSquare == 0) {
                    continue;
                }
                for (int offset : KNIGHT_OFFSETS) {
                    startingSquare = compass(endingSquare, offset) & knights;
                    if (startingSquare != 0) {
                        move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                        if (!pseudoLegalMoves[turn].contains(move)) {
                            pseudoLegalMoves[turn].add(move);
                        }
                    }
                }
            }
        }
        return;
    }

    public void addPseudoLegalKingMoves() {
        long kings = pieceBoards[turn][KING];
        long possibleSquares = combinedBoards[turn ^ 1] | pieceBoards[WHITE][EMPTY];
        for (int offset : ROOK_OFFSETS) {
            long destinationSquare = compass(kings, offset) & possibleSquares;
            if (destinationSquare != 0) {
                short move = encodeMove(kings, destinationSquare, 0, FLAG_STANDARD);
                if (!pseudoLegalMoves[turn].contains(move)) {
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }
        for (int offset : BISHOP_OFFSETS) {
            long destinationSquare = compass(kings, offset) & possibleSquares;
            if (destinationSquare != 0) {
                short move = encodeMove(kings, destinationSquare, 0, FLAG_STANDARD);
                if (!pseudoLegalMoves[turn].contains(move)) {
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }
        return;
    }

    public void addPseudoLegalCastleMoves() {
        long kings = pieceBoards[turn][KING];
        long gap = 0;
        long combined = combinedBoards[turn] | combinedBoards[turn ^ 1];
        if (castleRights[turn][KINGSIDE]) {
            gap = (e(kings) | e(e(kings))) & combined; // space from king to rook empty
            if (gap == 0) {
                long destinationSquare = e(e(e(kings)));
                short move = encodeMove(kings, destinationSquare, 0, FLAG_CASTLE);
                if (!pseudoLegalMoves[turn].contains(move)) {
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }
        if (castleRights[turn][QUEENSIDE]) {
            gap = (w(kings) | w(w(kings)) | w(w(w(kings)))) & combined; // space from king to rook empty
            if (gap == 0) {
                long destinationSquare = w(w(w(w(kings))));
                short move = encodeMove(kings, destinationSquare, 0, FLAG_CASTLE);
                if (!pseudoLegalMoves[turn].contains(move)) {
                    pseudoLegalMoves[turn].add(move);
                }
            }
        }
    }

    public void move(long start, long end) {
        int startPiece = getPiece(start);
        int startColor = getColor(start);
        remove(start);
        add(end, startColor, startPiece);
    }

    public void swap(long start, long end) {
        int startPiece = getPiece(start);
        int endPiece = getPiece(end);
        int startColor = getColor(start);
        int endColor = getColor(end);
        add(start, endColor, endPiece);
        add(end, startColor, startPiece);
    }

    public void swap(String start, String end) {
        swap(create(start), create(end));
    }

    public void replace(long square, int color, int piece) {
        add(square, color, piece);
    }

    public void add(long square, int color, int piece) {
        int priorPiece = getPiece(square);
        int priorColor = getColor(square);
        pieceBoards[priorColor][priorPiece] &= ~square; // remove prior piece from square
        if (priorPiece != EMPTY) {
            combinedBoards[priorColor] &= ~square; // remove prior piece from combinedBoard if its not EMPTY
        }
        pieceBoards[color][piece] |= square; // add new piece to square
        if (piece != EMPTY) {
            combinedBoards[color] |= square; // add new piece to combinedBoard if its not EMPTY
        }
    }

    public void remove(long square) {
        add(square, WHITE, EMPTY);
    }

    public int getPiece(long square) {
        int color = getColor(square);
        for (int piece : PIECES) {
            if ((pieceBoards[color][piece] & square) == square) {
                return piece;
            }
        }
        return -1; // This shouldn't occur
    }

    public int getColor(long square) {
        return (combinedBoards[BLACK] & square) == square ? BLACK : WHITE;
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

    public String getFen() {
        StringBuilder build = new StringBuilder("");
        int count = 0;
        for (long rank : RANKS_REVERSED) {
            for (long file : FILES) {
                int piece = getPiece(rank & file);
                int color = getColor(rank & file);
                if (piece == EMPTY) {
                    count++;
                    if (count == FILES.length) {
                        build.append(count);
                        count = 0;
                    }
                    continue;
                }
                if (count > 0) {
                    build.append(count);
                    count = 0;
                }
                build.append(PIECE_CHARS_ASCII[color][piece]);
            }
            build.append("/");
        }
        build.setLength(build.length() - 1);
        build.append(" ");
        if (turn == WHITE) {
            build.append("w ");
        } else {
            build.append("b ");
        }
        boolean anyCastle = false;
        for (int color : COLORS) {
            for (int side : SIDES_REVERSED) {
                if (castleRights[color][side]) {
                    anyCastle = true;
                    char temp = side == KINGSIDE ? 'k' : 'q';
                    if (color == WHITE) {
                        temp = Character.toUpperCase(temp);
                    }
                    build.append(temp);
                }
            }
        }
        if (!anyCastle) {
            build.append("-");
        }
        build.append(" ");
        if (enPassantSquare == 0l) {
            build.append("-");
        } else {
            build.append((char) (getFileIndex(enPassantSquare) + 'a'));
            build.append((char) (getRankIndex(enPassantSquare) + '1'));
        }
        build.append(" ");
        build.append(halfMoveCount);
        build.append(" ");
        build.append(fullMoveCount);
        return build.toString();
    }

    public String getShortenedFen() {
        StringBuilder build = new StringBuilder("");
        int count = 0;
        for (long rank : RANKS_REVERSED) {
            for (long file : FILES) {
                int piece = getPiece(rank & file);
                int color = getColor(rank & file);
                if (piece == EMPTY) {
                    count++;
                    if (count == FILES.length) {
                        build.append(count);
                        count = 0;
                    }
                    continue;
                }
                if (count > 0) {
                    build.append(count);
                    count = 0;
                }
                build.append(PIECE_CHARS_ASCII[color][piece]);
            }
            build.append("/");
        }
        build.setLength(build.length() - 1);
        build.append(" ");
        if (turn == WHITE) {
            build.append("w ");
        } else {
            build.append("b ");
        }
        boolean anyCastle = false;
        for (int color : COLORS) {
            for (int side : SIDES_REVERSED) {
                if (castleRights[color][side]) {
                    anyCastle = true;
                    char temp = side == KINGSIDE ? 'k' : 'q';
                    if (color == WHITE) {
                        temp = Character.toUpperCase(temp);
                    }
                    build.append(temp);
                }
            }
        }
        if (!anyCastle) {
            build.append("-");
        }
        build.append(" ");
        if (enPassantSquare == 0l) {
            build.append("-");
        } else {
            build.append((char) (getFileIndex(enPassantSquare) + 'a'));
            build.append((char) (getRankIndex(enPassantSquare) + '1'));
        }
        return build.toString();
    }

    public void setFromFen(String fen) throws Exception {
        int fileIndex = 0;
        int stringIndex = 0;
        char pointer = fen.charAt(stringIndex);
        for (long rank : RANKS_REVERSED) {
            fileIndex = 0;
            while (pointer != '/' && pointer != ' ') {
                if ('0' <= pointer && pointer <= '9') {
                    for (int i = 0; i < pointer - '0'; i++) {
                        add(FILES[fileIndex + i] & rank, WHITE, EMPTY);
                    }
                    fileIndex += pointer - '0';
                    stringIndex++;
                    pointer = fen.charAt(stringIndex);
                    continue;
                }
                int piece = getPieceFromChar(pointer);
                int color = getColorFromChar(pointer);
                add(FILES[fileIndex] & rank, color, piece);
                fileIndex++;
                stringIndex++;
                pointer = fen.charAt(stringIndex);
            }
            stringIndex++;
            pointer = fen.charAt(stringIndex);
        }
        turn = (pointer == 'w') ? WHITE : BLACK;
        stringIndex += 2;
        pointer = fen.charAt(stringIndex);
        for (int color : COLORS) {
            for (int side : SIDES) {
                castleRights[color][side] = false;
            }
        }
        if (pointer == '-') {
            stringIndex++;
            pointer = fen.charAt(stringIndex);
        } else {
            while (pointer != ' ') {
                int color = getColorFromChar(pointer);
                int side = (Character.toUpperCase(pointer) == 'Q') ? QUEENSIDE : KINGSIDE;
                castleRights[color][side] = true;
                stringIndex++;
                pointer = fen.charAt(stringIndex);
            }
        }
        stringIndex++;
        pointer = fen.charAt(stringIndex);
        if (pointer == '-') {
            enPassantSquare = 0l;
        } else {
            long file = FILES[pointer - 'a'];
            stringIndex++;
            pointer = fen.charAt(stringIndex);
            long rank = RANKS[pointer - '1'];
            enPassantSquare = file & rank;
        }
        stringIndex += 2;
        pointer = fen.charAt(stringIndex);
        String temp = "";
        while (pointer != ' ') {
            temp += pointer;
            stringIndex++;
            pointer = fen.charAt(stringIndex);
        }
        halfMoveCount = Integer.parseInt(temp);
        stringIndex++;
        temp = fen.substring(stringIndex);
        fullMoveCount = Integer.parseInt(temp);
        reversibleMoves.clear();
        shortenedFenList.clear();
        updateLegalMoves();
        shortenedFenList.add(getShortenedFen());
        return;
    }

    public int getPieceFromChar(char c) {
        if (c == ' ') {
            return EMPTY;
        }
        switch (Character.toUpperCase(c)) {
            case 'Q':
                return QUEEN;
            case 'R':
                return ROOK;
            case 'B':
                return BISHOP;
            case 'N':
                return KNIGHT;
            case 'K':
                return KING;
            case 'P':
                return PAWN;
            default:
                return -1;
        }
    }

    public int getSideFromChars(char right, char left) {
        int colorLeft = getColor(left);
        int colorRight = getColor(right);
        if (colorLeft == colorRight) {
            return QUEENSIDE;
        }
        return KINGSIDE;
    }

    public int getColorFromChar(char c) {
        if (c == ' ') {
            return WHITE;
        }
        return Character.isUpperCase(c) ? WHITE : BLACK;
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

    public static long compass(long input, int offset) {
        int east = (int) (3 & pushLeft(offset, 2));
        int north = (int) (3 & offset);
        if ((offset & pushRight(1l, 8)) != 0) {
            east = -east;
        }
        if ((offset & pushRight(1l, 7)) != 0) {
            north = -north;
        }
        long result = input;
        if (east > 0) {
            for (int i = 0; i < east; i++) {
                result = e(result);
            }
        } else {
            for (int i = 0; i < -east; i++) {
                result = w(result);
            }
        }
        if (north > 0) {
            for (int i = 0; i < north; i++) {
                result = n(result);
            }
        } else {
            for (int i = 0; i < -north; i++) {
                result = s(result);
            }
        }
        return result;
    }

    public static long compass(long input, int east, int north) {
        int offset = encodeDirection(east, north);
        return compass(input, offset);
    }

    public static int encodeDirection(int east, int north) {
        int output = 0;
        if (east < 0) {
            output |= pushRight(1l, 8);
            east = -east;
        }
        if (north < 0) {
            output |= pushRight(1l, 7);
            north = -north;
        }
        output |= pushRight(3 & east, 2);
        output |= (3 & north);
        return output;
    }

    public static long n(long input) {
        return (input & ~RANK_8) << 8;
    }

    public static long n(long input, int count) {
        int offset = encodeDirection(0, count);
        return compass(input, offset);
    }

    public static long s(long input, int count) {
        int offset = encodeDirection(0, -count);
        return compass(input, offset);
    }

    public static long e(long input, int count) {
        int offset = encodeDirection(count, 0);
        return compass(input, offset);
    }

    public static long w(long input, int count) {
        int offset = encodeDirection(-count, 0);
        return compass(input, offset);
    }

    public static long s(long input) {
        return (input & ~RANK_1) >>> 8;
    }

    public static long e(long input) {
        return (input & ~H_FILE) << 1;
    }

    public static long w(long input) {
        return (input & ~A_FILE) >>> 1;
    }

    public static long ne(long input) {
        return n(e(input));
    }

    public static long nw(long input) {
        return n(w(input));
    }

    public static long se(long input) {
        return s(e(input));
    }

    public static long sw(long input) {
        return s(w(input));
    }

    public static long nne(long input) {
        return n(n(e(input)));
    }

    public static long nnw(long input) {
        return n(n(w(input)));
    }

    public static long sse(long input) {
        return s(s(e(input)));
    }

    public static long ssw(long input) {
        return s(s(w(input)));
    }

    public static long nee(long input) {
        return n(e(e(input)));
    }

    public static long nww(long input) {
        return n(w(w(input)));
    }

    public static long see(long input) {
        return s(e(e(input)));
    }

    public static long sww(long input) {
        return s(w(w(input)));
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