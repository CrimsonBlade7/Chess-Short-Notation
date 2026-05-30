package model.move_tools;

import model.misc_vars.Colour;
import model.pieces.*;

public class Board {

    private Piece[][] board;

    // EFFECTS: creates a board with the standard chess starting position
    public Board() { initializeBoard(); }

    // REQUIRES: other != null
    // EFFECTS: creates a deep copy of other
    public Board(Board other) {
        board = new Piece[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                board[y][x] = copyPiece(other.board[y][x]);
            }
        }
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    private void initializeBoard() {

        board = new Piece[8][8];

        // Initialize white pieces
        Rook whiteRook1 = new Rook(Colour.WHITE);
        Knight whiteKnight1 = new Knight(Colour.WHITE);
        Bishop whiteBishop1 = new Bishop(Colour.WHITE);
        Queen whiteQueen = new Queen(Colour.WHITE);
        King whiteKing = new King(Colour.WHITE);
        Bishop whiteBishop2 = new Bishop(Colour.WHITE);
        Knight whiteKnight2 = new Knight(Colour.WHITE);
        Rook whiteRook2 = new Rook(Colour.WHITE);

        board[0][0] = whiteRook1;
        board[0][1] = whiteKnight1;
        board[0][2] = whiteBishop1;
        board[0][3] = whiteQueen;
        board[0][4] = whiteKing;
        board[0][5] = whiteBishop2;
        board[0][6] = whiteKnight2;
        board[0][7] = whiteRook2;

        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(Colour.WHITE);
            Pawn blackPawn = new Pawn(Colour.BLACK);
            board[1][i] = whitePawn;
            board[6][i] = blackPawn;
        }

        // Initialize black pieces
        Rook blackRook1 = new Rook(Colour.BLACK);
        Knight blackKnight1 = new Knight(Colour.BLACK);
        Bishop blackBishop1 = new Bishop(Colour.BLACK);
        Queen blackQueen = new Queen(Colour.BLACK);
        King blackKing = new King(Colour.BLACK);
        Bishop blackBishop2 = new Bishop(Colour.BLACK);
        Knight blackKnight2 = new Knight(Colour.BLACK);
        Rook blackRook2 = new Rook(Colour.BLACK);

        board[7][0] = blackRook1;
        board[7][1] = blackKnight1;
        board[7][2] = blackBishop1;
        board[7][3] = blackQueen;
        board[7][4] = blackKing;
        board[7][5] = blackBishop2;
        board[7][6] = blackKnight2;
        board[7][7] = blackRook2;
    }

    // MODIFIES: board
    // EFFECTS: removes all pieces from the board
    public void clearBoard() { board = new Piece[8][8]; }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    public void resetBoard() { initializeBoard(); }

    // EFFECTS: returns the backing board array
    public Piece[][] getBoard() { return board; }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns the piece at the specified position, or null
    public Piece getSquare(Position pos) { return board[pos.Y][pos.X]; }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // MODIFIES: board
    // EFFECTS: sets the square to the given piece
    public void setSquare(Piece piece, Position pos) {
        if (pos != null)
            board[pos.Y][pos.X] = piece;
    }

    // REQUIRES: startPos and endPos is within the bounds of the board (0 <= x, y <
    // 8)
    // board != null
    // MODIFIES: board
    // EFFECTS: moves unit on startPos to endPos and sets square at startPos to null
    public void moveSquare(Position startPos, Position endPos) {
        setSquare(board[startPos.Y][startPos.X], endPos);
        board[startPos.Y][startPos.X] = null;
    }

    // REQUIRES: piece is either null or a known concrete Piece subtype
    // EFFECTS: returns a new piece with the same type and colour, or null
    private Piece copyPiece(Piece piece) {
        if (piece == null)
            return null;
        Colour colour = piece.getColour();
        if (piece instanceof Pawn)
            return new Pawn(colour);
        if (piece instanceof Rook)
            return new Rook(colour);
        if (piece instanceof Knight)
            return new Knight(colour);
        if (piece instanceof Bishop)
            return new Bishop(colour);
        if (piece instanceof Queen)
            return new Queen(colour);
        if (piece instanceof King)
            return new King(colour);
        throw new IllegalArgumentException("Unknown piece type: " + piece.getClass());
    }

    // EFFECTS: returns a terminal-friendly display of the board
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
}
