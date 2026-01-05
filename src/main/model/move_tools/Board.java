package model.move_tools;

import model.misc_vars.Colour;
import model.pieces.*;

public class Board {

    private Piece[][] board;

    public Board() { initializeBoard(); }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    private void initializeBoard() {

        board = new Piece[8][8];

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
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    public void resetBoard() { initializeBoard(); }

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