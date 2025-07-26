package model;

import java.util.ArrayList;
import java.util.List;
import model.exceptions.MoveMismatchException;
import model.misc_vars.Colour;
import model.misc_vars.MoveTag;
import model.move_tools.Move;
import model.pieces.*;

public class Board {

    private Piece[][] board;
    List<Piece> whitePieces;
    List<Piece> blackPieces;

    public Board() {
        initializeBoard();
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    private void initializeBoard() {

        board = new Piece[8][8];
        // Initialize white pieces
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();

        Rook whiteRook1 = new Rook(Colour.WHITE, 0, 0);
        Knight whiteKnight1 = new Knight(Colour.WHITE, 1, 0);
        Bishop whiteBishop1 = new Bishop(Colour.WHITE, 2, 0);
        Queen whiteQueen = new Queen(Colour.WHITE, 3, 0);
        King whiteKing = new King(Colour.WHITE, 4, 0);
        Bishop whiteBishop2 = new Bishop(Colour.WHITE, 5, 0);
        Knight whiteKnight2 = new Knight(Colour.WHITE, 6, 0);
        Rook whiteRook2 = new Rook(Colour.WHITE, 7, 0);

        board[0][0] = whiteRook1;
        board[0][1] = whiteKnight1;
        board[0][2] = whiteBishop1;
        board[0][3] = whiteQueen;
        board[0][4] = whiteKing;
        board[0][5] = whiteBishop2;
        board[0][6] = whiteKnight2;
        board[0][7] = whiteRook2;

        whitePieces.add(whiteRook1);
        whitePieces.add(whiteKnight1);
        whitePieces.add(whiteBishop1);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);
        whitePieces.add(whiteBishop2);
        whitePieces.add(whiteKnight2);
        whitePieces.add(whiteRook2);

        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(Colour.WHITE, i, 1);
            board[1][i] = whitePawn;
            whitePieces.add(whitePawn);
        }

        // Initialize black pieces
        Rook blackRook1 = new Rook(Colour.BLACK, 0, 7);
        Knight blackKnight1 = new Knight(Colour.BLACK, 1, 7);
        Bishop blackBishop1 = new Bishop(Colour.BLACK, 2, 7);
        Queen blackQueen = new Queen(Colour.BLACK, 3, 7);
        King blackKing = new King(Colour.BLACK, 4, 7);
        Bishop blackBishop2 = new Bishop(Colour.BLACK, 5, 7);
        Knight blackKnight2 = new Knight(Colour.BLACK, 6, 7);
        Rook blackRook2 = new Rook(Colour.BLACK, 7, 7);

        board[7][0] = blackRook1;
        board[7][1] = blackKnight1;
        board[7][2] = blackBishop1;
        board[7][3] = blackQueen;
        board[7][4] = blackKing;
        board[7][5] = blackBishop2;
        board[7][6] = blackKnight2;
        board[7][7] = blackRook2;

        blackPieces.add(blackRook1);
        blackPieces.add(blackKnight1);
        blackPieces.add(blackBishop1);
        blackPieces.add(blackQueen);
        blackPieces.add(blackKing);
        blackPieces.add(blackBishop2);
        blackPieces.add(blackKnight2);
        blackPieces.add(blackRook2);

        for (int i = 0; i < 8; i++) {
            Pawn blackPawn = new Pawn(Colour.BLACK, i, 6);
            board[6][i] = blackPawn;
            blackPieces.add(blackPawn);
        }
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    public void resetBoard() {
        initializeBoard();
    }

    public Piece[][] getBoard() {
        return board;
    }

    public Piece getSquare(int x, int y) {
        return board[y][x];
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

    // REQUIRES: move is valid
    // MODIFIES: board
    // EFFECTS: Moves a piece from the initial square (ix, iy) to the final square
    // (fx, fy)
    public boolean move(Move move) throws MoveMismatchException {

        int ix = move.PIECE.getX();
        int iy = move.PIECE.getY();
        int fx = move.X;
        int fy = move.Y;

        if (move.MOVE_TAGS.contains(MoveTag.CAPTURE) && board[fy][fx] == null) {
            throw new MoveMismatchException("Capture move attempted on an empty square.");
        }

        if (move.MOVE_TAGS.contains(MoveTag.CHECK) && !isCheck(move)) {
            throw new MoveMismatchException("Check move attempted when not putting the opposing king in check.");
        }

        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;

        board[fy][fx].setPosition(fx, fy);

        return true;
    }

    // TODO: remove later; temp method for testing
    public boolean move(Piece piece, int fx, int fy) {

        board[piece.getY()][piece.getX()] = null;
        board[fy][fx] = piece;

        board[fy][fx].setPosition(fx, fy);

        return true;
    }

    // REQUIRES: colour is either Colour.WHITE or Colour.BLACK
    // MODIFIES: board
    // EFFECTS: Resets the en passant flags for all pawns on the board
    public void resetEnPassantFlags(Colour colour) {
        if (colour != Colour.WHITE && colour != Colour.BLACK) {
            throw new IllegalArgumentException("Colour must be either WHITE or BLACK");
        }

        List<Piece> pieceList = colour == Colour.WHITE ? whitePieces : blackPieces;

        for (Piece piece : pieceList) {
            if (piece instanceof Pawn pawn) {
                pawn.resetEnPassantFlag();
            }
        }
    }

    // REQUIRES: move.getMoveTags() contains MoveTag.CHECK
    // EFFECTS: Checks if the move puts the opposing king in check
    private boolean isCheck(Move move) {
        int fx = move.X;
        int fy = move.Y;
        // TODO: Implement logic to check if the move puts the opposing king in check
        throw new UnsupportedOperationException("isCheck method not implemented yet.");
    }

    // REQUIRES: board != null
    // MODIFIES: board
    // EFFECTS: Returns a deep copy of the board
    public Board copy() {
        Board copy = new Board();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board[y][x];
                if (piece != null) {
                    copy.board[y][x] = piece.copy();
                }
            }
        }
        return copy;
    }
}