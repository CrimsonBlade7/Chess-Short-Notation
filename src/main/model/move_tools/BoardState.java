package model.move_tools;

import model.misc_vars.Colour;
import model.pieces.Pawn;
import model.pieces.Piece;

// Manages the board state, such as check, checkmate, and stalemate.
// Stores information about enpassant, castling rights, etc.
public class BoardState {

    private Board board;
    private Colour currentTurn;
    private Pawn enpassantTarget;
    private boolean whiteCanCastle;
    private boolean blackCanCastle;

    // REQUIRES: board is not null
    // EFFECTS: initializes the board state with the given parameters
    public BoardState() {
        board = new Board();
        currentTurn = Colour.WHITE;
        enpassantTarget = null;
        whiteCanCastle = true;
        blackCanCastle = true;
    }

    // REQUIRES: board is not null
    // EFFECTS: initializes the board state with the given parameters
    public BoardState(Board board) {
        this.board = board;
        currentTurn = Colour.WHITE;
        enpassantTarget = null;
        whiteCanCastle = true;
        blackCanCastle = true;
    }

    public Board getBoard() { return board; }

    public void setBoard(Board board) { this.board = board; }

    public Colour getCurrentTurn() { return currentTurn; }

    public void setCurrentTurn(Colour currentTurn) { this.currentTurn = currentTurn; }

    public Pawn getEnpassantTarget() { return enpassantTarget; }

    public void setEnpassantTarget(Pawn enpassantTarget) { this.enpassantTarget = enpassantTarget; }

    public boolean whiteCanCastle() { return whiteCanCastle; }

    public void setWhiteCanCastle(boolean whiteCanCastle) { this.whiteCanCastle = whiteCanCastle; }

    public boolean blackCanCastle() { return blackCanCastle; }

    public void setBlackCanCastle(boolean blackCanCastle) { this.blackCanCastle = blackCanCastle; }

    public boolean isCheckmate(Colour colour) { throw new UnsupportedOperationException("Not implemented yet"); }

    public boolean isStalemate(Colour colour) { throw new UnsupportedOperationException("Not implemented yet"); }

    public boolean isInCheck(Colour colour) { throw new UnsupportedOperationException("Not implemented yet"); }

    // REQUIRES: move != null && board != null
    // EFFECTS: returns true if the move results in a check state for the given colour
    public boolean isCheckMove(Colour colour, Move move) {
        BoardState newBoardState;
        try {
            newBoardState = this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        newBoardState.executeMove(move);
        return newBoardState.isInCheck(colour);
    }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns the piece at the specified position, or null if the square
    // is
    public Piece getSquare(Position pos) { return board.getSquare(pos); }

    public void executeMove(Move move) { board.executeMove(move); }

    @Override
    public BoardState clone() throws CloneNotSupportedException {
        BoardState newState = (BoardState) super.clone();
        newState.board = this.board;
        newState.currentTurn = this.currentTurn;
        newState.enpassantTarget = this.enpassantTarget;
        newState.whiteCanCastle = this.whiteCanCastle;
        newState.blackCanCastle = this.blackCanCastle;
        return newState;
    }
}
