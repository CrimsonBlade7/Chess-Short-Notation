package model.move_tools;

import java.util.ArrayList;
import java.util.List;
import model.exceptions.ImpossibleMoveStateException;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Rook;

// Manages board state: turn, check, checkmate, stalemate, en passant,
// castling rights, and half-move clock.
public class BoardState {

    public static final byte WK = 1 << 0;
    public static final byte WQ = 1 << 1;
    public static final byte BK = 1 << 2;
    public static final byte BQ = 1 << 3;

    private Board board;
    private Colour currentTurn;
    private Position enpassantTarget;
    private byte castlingRights;
    private int halfMoveClock;

    // EFFECTS: initializes a board state with the standard starting position
    public BoardState() {
        board = new Board();
        currentTurn = Colour.WHITE;
        enpassantTarget = null;
        castlingRights = WK | WQ | BK | BQ;
        halfMoveClock = 0;
    }

    // REQUIRES: board != null
    // EFFECTS: initializes a board state around the given board with white to move
    public BoardState(Board board) {
        this.board = board;
        currentTurn = Colour.WHITE;
        enpassantTarget = null;
        castlingRights = WK | WQ | BK | BQ;
        halfMoveClock = 0;
    }

    // REQUIRES: other != null
    // EFFECTS: initializes a deep copy of other
    public BoardState(BoardState other) {
        board = new Board(other.board);
        currentTurn = other.currentTurn;
        enpassantTarget = other.enpassantTarget == null
                ? null
                : new Position(other.enpassantTarget.X, other.enpassantTarget.Y);
        castlingRights = other.castlingRights;
        halfMoveClock = other.halfMoveClock;
    }

    // REQUIRES: colour != null
    // EFFECTS: returns true if colour's king is currently attacked
    public boolean isInCheck(Colour colour) {
        Position kingPos = findKing(colour);
        if (kingPos == null)
            return false;
        return MoveValidation.isSquareAttacked(this, kingPos, opposite(colour));
    }

    // REQUIRES: colour != null
    // EFFECTS: returns true if colour is in check and has no legal moves
    public boolean isCheckmate(Colour colour) {
        return isInCheck(colour) && MoveValidation.legalMoves(this, colour).isEmpty();
    }

    // REQUIRES: colour != null
    // EFFECTS: returns true if colour is not in check and has no legal moves
    public boolean isStalemate(Colour colour) {
        return !isInCheck(colour) && MoveValidation.legalMoves(this, colour).isEmpty();
    }

    // REQUIRES: pos is within the bounds of the board
    // EFFECTS: returns the piece at pos, or null if the square is empty
    public Piece getSquare(Position pos) { return board.getSquare(pos); }

    // REQUIRES: move != null
    // MODIFIES: this
    // EFFECTS: executes move and updates turn, clocks, en passant, and castling;
    // throws ImpossibleMoveStateException if move is illegal unless ignoreCheck is true
    public void executeMove(Move move, boolean ignoreCheck) throws ImpossibleMoveStateException {
        if (!ignoreCheck && !MoveValidation.isLegalMove(this, move))
            throw new ImpossibleMoveStateException("Illegal move");

        Piece captured = capturedPiece(move);
        enpassantTarget = null;

        switch (move.MOVE_TYPE) {
        case NORMAL -> handleNormalMove(move);
        case EN_PASSANT -> handleEnPassant(move);
        case CASTLING -> handleCastling(move);
        case PROMOTION -> handlePromotion(move);
        default -> throw new IllegalArgumentException("Unexpected value: " + move.MOVE_TYPE);
        }

        updateCastlingRights(move, captured);
        updateHalfMoveClock(move, captured);
        updateEnpassantTarget(move);
        currentTurn = opposite(currentTurn);
    }

    // REQUIRES: move.MOVE_TYPE == MoveType.NORMAL
    // MODIFIES: board
    // EFFECTS: moves the primary piece from its start square to its end square
    private void handleNormalMove(Move move) {
        board.moveSquare(move.START_POS_1, move.END_POS_1);
    }

    // REQUIRES: move.MOVE_TYPE == MoveType.CASTLING
    // MODIFIES: board
    // EFFECTS: moves the king and rook to their castled squares
    private void handleCastling(Move move) {
        board.moveSquare(move.START_POS_1, move.END_POS_1);
        board.moveSquare(move.START_POS_2, move.END_POS_2);
    }

    // REQUIRES: move.MOVE_TYPE == MoveType.EN_PASSANT
    // MODIFIES: board
    // EFFECTS: moves the pawn diagonally and removes the captured pawn
    private void handleEnPassant(Move move) {
        board.moveSquare(move.START_POS_1, move.END_POS_1);
        board.setSquare(null, move.START_POS_2);
    }

    // REQUIRES: move.MOVE_TYPE == MoveType.PROMOTION
    // MODIFIES: board
    // EFFECTS: replaces the moving pawn with its promotion piece
    private void handlePromotion(Move move) {
        board.setSquare(null, move.START_POS_1);
        board.setSquare(move.PROMOTION_PIECE, move.END_POS_1);
    }

    // REQUIRES: colour != null
    // EFFECTS: returns all board positions containing pieces of colour
    public List<Position> positionsFor(Colour colour) {
        List<Position> positions = new ArrayList<>();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Position pos = new Position(x, y);
                Piece piece = getSquare(pos);
                if (piece != null && piece.getColour() == colour)
                    positions.add(pos);
            }
        }
        return positions;
    }

    // REQUIRES: colour != null
    // EFFECTS: returns the position of colour's king, or null if absent
    public Position findKing(Colour colour) {
        for (Position pos : positionsFor(colour)) {
            if (getSquare(pos) instanceof King)
                return pos;
        }
        return null;
    }

    // REQUIRES: colour != null
    // EFFECTS: returns the opposite colour
    public static Colour opposite(Colour colour) {
        return colour == Colour.WHITE ? Colour.BLACK : Colour.WHITE;
    }

    // REQUIRES: colour != null
    // EFFECTS: returns true if colour may currently castle kingside
    public boolean canCastleKingside(Colour colour) {
        return (castlingRights & (colour == Colour.WHITE ? WK : BK)) != 0;
    }

    // REQUIRES: colour != null
    // EFFECTS: returns true if colour may currently castle queenside
    public boolean canCastleQueenside(Colour colour) {
        return (castlingRights & (colour == Colour.WHITE ? WQ : BQ)) != 0;
    }

    // REQUIRES: move != null
    // EFFECTS: returns the captured piece for move, or null
    private Piece capturedPiece(Move move) {
        if (move.MOVE_TYPE == MoveType.EN_PASSANT)
            return move.PIECE_2;
        return board.getSquare(move.END_POS_1);
    }

    // REQUIRES: move != null
    // MODIFIES: this
    // EFFECTS: clears castling rights affected by moved or captured kings/rooks
    private void updateCastlingRights(Move move, Piece captured) {
        if (move.PIECE_1 instanceof King)
            clearKingRights(move.PIECE_1.getColour());
        if (move.PIECE_1 instanceof Rook)
            clearRookRights(move.PIECE_1.getColour(), move.START_POS_1);
        if (captured instanceof Rook)
            clearRookRights(captured.getColour(), move.END_POS_1);
    }

    // REQUIRES: colour != null
    // MODIFIES: this
    // EFFECTS: clears both castling rights for colour
    private void clearKingRights(Colour colour) {
        if (colour == Colour.WHITE)
            castlingRights &= ~(WK | WQ);
        else
            castlingRights &= ~(BK | BQ);
    }

    // REQUIRES: colour != null and pos != null
    // MODIFIES: this
    // EFFECTS: clears the rook castling right associated with pos, if any
    private void clearRookRights(Colour colour, Position pos) {
        if (colour == Colour.WHITE && pos.equals(new Position(0, 0)))
            castlingRights &= ~WQ;
        else if (colour == Colour.WHITE && pos.equals(new Position(7, 0)))
            castlingRights &= ~WK;
        else if (colour == Colour.BLACK && pos.equals(new Position(0, 7)))
            castlingRights &= ~BQ;
        else if (colour == Colour.BLACK && pos.equals(new Position(7, 7)))
            castlingRights &= ~BK;
    }

    // REQUIRES: move != null
    // MODIFIES: this
    // EFFECTS: resets the half-move clock on pawn moves or captures; otherwise increments it
    private void updateHalfMoveClock(Move move, Piece captured) {
        if (move.PIECE_1 instanceof Pawn || captured != null)
            halfMoveClock = 0;
        else
            halfMoveClock++;
    }

    // REQUIRES: move != null
    // MODIFIES: this
    // EFFECTS: sets an en passant target after a two-square pawn move
    private void updateEnpassantTarget(Move move) {
        if (move.PIECE_1 instanceof Pawn && Math.abs(move.END_POS_1.Y - move.START_POS_1.Y) == 2) {
            int targetY = (move.START_POS_1.Y + move.END_POS_1.Y) / 2;
            enpassantTarget = new Position(move.START_POS_1.X, targetY);
        }
    }

    // EFFECTS: returns the board
    public Board getBoard() { return board; }

    // REQUIRES: board != null
    // MODIFIES: this
    // EFFECTS: replaces the board
    public void setBoard(Board board) { this.board = board; }

    // EFFECTS: returns the colour whose turn it is
    public Colour getCurrentTurn() { return currentTurn; }

    // REQUIRES: currentTurn != null
    // MODIFIES: this
    // EFFECTS: sets the colour whose turn it is
    public void setCurrentTurn(Colour currentTurn) { this.currentTurn = currentTurn; }

    // EFFECTS: returns the current en passant target square, or null
    public Position getEnpassantTarget() { return enpassantTarget; }

    // MODIFIES: this
    // EFFECTS: sets the en passant target square
    public void setEnpassantTarget(Position enpassantTarget) { this.enpassantTarget = enpassantTarget; }

    // EFFECTS: returns the castling rights bit field
    public byte getCastlingRights() { return castlingRights; }

    // MODIFIES: this
    // EFFECTS: sets the castling rights bit field
    public void setCastlingRights(byte castlingRights) { this.castlingRights = castlingRights; }

    // EFFECTS: returns the half-move clock
    public int getHalfMoveClock() { return halfMoveClock; }

    // MODIFIES: this
    // EFFECTS: sets the half-move clock
    public void setHalfMoveClock(int halfMoveClock) { this.halfMoveClock = halfMoveClock; }
}
