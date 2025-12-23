package model.move_tools;

import model.Board;
import model.misc_vars.Colour;
import model.pieces.Pawn;

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

    public Colour getCurrentTurn() { return currentTurn; }

    public void setCurrentTurn(Colour currentTurn) { this.currentTurn = currentTurn; }

    public Pawn getEnpassantTarget() { return enpassantTarget; }

    public void setEnpassantTarget(Pawn enpassantTarget) { this.enpassantTarget = enpassantTarget; }

    public boolean isWhiteCanCastle() { return whiteCanCastle; }

    public void setWhiteCanCastle(boolean whiteCanCastle) { this.whiteCanCastle = whiteCanCastle; }

    public boolean isBlackCanCastle() { return blackCanCastle; }

    public void setBlackCanCastle(boolean blackCanCastle) { this.blackCanCastle = blackCanCastle; }

    public boolean isCheckmate(Colour colour, Board board) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isStalemate(Colour colour, Board board) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isInCheck(Colour colour, Board board) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public BoardState clone() {
        BoardState newState = new BoardState();
        newState.board = this.board;
        newState.currentTurn = this.currentTurn;
        newState.enpassantTarget = this.enpassantTarget;
        newState.whiteCanCastle = this.whiteCanCastle;
        newState.blackCanCastle = this.blackCanCastle;
        return newState;
    }
}
