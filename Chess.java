import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Chess {
    public static final int NORTH = 1;
    public static final int SOUTH = -1;
    public static final int EAST = 1;
    public static final int WEST = -1;
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
    public static final int[] PROMOTION_PIECES = { QUEEN, KNIGHT, BISHOP, ROOK };
    public static final byte[] FLAGS = new byte[] { FLAG_STANDARD, FLAG_CASTLE, FLAG_EN_PASSANT, FLAG_PROMOTION };
    public static final int[] PIECES = new int[] { QUEEN, KNIGHT, BISHOP, ROOK, KING, PAWN };
    public static final long[] FILES = new long[] { A_FILE, B_FILE, C_FILE, D_FILE, E_FILE, F_FILE, G_FILE, H_FILE };
    public static final long[] FILES_REVERSED = new long[] { H_FILE, G_FILE, F_FILE, E_FILE, D_FILE, C_FILE, B_FILE,
            A_FILE };
    public static final long[] RANKS = new long[] { RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8 };
    public static final long[] RANKS_REVERSED = new long[] { RANK_8, RANK_7, RANK_6, RANK_5, RANK_4, RANK_3, RANK_2,
            RANK_1 };
    public static final char[][] PIECE_CHARS_UNICODE = new char[][] { { '♛', '♞', '♝', '♜', '♚', '♟', ' ' },
            { '♕', '♘', '♗', '♖', '♔', '♙', ' ' } }; // All empty squares will be white, ⚠ should never print
    public static final char[][] PIECE_CHARS_ASCII = new char[][] { { 'Q', 'N', 'B', 'R', 'K', 'P', ' ' },
            { 'q', 'n', 'b', 'r', 'k', 'p', ' ' } }; // All empty squares will be white, ⚠ should never print
    public static final int[] COLORS = new int[] { WHITE, BLACK };
    public static final int[] COLORS_REVERSED = new int[] { BLACK, WHITE };
    public static final int[] SIDES = new int[] { QUEENSIDE, KINGSIDE };
    public static final int[] SIDES_REVERSED = new int[] { KINGSIDE, QUEENSIDE };
    public static final long[][] DEFAULT_PIECEBOARDS = new long[][] {
            { 8l, 66l, 36l, 129l, 16l, 65280l },
            { 576460752303423488l, 4755801206503243776l, 2594073385365405696l,
                    -9151314442816847872l, 1152921504606846976l,
                    71776119061217280l }
    };
    public static final long[] ROOK_STARTING_FILES = new long[] { A_FILE, H_FILE };

    public static final long[] knightMoveSquares = { 0x00020400L, 0x00050800L, 0x000A1100L, 0x00142200L, 0x00284400L,
            0x00508800L, 0x00A01000L, 0x00402000L, 0x02040004L, 0x05080008L, 0x0A110011L, 0x14220022L, 0x28440044L,
            0x50880088L, 0xA0100010L, 0x40200020L, 0x204000402L, 0x508000805L, 0xA1100110AL, 0x1422002214L,
            0x2844004428L, 0x5088008850L, 0xA0100010A0L, 0x4020002040L, 0x20400040200L, 0x50800080500L, 0xA1100110A00L,
            0x142200221400L, 0x284400442800L, 0x508800885000L, 0xA0100010A000L, 0x402000204000L, 0x2040004020000L,
            0x5080008050000L, 0xA1100110A0000L, 0x14220022140000L, 0x28440044280000L, 0x50880088500000L,
            0xA0100010A00000L, 0x40200020400000L, 0x204000402000000L, 0x508000805000000L, 0xA1100110A000000L,
            0x1422002214000000L, 0x2844004428000000L, 0x5088008850000000L, 0xA0100010A0000000L, 0x4020002040000000L,
            0x400040200000000L, 0x800080500000000L, 0x1100110A00000000L, 0x2200221400000000L, 0x4400442800000000L,
            0x8800885000000000L, 0x100010A000000000L, 0x2000204000000000L, 0x4020000000000L, 0x8050000000000L,
            0x110A0000000000L, 0x22140000000000L, 0x44280000000000L, 0x88500000000000L, 0x10A00000000000L,
            0x20400000000000L };

    public static final long[] kingMoveSquares = { 0x00000302L, 0x00000705L, 0x00000E0AL, 0x00001C14L, 0x00003828L,
            0x00007050L,
            0x0000E0A0L, 0x0000C040L, 0x00030203L, 0x00070507L, 0x000E0A0EL, 0x001C141CL, 0x00382838L, 0x00705070L,
            0x00E0A0E0L, 0x00C040C0L, 0x03020300L, 0x07050700L, 0x0E0A0E00L, 0x1C141C00L, 0x38283800L, 0x70507000L,
            0xE0A0E000L, 0xC040C000L, 0x302030000L, 0x705070000L, 0xE0A0E0000L, 0x1C141C0000L, 0x3828380000L,
            0x7050700000L, 0xE0A0E00000L, 0xC040C00000L, 0x30203000000L, 0x70507000000L, 0xE0A0E000000L,
            0x1C141C000000L, 0x382838000000L, 0x705070000000L, 0xE0A0E0000000L, 0xC040C0000000L, 0x3020300000000L,
            0x7050700000000L, 0xE0A0E00000000L, 0x1C141C00000000L, 0x38283800000000L, 0x70507000000000L,
            0xE0A0E000000000L, 0xC040C000000000L, 0x302030000000000L, 0x705070000000000L, 0xE0A0E0000000000L,
            0x1C141C0000000000L, 0x3828380000000000L, 0x7050700000000000L, 0xE0A0E00000000000L, 0xC040C00000000000L,
            0x203000000000000L, 0x507000000000000L, 0xA0E000000000000L, 0x141C000000000000L, 0x2838000000000000L,
            0x5070000000000000L, 0xA0E0000000000000L, 0x40C0000000000000L };
    public static final long[] horizontalMoves = { 0xFFFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFFFL, 0xFFFFFFFFFFFFL,
            0xFFFFFFFFFFL, 0xFFFFFFFFL, 0x00FFFFFFL, 0x0000FFFFL, 0x000000FFL, 0x00000000L };

    public static final long[] verticalMoves = { 0xFFFFFFFFFFFFFFFFL, 0xFEFEFEFEFEFEFEFEL, 0xFCFCFCFCFCFCFCFCL,
            0xF8F8F8F8F8F8F8F8L, 0xF0F0F0F0F0F0F0F0L, 0xE0E0E0E0E0E0E0E0L, 0xC0C0C0C0C0C0C0C0L, 0x8080808080808080L,
            0x00000000L };

    public static final int HASH_SIZE = 71;
    // Object instance variables
    int turn;
    long[][] pieceBoards;
    long[] combinedBoards;
    boolean[][] castleRights;
    long enPassantSquare;
    int halfMoveCount;
    int fullMoveCount;
    Set<Short> legalMoves;
    List<Set<Short>> pseudoLegalMoves;
    List<Long> reversibleMoves;
    List<String> hashList;
    boolean gameOver;
    char[] hash;

    public Chess(String fen) throws Exception {
        this();
        setFromFen(fen);
    }

    public Chess() throws Exception {
        hash = new char[HASH_SIZE];
        turn = WHITE;
        gameOver = false;
        enPassantSquare = 0l;
        halfMoveCount = 0;
        fullMoveCount = 1;
        reversibleMoves = new ArrayList<>();
        legalMoves = new HashSet<>();
        hashList = new ArrayList<>();
        pseudoLegalMoves = new ArrayList<>();
        pieceBoards = new long[COLORS.length][PIECES.length];
        combinedBoards = new long[COLORS.length];
        castleRights = new boolean[COLORS.length][SIDES.length];
        for (int color : COLORS) {
            pseudoLegalMoves.add(new HashSet<>());
            for (int piece : PIECES) {
                pieceBoards[color][piece] = DEFAULT_PIECEBOARDS[color][piece];
                combinedBoards[color] |= pieceBoards[color][piece];
            }
            for (int side : SIDES) {
                castleRights[color][side] = true;
            }
        }
        setHash();
        hashList.add(new String(hash));
        getLegalMoves(legalMoves);
    }

    public void move(String... moves) throws Exception {
        for (String move : moves) {
            move(move);
        }
    }

    public void move(String moveString) throws Exception {
        for (short move : legalMoves) {
            if (getMoveString(move).equals(moveString)) {
                move(move);
                return;
            }
        }
        throw new Exception("" + moveString + " is not a legal move.");
    }

    public void move(short move) throws Exception {
        if (!legalMoves.contains(move)) {
            throw new Exception("" + getMoveString(move) + " is not a legal move.");
        }
        moveNoUpdate(move);
        getLegalMoves(legalMoves);
    }

    public void moveNoUpdate(short move) throws Exception {
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
                throw new Exception("Not a valid flag.");
        }
        if (turn == BLACK) {
            fullMoveCount += 1;
        }
        halfMoveCount += 1;
        turn ^= 1;
        updateHash(move);
        hashList.add(new String(hash));
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
        long captureSquare = Chess.s(end, end - start > 0 ? 1 : -1);
        remove(captureSquare);
        move(start, end);
        halfMoveCount = -1;
        return;
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
                enPassantSquare = n(start, rankDifference / 2);
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

    public void undo() throws Exception {
        undoNoUpdate();
        getLegalMoves(legalMoves);
    }

    public void undoNoUpdate() throws Exception {
        if (reversibleMoves.size() == 0) {
            throw new Exception("No move to undo.");
        }
        // castleRights (4 bits)
        halfMoveCount = 0; // (32 bits)
        int flag = 0; // (2 bits)
        int enPassantSquareOffset = 0; // 6 bits
        int endingColor = 0; // 1 bit
        int endingPiece = 0; // 3 bits
        int endingSquareOffset = 0; // 6 bits
        int startingPiece = 0; // 3 bits
        int startingSquareOffset = 0; // 6 bits
        long endingSquare = 0l;
        long startingSquare = 0l;
        long input = reversibleMoves.remove(reversibleMoves.size() - 1);
        long push = 0;
        for (int color : COLORS_REVERSED) {
            for (int side : SIDES_REVERSED) {
                push = 1l;
                while (push != 0) {
                    castleRights[color][side] = (push & input) != 0;
                    push = push >>> 1;
                }
                input = input >>> 1;
            }
        }
        push = 1l << 31;
        while (push != 0) {
            halfMoveCount |= push & input;
            push = push >>> 1;
        }
        input = input >>> 32;

        push = 1l << 1;
        while (push != 0) {
            flag |= push & input;
            push = push >>> 1;
        }
        input = input >>> 2;

        push = 1l << 5;
        while (push != 0) {
            enPassantSquareOffset |= push & input;
            push = push >>> 1;
        }
        if (enPassantSquareOffset != 0) { // EnPassantOffset gets encoded as 0 if their is not enpassant square
            enPassantSquare = 1L << enPassantSquareOffset;
        } else {
            enPassantSquare = 0;
        }
        input = input >>> 6;

        push = 1l;
        while (push != 0) {
            endingColor |= push & input;
            push = push >>> 1;
        }
        input = input >>> 1;

        push = 1l << 2;
        while (push != 0) {
            endingPiece |= push & input;
            push = push >>> 1;
        }
        input = input >>> 3;

        push = 1l << 5;
        while (push != 0) {
            endingSquareOffset |= push & input;
            push = push >>> 1;
        }
        endingSquare = 1l << endingSquareOffset;
        input = input >>> 6;

        push = 1l << 2;
        while (push != 0) {
            startingPiece |= push & input;
            push = push >>> 1;
        }
        input = input >>> 3;

        push = 1l << 5;
        while (push != 0) {
            startingSquareOffset |= push & input;
            push = push >>> 1;
        }
        startingSquare = 1l << startingSquareOffset;
        input = input >>> 6;
        int startingColor = turn ^ 1;
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
                add(startingSquare, startingColor, startingPiece);
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
            default:
                throw new Exception("Not a valid flag.");
        }
        turn ^= 1;
        this.gameOver = false;
        hash = hashList.remove(hashList.size() - 1).toCharArray();
        return;
    }

    public Set<Short> getLegalMoves() throws Exception {
        return getLegalMoves(new HashSet<Short>());
    }

    public Set<Short> getLegalMoves(Set<Short> output) throws Exception {
        output.clear();
        if (gameOver) {
            return output;
        }
        if (Collections.frequency(hashList, hashList.get(hashList.size() - 1)) == 5) {
            gameOver = true; // 5 fold repetition
            return output;
        }
        if (halfMoveCount == 75) {
            gameOver = true;
            return output;
        }
        getPseudoLegalMoves(pseudoLegalMoves.get(turn));
        for (short move : pseudoLegalMoves.get(turn)) {
            this.moveNoUpdate(move);
            getPseudoLegalMoves(pseudoLegalMoves.get(turn));
            if (!enemyInCheck()) {
                output.add(move);
            }
            undoNoUpdate();
        }
        if (legalMoves.size() == 0) {
            gameOver = true;
        }
        return output;
    }

    public Set<Short> getPseudoLegalMoves(Set<Short> moveSet) {
        moveSet.clear();
        addPseudoLegalPawnMoves(moveSet, pieceBoards[turn][PAWN]);
        addPseudoLegalKnightMoves(moveSet, pieceBoards[turn][KNIGHT]);
        addPseudoLegalBishopMoves(moveSet, pieceBoards[turn][BISHOP] | pieceBoards[turn][QUEEN]);
        addPseudoLegalRookMoves(moveSet, pieceBoards[turn][ROOK] | pieceBoards[turn][QUEEN]);
        addPseudoLegalKingMoves(moveSet, pieceBoards[turn][KING]);
        addPseudoLegalCastleMoves(moveSet, pieceBoards[turn][KING]);
        return moveSet;
    }

    public Set<Short> getPseudoLegalMoves() {
        return getPseudoLegalMoves(new HashSet<Short>());
    }

    public void addPseudoLegalPawnDoublePushes(Set<Short> moveSet) {
        final long emptySquares = getEmpty();
        int forward = (turn == BLACK) ? SOUTH : NORTH;
        long pawnBirthSquares = ((turn == BLACK) ? RANK_7 : RANK_2);
        long doublePawns = pieceBoards[turn][PAWN] & pawnBirthSquares; // all friendly pawns who haven't moved
        doublePawns = compass(doublePawns, forward, 0) & emptySquares;
        doublePawns = compass(doublePawns, forward, 0) & emptySquares;
        short move = 0;
        for (int endingOffset : serializeBitboard(doublePawns)) {
            long endingSquare = getBitboard(endingOffset);
            long startingSquare = compass(endingSquare, -2 * forward, 0);
            move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
            moveSet.add(move);
        }
    }

    public void addPseudoLegalPawnSinglePushes(Set<Short> moveSet, long startingPieces) {
        final long emptySquares = getEmpty();
        int forward = (turn == BLACK) ? SOUTH : NORTH;
        long promotionSquares = ((turn == BLACK) ? RANK_1 : RANK_8);
        long doublePawns = pieceBoards[turn][PAWN]; // all friendly pawns
        doublePawns = compass(doublePawns, forward, 0) & emptySquares;
        short move = 0;
        for (int endingOffset : serializeBitboard(doublePawns)) {
            long endingSquare = getBitboard(endingOffset);
            long startingSquare = compass(endingSquare, -forward, 0);
            if (isEmpty(promotionSquares & endingSquare)) {
                move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                moveSet.add(move);
            } else {
                for (int piece : PROMOTION_PIECES) {
                    move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                }
            }
        }
    }

    public void addPseudoLegalPawnCapturesWest(Set<Short> moveSet, long startingPieces) {
        // DON'T FORGET PROMOTION AND EN PASSANT!!!
        final long captureSquares = combinedBoards[turn ^ 1];
        int forward = (turn == BLACK) ? SOUTH : NORTH;
        long promotionSquares = ((turn == BLACK) ? RANK_1 : RANK_8);
        long doublePawns = pieceBoards[turn][PAWN]; // all friendly pawns
        doublePawns = compass(doublePawns, forward, WEST) & captureSquares;
        short move = 0;
        for (int endingOffset : serializeBitboard(doublePawns)) {
            long endingSquare = getBitboard(endingOffset);
            long startingSquare = compass(endingSquare, -forward, 0);
            if (isEmpty(promotionSquares & endingSquare)) {
                move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                moveSet.add(move);
            } else {
                for (int piece : PROMOTION_PIECES) {
                    move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                }
            }
        }
    }

    public void addPseudoLegalDoubleCapturesEast(Set<Short> moveSet, long startingPieces) {
        final long emptySquares = getEmpty();
        final long captureSquares = combinedBoards[turn ^ 1] | enPassantSquare;
        final long backRank = getBackRank(turn ^ 1);
        int offset = (turn == BLACK) ? -1 : 1;
        long pawns = startingPieces;
        pawns &= ((turn == BLACK) ? RANK_7 : RANK_2); // all pawns on starting square
        pawns = n(pawns, offset) & emptySquares; // advance the pawns one square
        pawns = n(pawns, offset) & emptySquares; // advance the pawns one square
        long endingSquare = 0l;
        long startingSquare = 0l;
        short move = 0;
        pawns = startingPieces;
        pawns = n(pawns, offset);
        pawns = w(pawns, 1) & captureSquares;
        for (long file : FILES) { // this loop adds pawn captures towards the H-file
            for (long rank : RANKS) {
                endingSquare = file & rank & pawns;
                if (endingSquare == 0) {
                    continue;
                }
                startingSquare = s(endingSquare, offset);
                startingSquare = e(startingSquare, 1);
                if ((endingSquare & backRank) != 0) {
                    for (int piece : PROMOTION_PIECES) {
                        move = encodeMove(startingSquare, endingSquare, piece, FLAG_PROMOTION);
                        moveSet.add(move);
                    }
                } else if (endingSquare == enPassantSquare) {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_EN_PASSANT);
                    moveSet.add(move);
                } else {
                    move = encodeMove(startingSquare, endingSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
            }
        }
    }

    public void addPseudoLegalRookMoves(Set<Short> moveSet, long startingPieces) {
        long rooks = startingPieces;
        final long enemies = combinedBoards[turn ^ 1];
        final long friends = combinedBoards[turn];
        final long enemiesOrFriends = enemies | friends;
        for (long rank : RANKS) {
            for (long file : FILES) {
                long startingSquare = rooks & file & rank;
                if (startingSquare == 0) {
                    continue;
                }
                long destinationSquare = n(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = n(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
                destinationSquare = s(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = s(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
                destinationSquare = e(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = e(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
                destinationSquare = w(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = w(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
            }
        }
    }

    public void addPseudoLegalBishopMoves(Set<Short> moveSet, long startingPieces) {
        long bishops = startingPieces;
        final long enemies = combinedBoards[turn ^ 1];
        final long friends = combinedBoards[turn];
        final long enemiesOrFriends = enemies | friends;
        for (long rank : RANKS) {
            for (long file : FILES) {
                long startingSquare = bishops & file & rank;
                if (startingSquare == 0) {
                    continue;
                }
                long destinationSquare = ne(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = ne(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
                destinationSquare = se(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = se(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
                destinationSquare = nw(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = nw(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
                destinationSquare = sw(startingSquare);
                while (destinationSquare != 0 && (destinationSquare & enemiesOrFriends) == 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                    destinationSquare = sw(destinationSquare);
                }
                if ((destinationSquare & enemies) != 0) {
                    short move = encodeMove(startingSquare, destinationSquare, 0, FLAG_STANDARD);
                    moveSet.add(move);
                }
            }
        }
    }

    public void addPseudoLegalKnightMoves(Set<Short> moveSet, long startingPieces) {
        for (byte start : serializeBitboard(startingPieces)) {
            for (byte end : serializeBitboard(
                    knightMoveSquares[start] & (combinedBoards[turn ^ 1] | getEmpty()))) {
                moveSet.add(encodeMove(1l << start, 1l << end, 0, FLAG_STANDARD));
            }
        }
    }

    public void addPseudoLegalKingMoves(Set<Short> moveSet, long startingPieces) {
        for (byte end : serializeBitboard(
                kingMoveSquares[getIndex(startingPieces)] & (combinedBoards[turn ^ 1] | getEmpty()))) {
            moveSet.add(encodeMove(startingPieces, 1l << end, 0, FLAG_STANDARD));
        }
    }

    public void addPseudoLegalCastleMoves(Set<Short> moveSet, long startingPieces) {
        if (castleRights[turn][KINGSIDE] & (isEmpty(e(startingPieces) | e(e(startingPieces))))) {
            moveSet.add(encodeMove(startingPieces, e(e(e(startingPieces))), QUEEN, FLAG_CASTLE));
        }
        if (castleRights[turn][QUEENSIDE]
                & (isEmpty(w(startingPieces) | w(w(startingPieces)) | w(w(w(startingPieces)))))) {
            moveSet.add(encodeMove(startingPieces, w(w(w(w(startingPieces)))), QUEEN, FLAG_CASTLE));
        }
    }

    public List<Byte> serializeBitboard(long board) {
        List<Byte> output = new ArrayList<>();
        byte index = 0;
        while (board != 0) {
            index = (byte) getIndex(board);
            board = board & (~(1l << index));
            output.add(index);
        }
        return output;
    }

    public boolean isEmpty(long squares) {
        return (squares & (combinedBoards[WHITE] | combinedBoards[BLACK])) == 0;
    }

    public boolean enemyInCheck() {
        return enemySquareAttacked(pieceBoards[turn ^ 1][KING]);
    }

    public boolean enemySquareAttacked(long square) { // Returns true if one of your pieces is attacking a square
        // In the case of pawns, only considers diagonal attacks.
        // also if a king just castled and a square it castled through is attacked,
        // including starting square, and
        // square = where the king is now, this will return true
        if (((e(n(pieceBoards[turn][PAWN], (turn == BLACK) ? -1 : 1))
                | w(n(pieceBoards[turn][PAWN], (turn == BLACK) ? -1 : 1))) & getEmpty() & square) != 0) {
            return true;
        }
        long enemyKing = pieceBoards[turn ^ 1][KING];
        if (square == enemyKing) {
            if (reversibleMoves.size() > 0
                    && ((reversibleMoves.get(reversibleMoves.size() - 1) >>> 36) & 3) == FLAG_CASTLE) {
                if ((pieceBoards[turn ^ 1][KING] & G_FILE) != 0) {
                    if (enemySquareAttacked(w(enemyKing))) {
                        return true;
                    }
                    if (enemySquareAttacked(w(w(enemyKing)))) {
                        return true;
                    }
                } else {
                    if (enemySquareAttacked(e(enemyKing))) {
                        return true;
                    }
                    if (enemySquareAttacked(e(e(enemyKing)))) {
                        return true;
                    }
                }
            }
        }
        for (short move : pseudoLegalMoves.get(turn)) {
            if (getEndingSquare(move) == square) {
                long startingSquare = getStartingSquare(move);
                int attackingPiece = getPiece(startingSquare);
                if (attackingPiece == PAWN && getFileIndex(square) == getFileIndex(startingSquare)) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }

    public long encodeReversibleMove(short move) {
        // Info needed to encode reversible move:
        // starting and ending square, starting and ending piece,
        // starotting and ending piece color, enPassantSquare
        // castleRights, move flag, and half move count.
        // starting piece and starting color can be inferred from board state
        // so they do not need to be encoded. But they will just for ease.

        long startingSquareLong = getStartingSquare(move);
        int startingSquareInt = Long.numberOfTrailingZeros(startingSquareLong); // 6 bits
        int startingPiece = getPiece(startingSquareLong); // 3 bits
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
        long push = 1l << 5;
        while (push != 0) {
            output |= push & startingSquareInt;
            push = (push >>> 1);
        }

        output = output << 3;
        push = 1l << 2;
        while (push != 0) {
            output |= push & startingPiece;
            push = (push >>> 1);
        }

        output = output << 6;
        push = 1l << 6 - 1;
        while (push != 0) {
            output |= push & endingSquareInt;
            push = (push >>> 1);
        }

        output = output << 3;
        push = 1l << 2;
        while (push != 0) {
            output |= push & endingPiece;
            push = (push >>> 1);
        }

        output = output << 1;
        push = 1l;
        while (push != 0) {
            output |= push & endingColor;
            push = (push >>> 1);
        }

        output = output << 6;
        push = 1l << 5;
        while (push != 0) {
            output |= push & enPassantSquareOffset; // if enPassantSquare is 0, then enPassantSquareOffset gets encoded
                                                    // as 64, but only 5 bits, so get set as 0 in encodedMove
            push = (push >>> 1);
        }

        output = output << 2;
        push = 1l << 1;
        while (push != 0) {
            output |= push & flag;
            push = (push >>> 1);
        }

        output = output << 32;
        push = 1l << 31;
        while (push != 0) {
            output |= push & halfMoveCount;
            push = (push >>> 1);
        }

        for (int color : COLORS) {
            for (int side : SIDES) {
                output = output << 1;
                push = 1l;
                while (push != 0) {
                    output |= push & (castleRights[color][side] ? 1l : 0l);
                    push = (push >>> 1);
                }
            }
        }

        return output;
    }

    public void setHash() {
        int index = 0;
        for (; index < 64; index++) {
            long square = FILES[index % 8] & RANKS[index / 8];
            hash[index] = PIECE_CHARS_ASCII[getColor(square)][getPiece(square)]; // 0-63
            index++;
        }
        hash[index] = (turn == WHITE) ? 'w' : 'b'; // 64
        index++;
        for (int color : COLORS) {
            for (int side : SIDES) {
                hash[index] = castleRights[color][side] ? 'T' : 'F'; // 65-69
                index++;
            }
        }
        if (enPassantSquare == 0) {
            hash[index] = '-'; // 70
            index++;
            hash[index] = '-'; // 71
            index++;
        } else {
            hash[index] = (char) ('a' + getFileIndex(enPassantSquare)); // 70
            index++;
            hash[index] = (char) ('1' + getRankIndex(enPassantSquare)); // 71
            index++;
        }
    }

    public void updateHash(short move) {
        long start = getStartingSquare(move);
        long end = getEndingSquare(move);
        byte promotion = getPromotion(move);
        int startColor = getColor(start);
        int endColor = getColor(end);
        switch (getFlag(move)) {
            case FLAG_PROMOTION:
                hash[getIndex(start)] = ' ';
                hash[getIndex(end)] = PIECE_CHARS_ASCII[startColor][promotion];
                break;
            case FLAG_EN_PASSANT:
                long captureSquare = Chess.s(end, end - start > 0 ? 1 : -1);
                hash[getIndex(start)] = ' ';
                hash[getIndex(end)] = PIECE_CHARS_ASCII[turn][PAWN];
                hash[getIndex(captureSquare)] = ' ';
                break;
            case FLAG_CASTLE:
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
                hash[getIndex(start)] = ' ';
                hash[getIndex(end)] = ' ';
                hash[getIndex(mainEndSquare)] = PIECE_CHARS_ASCII[startColor][KING];
                hash[getIndex(helperEndSquare)] = PIECE_CHARS_ASCII[startColor][ROOK];
                break;
            case FLAG_STANDARD:
                int startPiece = getPiece(start);
                hash[getIndex(start)] = ' ';
                hash[getIndex(end)] = PIECE_CHARS_ASCII[startColor][startPiece];
                break;
            default:
                break;
        }
        int index = 64;
        hash[index] = (turn == WHITE) ? 'w' : 'b'; // 64
        index++;
        for (int color : COLORS) {
            for (int side : SIDES) {
                hash[index] = castleRights[color][side] ? 'T' : 'F'; // 65-69
                index++;
            }
        }
        if (enPassantSquare == 0) {
            hash[index] = '-'; // 70
            index++;
            hash[index] = '-'; // 71
            index++;
        } else {
            hash[index] = (char) ('a' + getFileIndex(enPassantSquare)); // 70
            index++;
            hash[index] = (char) ('1' + getRankIndex(enPassantSquare)); // 71
            index++;
        }
    }

    public String getMoveString(short move) {
        long startingSquare = getStartingSquare(move);
        long endingSquare = getEndingSquare(move);
        int flag = getFlag(move);
        if (flag == FLAG_CASTLE) {
            if (FILES[getFileIndex(endingSquare)] == ROOK_STARTING_FILES[KINGSIDE]) {
                endingSquare = e(e(startingSquare));
            } else {
                endingSquare = w(w(startingSquare));
            }
        }
        char startingFile = (char) ('a' + getFileIndex(startingSquare));
        char startingRank = (char) ('1' + getRankIndex(startingSquare));
        char endingFile = (char) ('a' + getFileIndex(endingSquare));
        char endingRank = (char) ('1' + getRankIndex(endingSquare));
        String output = "" + startingFile + startingRank + endingFile + endingRank;
        if (flag == FLAG_PROMOTION) {
            output += PIECE_CHARS_ASCII[BLACK][getPromotion(move)];
        }
        return output;
    }

    public long getBackRank(int color) {
        return (color == BLACK) ? RANK_8 : RANK_1;
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

    public void replace(long square, int color, int piece) {
        add(square, color, piece);
    }

    public void add(long square, int color, int piece) {
        remove(square);
        if (piece != EMPTY) {
            combinedBoards[color] |= square; // add piece to combinedBoard

            pieceBoards[color][piece] |= square; // add piece to board
        }
    }

    public void remove(long square) {
        int priorPiece = getPiece(square);
        int priorColor = getColor(square);
        if (priorPiece != EMPTY) {
            combinedBoards[priorColor] &= ~square; // remove prior piece from combinedBoard if its not EMPTY

            pieceBoards[priorColor][priorPiece] &= ~square; // remove prior piece from board
        }
    }

    public int getPiece(long square) {
        int color = getColor(square);
        for (int piece : PIECES) {
            if ((pieceBoards[color][piece] & square) == square) {
                return piece;
            }
        }
        return EMPTY;
    }

    public int getColor(long square) {
        return (combinedBoards[BLACK] & square) == square ? BLACK : WHITE;
    }

    public static short encodeMove(long start, long end, int promotion, int flag) {
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

    public long getEmpty() {
        return ~(combinedBoards[WHITE] | combinedBoards[BLACK]);
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

    public void setFromFen(String fen) throws Exception {
        fen = fen.trim();
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
        if (stringIndex == fen.length() - 1) {
            halfMoveCount = 0;
            fullMoveCount = 1;
        } else {
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
        }
        reversibleMoves.clear();
        hashList.clear();
        setHash();
        hashList.add(new String(hash));
        getLegalMoves(legalMoves);
        if (isValidBoardState()) {
            return;
        } else {
            throw new Exception("Not a valid fen.");
        }
    }

    public boolean isValidBoardState() {
        if (Long.bitCount(pieceBoards[WHITE][KING]) != 1) { // exactly one white king
            return false;
        }
        if (Long.bitCount(pieceBoards[BLACK][KING]) != 1) { // exactly one black king
            return false;
        }
        if (((RANK_1 | RANK_8) & (pieceBoards[WHITE][PAWN] | pieceBoards[WHITE][PAWN])) != 0) { // no pawns on back rank
            return false;
        }
        for (int color : COLORS) {
            long backRank = getBackRank(color);
            long kingStartingSquare = backRank & E_FILE;
            for (int side : SIDES) {
                if (castleRights[color][side]) {
                    if (pieceBoards[color][KING] != kingStartingSquare) {
                        return false;
                    }
                    if ((pieceBoards[color][ROOK] & (backRank & ROOK_STARTING_FILES[side])) == 0) {
                        return false;
                    }
                }
            }
        }
        if (enemyInCheck()) {
            return false;
        }
        return true;
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

    public int getColorFromChar(char c) {
        if (c == ' ') {
            return WHITE;
        }
        return Character.isUpperCase(c) ? WHITE : BLACK;
    }

    public static int getIndex(long square) {
        return Long.numberOfTrailingZeros(square);
    }

    public static int getRankIndex(long square) {
        int zeroes = Long.numberOfTrailingZeros(square);
        return (zeroes / 8);
    }

    public static int getFileIndex(long square) {
        int zeroes = Long.numberOfTrailingZeros(square);
        return (zeroes % 8);
    }

    public static long getBitboard(int index) {
        return 1l >> index;
    }

    private long perft(int depth) throws Exception {
        Set<Short> moves = getLegalMoves();
        if (depth == 1) {
            return moves.size();
        }
        long nodes = 0;
        for (short move : moves) {
            moveNoUpdate(move);
            nodes += perft(depth - 1);
            undoNoUpdate();
        }
        return nodes;
    }

    public long perft(int depth, boolean verbose) throws Exception {
        Map<String, Long> map = perftMap(depth);
        if (verbose) {
            for (String key : map.keySet()) {
                System.out.println(key + ": " + map.get(key));
            }
        }
        return map.get("total");
    }

    public Map<String, Long> perftMap(int depth) throws Exception {
        Map<String, Long> map = new TreeMap<>();
        long nodes = 0;
        Set<Short> moves = getLegalMoves();
        if (depth == 1) {
            for (short move : moves) {
                map.put(getMoveString(move), 1l);
            }
            map.put("total", (long) moves.size());
            return map;
        }
        for (short move : moves) {
            moveNoUpdate(move);
            long divide = perft(depth - 1);
            nodes += divide;
            undoNoUpdate();
            map.put(getMoveString(move), divide);
        }
        map.put("total", nodes);
        return map;
    }

    public static long perft(String fen, int depth, boolean verbose, String... moves) throws Exception {
        Map<String, Long> map = Chess.perftMap(fen, depth, moves);
        if (verbose) {
            for (String key : map.keySet()) {
                System.out.println(key + ": " + map.get(key));
            }
        }
        return map.get("total");
    }

    public static Map<String, Long> perftMap(String fen, int depth, String... moves) throws Exception {
        Chess game = new Chess(fen);
        game.move(moves);
        return game.perftMap(depth);
    }

    public boolean isLegalMove(String move) {
        for (short moveShort : legalMoves) {
            if (getMoveString(moveShort).equals(move)) {
                return true;
            }
        }
        return false;
    }

    // Methods for pushing bitboards around, will not overflow bits around the board
    public static long n(long input) {
        return (input & ~RANK_8) << 8;
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

    public static long n(long input, int count) {
        return (input & horizontalMoves[count]) << (8 * count);
    }

    public static long s(long input, int count) {
        return (input & ~horizontalMoves[8 - count]) >>> (8 * count);
    }

    public static long e(long input, int count) {
        return (input & ~verticalMoves[8 - count]) << count;
    }

    public static long w(long input, int count) {
        return (input & verticalMoves[count]) >>> count;
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

    public static long compass(long input, int north, int east) {
        return east >= 0 ? e(north >= 0 ? n(input, north) : s(input, -north), east)
                : w(north >= 0 ? n(input, north) : s(input, -north), -east);
    }

    // Print Board Methods
    public void print(boolean ascii, boolean flipped) {
        if (ascii) {
            printSimple(flipped);
        } else {
            print(flipped);
        }
    }

    public void print() {
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

    public void printLegalMoves() throws Exception {
        getLegalMoves(legalMoves);
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

    public void printPseudoLegalMoves() throws Exception {
        getPseudoLegalMoves(pseudoLegalMoves.get(turn));
        System.out.print("[");
        boolean flag = false;
        for (short move : pseudoLegalMoves.get(turn)) {
            if (flag) {
                System.out.print(", ");
            }
            flag = true;
            System.out.print(getMoveString(move) + "");
        }

        System.out.println("]");
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

    public String getMoveStringReversible(long input) {
        int endingSquareOffset = 0;
        int startingSquareOffset = 0;
        long endingSquare = 0l;
        long startingSquare = 0l;
        long push = 0;

        input = (input >>> 48);
        push = 1l << 5;
        while (push != 0) {
            endingSquareOffset |= push & input;
            push = (push >>> 1);
        }
        endingSquare = 1L << endingSquareOffset;
        input = (input >>> 10);
        push = 1l << 5;
        while (push != 0) {
            startingSquareOffset |= push & input;
            push = (push >>> 1);
        }
        startingSquare = 1l << startingSquareOffset;
        char startingFile = (char) ('a' + getFileIndex(startingSquare));
        char startingRank = (char) ('1' + getRankIndex(startingSquare));
        char endingFile = (char) ('a' + getFileIndex(endingSquare));
        char endingRank = (char) ('1' + getRankIndex(endingSquare));
        return "" + startingFile + startingRank + endingFile + endingRank;
    }

    public void printPastMoves() {
        System.out.print("[");
        for (long move : reversibleMoves) {
            System.out.print(getMoveStringReversible(move) + ",");
        }
        System.out.println("]");

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
}