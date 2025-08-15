package model;

import java.util.ArrayList;
import java.util.List;

import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;
import model.pieces.*;

public class Board {

    private Piece[][] board;
    List<Piece> whitePieces;
    List<Piece> blackPieces;

    public Board() { initializeBoard(); }

    public Board(Board board) {
        this.board = new Piece[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board.getSquare(new Position(x, y));
                this.board[y][x] = piece != null ? piece.copy() : null;
            }
        }

        for (Piece piece : board.whitePieces) {
            this.whitePieces.add(piece.copy());
        }
        for (Piece piece : board.blackPieces) {
            this.blackPieces.add(piece.copy());
        }
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    private void initializeBoard() {

        board = new Piece[8][8];
        // Initialize white pieces
        whitePieces = new ArrayList<>();
        blackPieces = new ArrayList<>();

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

        whitePieces.add(whiteRook1);
        whitePieces.add(whiteKnight1);
        whitePieces.add(whiteBishop1);
        whitePieces.add(whiteQueen);
        whitePieces.add(whiteKing);
        whitePieces.add(whiteBishop2);
        whitePieces.add(whiteKnight2);
        whitePieces.add(whiteRook2);

        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(Colour.WHITE, new Position(i, 1));
            board[1][i] = whitePawn;
            whitePieces.add(whitePawn);
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

        blackPieces.add(blackRook1);
        blackPieces.add(blackKnight1);
        blackPieces.add(blackBishop1);
        blackPieces.add(blackQueen);
        blackPieces.add(blackKing);
        blackPieces.add(blackBishop2);
        blackPieces.add(blackKnight2);
        blackPieces.add(blackRook2);

        for (int i = 0; i < 8; i++) {
            Pawn blackPawn = new Pawn(Colour.BLACK, new Position(i, 6));
            board[6][i] = blackPawn;
            blackPieces.add(blackPawn);
        }
    }

    public List<Piece> getWhitePieces() { return whitePieces; }

    public List<Piece> getBlackPieces() { return blackPieces; }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    public void resetBoard() { initializeBoard(); }

    public Piece[][] getBoard() { return board; }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns the piece at the specified position, or null if the square
    // is
    public Piece getSquare(Position pos) { return board[pos.getY()][pos.getX()]; }

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

    // REQUIRES: move is legal and MoveType is not null
    // MODIFIES: board
    // EFFECTS: Moves a piece from the initial square (ix, iy) to the final square
    // (fx, fy)
    public void move(Move move) {
        // Check if the move is a castling move
        if (move.getMoveType() == MoveType.NORMAL) {
            handleNormalMove(move);
        }
        else if (move.getMoveType() == MoveType.KINGSIDE_CASTLE
                || move.getMoveType() == MoveType.QUEENSIDE_CASTLE) {
            handleCastling(move);
        }
        else if (move.getMoveType() == MoveType.EN_PASSANT) {
            handleEnPassant(move);
        }
        else if (move.getMoveType() == MoveType.PROMOTION) {
            handlePromotion(move);
        }
        else {
            throw new IllegalArgumentException("Illegal move type: " + move.getMoveType());
        }

        if (move.isCapture()) {
            handleCapture(move);
        }
    }

    // REQUIRES: move.getMoveType() is MoveType.NORMAL
    // MODIFIES: board
    // EFFECTS: Handles normal moves for the specified move with no captures
    private void handleNormalMove(Move move) {
        int ix = move.getPiece().getX();
        int iy = move.getPiece().getY();
        int fx = move.getTargetX();
        int fy = move.getTargetY();

        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;

        board[fy][fx].setPos(new Position(fx, fy));
    }

    // TODO: add method to handle castling
    // REQUIRES: move.getMoveType() is MoveTag.KINGSIDE_CASTLE or
    // MoveTag.QUEENSIDE_CASTLE and casting is valid
    // MODIFIES: board
    // EFFECTS: Handles castling moves for the specified move
    private void handleCastling(Move move) {
        
    }

    // TODO: add method to handle en passant
    // REQUIRES: move.getMoveTags() contains MoveTag.EN_PASSANT
    // MODIFIES: board
    // EFFECTS: Handles en passant moves for the specified move
    private void handleEnPassant(Move move) {
        int fx = move.getTargetX();
        int fy = move.getTargetY();
        int ix = move.getPiece().getX();
        int iy = move.getPiece().getY();

        // Remove the captured pawn
        if (move.getPiece().getColour() == Colour.WHITE) {
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
    // REQUIRES: move.getMoveTags() contains MoveTag.PROMOTION
    // MODIFIES: board
    // EFFECTS: Handles promotion moves for the specified move
    private void handlePromotion(Move move) {
        int fx = move.getTargetX();
        int fy = move.getTargetY();
        Piece piece = move.getPiece();

        // Remove the pawn from the board
        board[fy][fx] = null;

        // Create a new piece based on the promotion type
        if (move.getMoveType() == MoveType.PROMOTION) {
            board[move.getPiece().getColour() == Colour.WHITE ? 7 : 0][fx] = move.getAltPiece();
            // Promote to the specified piece
        }
    }

    // REQUIRES: move.getMoveTags() contains MoveTag.CAPTURE
    // MODIFIES: board
    // EFFECTS: handles captures but does not move the piece
    private void handleCapture(Move move) {
        int fx = move.getTargetX();
        int fy = move.getTargetY();
        Piece capturedPiece = board[fy][fx];

        if (capturedPiece != null) {
            // Remove the captured piece from the board
            if (capturedPiece.getColour() == Colour.WHITE) {
                whitePieces.remove(capturedPiece);
            }
            else {
                blackPieces.remove(capturedPiece);
            }
        }
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

    // TODO: isCheck logic needs to be implemented
    // REQUIRES: move.getMoveTags() contains MoveTag.CHECK
    // EFFECTS: Checks if the move puts the opposing king in check
    public boolean inCheck(Colour colour) {
        return false; // TODO: complete inCheck logic
    }

    // TODO: isCheckmate logic needs to be implemented
    // EFFECTS: Checks if the game is in checkmate for the specified colour
    private boolean isCheckmate(Colour colour) { // TODO: complete isCheckmate logic
        for (Piece piece : (colour == Colour.WHITE ? whitePieces : blackPieces)) {
            for (Move move : piece.validMoves(this)) {
                Board newBoard = new Board(this); // Create a copy of the board
                newBoard.move(move); // Simulate the move

                if (!newBoard.inCheck(colour)) {
                    return false; // If any move does not result in check, it's not checkmate
                }
            } // TODO: fix for pawns
        }
        return true;
    }

    // TODO: isDraw logic needs to be implemented
    // EFFECTS: Checks if the game is a draw for the specified colour
    public boolean isDraw(Colour colour) {
        if (isCheckmate(colour)) {
            return false; // If it's checkmate, it's not a draw
        }

        List<Piece> pieceList = colour == Colour.WHITE ? whitePieces : blackPieces;
        for (Piece piece : pieceList) {
            if (!piece.validMoves(this).isEmpty()) {
                return false; // If any piece has valid moves, it's not a draw
            }
        }

        return true; // No valid moves for any piece, it's a draw
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