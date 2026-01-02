package model.move_tools;

import java.util.HashMap;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.*;

public class Board {

    private Piece[][] board;
    private Integer[][] previousBoard;
    private HashMap<Integer, Piece> pieces;

    public Board() { initializeBoard(); }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    private void initializeBoard() {

        board = new Piece[8][8];
        previousBoard = null;
        pieces = new HashMap<>();
        // Initialize white pieces

        Rook whiteRook1 = new Rook(Colour.WHITE, new Position(0, 0));
        Knight whiteKnight1 = new Knight(Colour.WHITE, new Position(1, 0));
        Bishop whiteBishop1 = new Bishop(Colour.WHITE, new Position(2, 0));
        Queen whiteQueen = new Queen(Colour.WHITE, new Position(3, 0));
        King whiteKing = new King(Colour.WHITE, new Position(4, 0));
        Bishop whiteBishop2 = new Bishop(Colour.WHITE, new Position(5, 0));
        Knight whiteKnight2 = new Knight(Colour.WHITE, new Position(6, 0));
        Rook whiteRook2 = new Rook(Colour.WHITE, new Position(7, 0));

        board[0][0] = whiteRook1;
        board[0][1] = whiteKnight1;
        board[0][2] = whiteBishop1;
        board[0][3] = whiteQueen;
        board[0][4] = whiteKing;
        board[0][5] = whiteBishop2;
        board[0][6] = whiteKnight2;
        board[0][7] = whiteRook2;

        pieces.put(0, whiteRook1);
        pieces.put(1, whiteKnight1);
        pieces.put(2, whiteBishop1);
        pieces.put(3, whiteQueen);
        pieces.put(4, whiteKing);
        pieces.put(5, whiteBishop2);
        pieces.put(6, whiteKnight2);
        pieces.put(7, whiteRook2);

        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(Colour.WHITE, new Position(i, 1));
            Pawn blackPawn = new Pawn(Colour.BLACK, new Position(i, 6));
            board[1][i] = whitePawn;
            board[6][i] = blackPawn;
        }

        // Initialize black pieces
        Rook blackRook1 = new Rook(Colour.BLACK, new Position(0, 7));
        Knight blackKnight1 = new Knight(Colour.BLACK, new Position(1, 7));
        Bishop blackBishop1 = new Bishop(Colour.BLACK, new Position(2, 7));
        Queen blackQueen = new Queen(Colour.BLACK, new Position(3, 7));
        King blackKing = new King(Colour.BLACK, new Position(4, 7));
        Bishop blackBishop2 = new Bishop(Colour.BLACK, new Position(5, 7));
        Knight blackKnight2 = new Knight(Colour.BLACK, new Position(6, 7));
        Rook blackRook2 = new Rook(Colour.BLACK, new Position(7, 7));

        board[7][0] = blackRook1;
        board[7][1] = blackKnight1;
        board[7][2] = blackBishop1;
        board[7][3] = blackQueen;
        board[7][4] = blackKing;
        board[7][5] = blackBishop2;
        board[7][6] = blackKnight2;
        board[7][7] = blackRook2;

        pieces.put(8, blackRook1);
        pieces.put(9, blackKnight1);
        pieces.put(10, blackBishop1);
        pieces.put(11, blackQueen);
        pieces.put(12, blackKing);
        pieces.put(13, blackBishop2);
        pieces.put(14, blackKnight2);
        pieces.put(15, blackRook2);
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    public void resetBoard() { initializeBoard(); }

    public Piece[][] getBoard() { return board; }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns the piece at the specified position, or null if the square
    // is
    public Piece getSquare(Position pos) { return board[pos.Y][pos.X]; }

    @Override
    public String toString() {

        String result = "";
        result += "                  Chess\n";
        result += "    |---|---|---|---|---|---|---|---|\n";
        for (int y = 7; y >= 0; y--) {
            result += y + 1 + "   | ";
            for (int x = 0; x < 8; x++) {
                Piece currentPiece = board[y][x];
                if (currentPiece == null)
                    result += "  | ";
                else if (currentPiece.getColour() == Colour.BLACK)
                    result += currentPiece.getSymbol().toLowerCase() + " | ";
                else
                    result += currentPiece.getSymbol() + " | ";
            }
            result += "\n    |---|---|---|---|---|---|---|---|\n";
        }
        result += "\n      a   b   c   d   e   f   g   h\n\n";

        return result;
    }

    // MODIFIES: previousBoard
    // EFFECTS: creates a 2D Integer array representation of the current board
    private Integer[][] refBoard() {
        Integer[][] refBoard = new Integer[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board[y][x];
                if (piece == null) {
                    refBoard[y][x] = null;
                }
                else {
                    for (HashMap.Entry<Integer, Piece> entry : pieces.entrySet()) {
                        if (entry.getValue() == piece) {
                            refBoard[y][x] = entry.getKey();
                            break;
                        }
                    }
                }
            }
        }
        return refBoard;
    }

    // REQUIRES: move is legal and MoveType is not null
    // MODIFIES: board
    // EFFECTS: Moves a piece from the initial square (ix, iy) to the final square
    // (fx, fy)
    public void executeMove(Move move) {

        previousBoard = refBoard();

        switch (move.MOVETYPE) {
        case NORMAL -> handleNormalMove(move);
        case KINGSIDE_CASTLE, QUEENSIDE_CASTLE -> handleCastling(move);
        case EN_PASSANT -> handleEnPassant(move);
        case PROMOTION -> handlePromotion(move);
        default -> throw new IllegalArgumentException("Invalid move type: " + move.MOVETYPE);
        }
    }

    // REQUIRES: previousBoard != null
    // MODIFIES: board
    // EFFECTS: Reverts the board to the previous state before the last move
    public void undoMove() {
        if (previousBoard == null) {
            throw new IllegalStateException("No previous board state to revert to.");
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Integer pieceId = previousBoard[y][x];
                if (pieceId == null) {
                    board[y][x] = null;
                }
                else {
                    board[y][x] = pieces.get(pieceId);
                    if (board[y][x] != null) {
                        board[y][x].setPos(new Position(x, y));
                    }
                }
            }
        }

        previousBoard = null; // Clear previous board after undo
    }

    // REQUIRES: move.MOVETYPE == MoveType.NORMAL, move is legal
    // MODIFIES: board
    // EFFECTS: Repositions the piece on the board and updates its position
    private void repositionPiece(int ix, int iy, int fx, int fy) {
        Piece piece = board[iy][ix];
        board[fy][fx] = piece;
        board[iy][ix] = null;
        piece.setPos(new Position(fx, fy));
    }

    // REQUIRES: move.MOVETYPE == MoveType.NORMAL, move is legal
    // MODIFIES: board
    // EFFECTS: Handles normal moves for the specified move with no captures
    private void handleNormalMove(Move move) {
        int ix = move.PIECE.getX();
        int iy = move.PIECE.getY();
        int fx = move.POS.X;
        int fy = move.POS.Y;

        repositionPiece(ix, iy, fx, fy);
    }

    // REQUIRES: move.MOVETYPE == MoveTag.KINGSIDE_CASTLE or
    // MoveTag.QUEENSIDE_CASTLE and casting is legal
    // MODIFIES: board
    // EFFECTS: Handles castling moves for the specified move
    private void handleCastling(Move move) {
        int row = move.PIECE.getColour() == Colour.WHITE ? 0 : 7;
        if (null == move.MOVETYPE) {
            throw new IllegalArgumentException("Invalid castling move type: " + move.MOVETYPE);
        }
        else
            switch (move.MOVETYPE) {
            case KINGSIDE_CASTLE -> {
                repositionPiece(4, row, 6, row);
                repositionPiece(7, row, 5, row);
            }
            case QUEENSIDE_CASTLE -> {
                repositionPiece(4, row, 2, row);
                repositionPiece(0, row, 3, row);
            }
            default -> throw new IllegalArgumentException("Invalid castling move type: " + move.MOVETYPE);
            }

    }

    // TODO: add method to handle en passant
    // REQUIRES: move.getMoveTags == MoveTag.EN_PASSANT and move is legal
    // MODIFIES: board
    // EFFECTS: Handles en passant moves for the specified move
    private void handleEnPassant(Move move) {
        int ix = move.PIECE.getX();
        int iy = move.PIECE.getY();
        int fx = move.POS.X;
        int fy = move.POS.Y;

        // Remove the captured pawn
        if (move.PIECE.getColour() == Colour.WHITE) {
            board[fy - 1][fx] = null; // Capture the black pawn
        }
        else {
            board[fy + 1][fx] = null; // Capture the white pawn
        }

        // Move the piece to the target square
        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;

        board[fy][fx].setPos(new Position(fx, fy));
    }

    // TODO: handle promotion, add pieces to list
    // REQUIRES: move.getMoveTag == MoveTag.PROMOTION and move is legal
    // MODIFIES: board
    // EFFECTS: Handles promotion moves for the specified move
    private void handlePromotion(Move move) {
        int fx = move.POS.X;
        int fy = move.POS.Y;
        int ix = move.PIECE.getX();
        int iy = move.PIECE.getY();

        // Remove the pawn from the board
        board[iy][ix] = null;
        board[fy][fx] = null;

        // Create a new piece based on the promotion type
        if (move.MOVETYPE == MoveType.PROMOTION) {
            // board[move.PIECE.getColour() == Colour.WHITE ? 7 : 0][fx] =
            // move.getPromotePiece();
            // Promote to the specified piece
        }
    }
}