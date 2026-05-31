package model.move_tools;

import static org.junit.jupiter.api.Assertions.*;

import model.exceptions.ImpossibleMoveStateException;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Rook;
import org.junit.jupiter.api.Test;

public class BoardStateTest {

    // EFFECTS: verifies castling moves both pieces, changes turn, and clears rights
    @Test
    void executeCastlingMovesKingAndRookAndClearsRights() throws ImpossibleMoveStateException {
        BoardState state = castleReadyState();
        King king = (King) state.getSquare(pos("e1"));
        Rook rook = (Rook) state.getSquare(pos("h1"));
        Move castle = new Move(king, pos("e1"), pos("g1"), rook, pos("h1"), pos("f1"),
                null, MoveType.CASTLING, state);

        state.executeMove(castle, false);

        assertNull(state.getSquare(pos("e1")));
        assertNull(state.getSquare(pos("h1")));
        assertSame(king, state.getSquare(pos("g1")));
        assertSame(rook, state.getSquare(pos("f1")));
        assertEquals(Colour.BLACK, state.getCurrentTurn());
        assertFalse(state.canCastleKingside(Colour.WHITE));
        assertFalse(state.canCastleQueenside(Colour.WHITE));
        assertEquals(1, state.getHalfMoveClock());
    }

    // EFFECTS: verifies a two-square pawn move creates the en passant target
    @Test
    void executeDoublePawnMoveSetsEnPassantTargetAndResetsClock() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        Pawn pawn = (Pawn) state.getSquare(pos("e2"));
        state.setHalfMoveClock(7);

        state.executeMove(new Move(pawn, pos("e2"), pos("e4"), MoveType.NORMAL, state), false);

        assertEquals(pos("e3"), state.getEnpassantTarget());
        assertEquals(0, state.getHalfMoveClock());
        assertEquals(Colour.BLACK, state.getCurrentTurn());
    }

    // EFFECTS: verifies moving and capturing rooks on original squares clears both rights
    @Test
    void capturingRookOnStartingSquareClearsCastlingRight() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Rook whiteRook = new Rook(Colour.WHITE);
        state.getBoard().setSquare(new King(Colour.WHITE), pos("e1"));
        state.getBoard().setSquare(new King(Colour.BLACK), pos("a8"));
        state.getBoard().setSquare(whiteRook, pos("h1"));
        state.getBoard().setSquare(new Rook(Colour.BLACK), pos("h8"));
        state.setCastlingRights((byte) (BoardState.WK | BoardState.BK));

        state.executeMove(new Move(whiteRook, pos("h1"), pos("h8"), MoveType.NORMAL, state), false);

        assertFalse(state.canCastleKingside(Colour.WHITE));
        assertFalse(state.canCastleKingside(Colour.BLACK));
    }

    // EFFECTS: verifies copied board states do not share board pieces or state fields
    @Test
    void copyConstructorCreatesIndependentState() {
        BoardState state = new BoardState();
        state.setCurrentTurn(Colour.BLACK);
        state.setEnpassantTarget(pos("e3"));
        state.setCastlingRights(BoardState.WK);
        state.setHalfMoveClock(14);

        BoardState copy = new BoardState(state);
        copy.getBoard().setSquare(null, pos("e1"));
        copy.setCurrentTurn(Colour.WHITE);
        copy.setEnpassantTarget(null);
        copy.setCastlingRights((byte) 0);
        copy.setHalfMoveClock(0);

        assertNotNull(state.getSquare(pos("e1")));
        assertEquals(Colour.BLACK, state.getCurrentTurn());
        assertEquals(pos("e3"), state.getEnpassantTarget());
        assertEquals(BoardState.WK, state.getCastlingRights());
        assertEquals(14, state.getHalfMoveClock());
    }

    // EFFECTS: returns a minimal position where white can castle either side
    private BoardState castleReadyState() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), pos("e1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), pos("a1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), pos("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), pos("h8"));
        state.setCastlingRights((byte) (BoardState.WK | BoardState.WQ));
        return state;
    }

    // EFFECTS: converts algebraic notation to a position
    private Position pos(String square) {
        return NotationConverter.algebraicToPosition(square);
    }
}
