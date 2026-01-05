package model.move_tools;

import java.util.ArrayList;
import model.misc_vars.Colour;
import model.pieces.Piece;

// Manages the board state, such as check, checkmate, and stalemate.
// Stores information about enpassant, castling rights, etc.
public class BoardState {

    private static final int WK = 1 << 0;
    private static final int WQ = 1 << 1;
    private static final int BK = 1 << 2;
    private static final int BQ = 1 << 3;

    private Board board;
    private Colour currentTurn;
    private Position enpassantTarget;

    private int castlingRights;
    private int halfMoveClock;
    private ArrayList<Move> moveHistory;

    // REQUIRES: board is not null
    // EFFECTS: initializes the board state
    public BoardState() {
        board = new Board();
        currentTurn = Colour.WHITE;
        enpassantTarget = null;

        castlingRights = WK | WQ | BK | BQ;
        halfMoveClock = 0;

        moveHistory = new ArrayList<>();
    }

    // REQUIRES: board is not null
    // EFFECTS: initializes the board state with the given parameters
    public BoardState(Board board) {
        this.board = board;
        currentTurn = Colour.WHITE;
        enpassantTarget = null;

        castlingRights = WK | WQ | BK | BQ;
        halfMoveClock = 0;

        moveHistory = new ArrayList<>();
    }

    public boolean isCheckmate(Colour colour) { throw new UnsupportedOperationException("Not implemented yet"); }

    public boolean isStalemate(Colour colour) { throw new UnsupportedOperationException("Not implemented yet"); }

    public boolean isInCheck(Colour colour) { throw new UnsupportedOperationException("Not implemented yet"); }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns the piece at the specified position, or null if the square
    // is empty
    public Piece getSquare(Position pos) { return board.getSquare(pos); }

    // MODIFIES: board, moveHistory
    // EFFECTS: executes the given move on the board and updates the move history
    public void executeMove(Move move) {
        board.executeMove(move);
        moveHistory.add(move);
    }

    // MODIFIES: board, moveHistory
    // EFFECTS: undoes the most recent move on the board and updates the move
    // history
    public void undoMove() {
        if (!moveHistory.isEmpty()) {
            Move move = moveHistory.get(moveHistory.size() - 1);
            board.undoMove(move);
            moveHistory.remove(moveHistory.size() - 1);
        }
    }

    public Board getBoard() { return board; }

    public void setBoard(Board board) { this.board = board; }

    public Colour getCurrentTurn() { return currentTurn; }

    public void setCurrentTurn(Colour currentTurn) { this.currentTurn = currentTurn; }

    public Position getEnpassantTarget() { return enpassantTarget; }

    public void setEnpassantTarget(Position enpassantTarget) { this.enpassantTarget = enpassantTarget; }

    public int getCastlingRights() { return castlingRights; }

    public void setCastlingRights(int castlingRights) { this.castlingRights = castlingRights; }

    public int getHalfMoveClock() { return halfMoveClock; }

    public void setHalfMoveClock(int halfMoveClock) { this.halfMoveClock = halfMoveClock; }

    public ArrayList<Move> getMoveHistory() { return moveHistory; }

    public void setMoveHistory(ArrayList<Move> moveHistory) { this.moveHistory = moveHistory; }
}
