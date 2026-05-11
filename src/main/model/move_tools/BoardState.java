package model.move_tools;

import model.exceptions.ImpossibleMoveStateException;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.Piece;

// Manages the board state, such as check, checkmate, and stalemate.
// Stores information about enpassant, castling rights, etc.
public class BoardState {

    private static final byte WK = 1 << 0;
    private static final byte WQ = 1 << 1;
    private static final byte BK = 1 << 2;
    private static final byte BQ = 1 << 3;

    private Board board;

    private Colour currentTurn;
    private Position enpassantTarget;
    private byte castlingRights; // bit field
    private int halfMoveClock;

    // REQUIRES: board is not null
    // EFFECTS: initializes the board state
    public BoardState() {
        board = new Board();
        currentTurn = Colour.WHITE;
        enpassantTarget = null;

        castlingRights = WK | WQ | BK | BQ;
        halfMoveClock = 0;
    }

    // REQUIRES: board is not null
    // EFFECTS: initializes the board state with the given parameters
    public BoardState(Board board) {
        this.board = board;
        currentTurn = Colour.WHITE;
        enpassantTarget = null;

        castlingRights = WK | WQ | BK | BQ;
        halfMoveClock = 0;
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
    public void executeMove(Move move, boolean ignoreCheck) throws ImpossibleMoveStateException {
        MoveType moveType = move.MOVE_TYPE;
        switch (moveType) {
        case NORMAL -> handleNormalMove(move);
        case EN_PASSANT -> handleEnPassant(move);
        case CASTLING -> handleCastling(move);
        case PROMOTION -> handlePromotion(move);
        default -> throw new IllegalArgumentException("Unexpected value: " + moveType);
        }
    }

    // TODO: implement normalmove
    // REQUIRES: move.MOVETYPE == MoveType.NORMAL
    // MODIFIES: board
    // EFFECTS: Handles normal moves for the specified move with no captures
    private void handleNormalMove(Move move) {

    }

    // TODO: implement castling
    // REQUIRES: move.MOVETYPE is a castling move
    // MODIFIES: board
    // EFFECTS: Handles castling moves for the specified move
    private void handleCastling(Move move) {

    }

    // TODO: add method to handle en passant
    // REQUIRES: move.getMoveTags == MoveTag.EN_PASSANT
    // MODIFIES: board
    // EFFECTS: Handles en passant moves for the specified move
    private void handleEnPassant(Move move) {

    }

    // TODO: handle promotion, add pieces to list
    // REQUIRES: move.getMoveTag == MoveTag.PROMOTION and move is legal
    // MODIFIES: board
    // EFFECTS: Handles promotion moves for the specified move
    private void handlePromotion(Move move) {

    }

    public Board getBoard() { return board; }

    public void setBoard(Board board) { this.board = board; }

    public Colour getCurrentTurn() { return currentTurn; }

    public void setCurrentTurn(Colour currentTurn) { this.currentTurn = currentTurn; }

    public Position getEnpassantTarget() { return enpassantTarget; }

    public void setEnpassantTarget(Position enpassantTarget) { this.enpassantTarget = enpassantTarget; }

    public byte getCastlingRights() { return castlingRights; }

    public void setCastlingRights(byte castlingRights) { this.castlingRights = castlingRights; }

    public int getHalfMoveClock() { return halfMoveClock; }

    public void setHalfMoveClock(int halfMoveClock) { this.halfMoveClock = halfMoveClock; }
}
