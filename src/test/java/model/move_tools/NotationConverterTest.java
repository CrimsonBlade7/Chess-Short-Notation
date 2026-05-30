package model.move_tools;

import static org.junit.jupiter.api.Assertions.*;

import model.exceptions.ImpossibleMoveStateException;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;
import org.junit.jupiter.api.Test;

public class NotationConverterTest {

    // EFFECTS: verifies simple pawn notation resolves to the expected move
    @Test
    void parsesSimplePawnMove() {
        BoardState state = new BoardState();
        Move move = NotationConverter.stringToMove("e4", state);

        assertNotNull(move);
        assertEquals(new Position(4, 1), move.START_POS_1);
        assertEquals(new Position(4, 3), move.END_POS_1);
        assertEquals(MoveType.NORMAL, move.MOVE_TYPE);
    }

    // EFFECTS: verifies capture markers must match an actual capture
    @Test
    void rejectsIncorrectCaptureMarker() {
        BoardState state = new BoardState();

        assertNull(NotationConverter.stringToMove("exd3", state));
        assertNull(NotationConverter.stringToMove("xe4", state));
    }

    // EFFECTS: verifies castling notation moves both king and rook
    @Test
    void parsesAndExecutesKingsideCastling() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("f1"));
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("g1"));

        Move move = NotationConverter.stringToMove("O-O", state);
        assertNotNull(move);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("g1")) instanceof King);
        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("f1")) instanceof Rook);
    }

    // EFFECTS: verifies queenside castling notation moves both king and rook
    @Test
    void parsesAndExecutesQueensideCastling() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("b1"));
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("c1"));
        state.getBoard().setSquare(null, NotationConverter.algebraicToPosition("d1"));

        Move move = NotationConverter.stringToMove("O-O-O", state);
        assertNotNull(move);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("c1")) instanceof King);
        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("d1")) instanceof Rook);
    }

    // EFFECTS: verifies castling is rejected when the king would pass through check
    @Test
    void rejectsCastlingThroughAttackedSquare() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("a1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new Rook(Colour.BLACK), NotationConverter.algebraicToPosition("d8"));
        state.setCastlingRights(BoardState.WQ);

        assertNull(NotationConverter.stringToMove("O-O-O", state));
    }

    // EFFECTS: verifies castling is rejected while the king is in check
    @Test
    void rejectsCastlingOutOfCheck() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new Rook(Colour.BLACK), NotationConverter.algebraicToPosition("e8"));
        state.setCastlingRights(BoardState.WK);

        assertNull(NotationConverter.stringToMove("O-O", state));
    }

    // EFFECTS: verifies castling is rejected when pieces block the path
    @Test
    void rejectsBlockedCastling() {
        BoardState state = new BoardState();

        assertNull(NotationConverter.stringToMove("O-O", state));
    }

    // EFFECTS: verifies moving the king permanently clears castling rights
    @Test
    void rejectsCastlingAfterKingHasMoved() throws ImpossibleMoveStateException {
        BoardState state = castleReadyState();

        execute(state, "Ke2");
        state.setCurrentTurn(Colour.WHITE);
        execute(state, "Ke1");

        assertNull(NotationConverter.stringToMove("O-O", state));
        assertFalse(state.canCastleKingside(Colour.WHITE));
        assertFalse(state.canCastleQueenside(Colour.WHITE));
    }

    // EFFECTS: verifies moving the rook permanently clears that side's castling right
    @Test
    void rejectsCastlingAfterRookHasMoved() throws ImpossibleMoveStateException {
        BoardState state = castleReadyState();

        execute(state, "Rh2");
        state.setCurrentTurn(Colour.WHITE);
        execute(state, "Rh1");

        assertNull(NotationConverter.stringToMove("O-O", state));
        assertFalse(state.canCastleKingside(Colour.WHITE));
        assertTrue(state.canCastleQueenside(Colour.WHITE));
    }

    // EFFECTS: verifies en passant notation captures the pawn that advanced two squares
    @Test
    void parsesAndExecutesEnPassant() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        execute(state, "e4");
        execute(state, "a6");
        execute(state, "e5");
        execute(state, "d5");

        Move move = NotationConverter.stringToMove("exd6", state);
        assertNotNull(move);
        assertEquals(MoveType.EN_PASSANT, move.MOVE_TYPE);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("d6")) instanceof Pawn);
        assertNull(state.getSquare(NotationConverter.algebraicToPosition("d5")));
    }

    // EFFECTS: verifies en passant is available only on the immediate reply
    @Test
    void rejectsExpiredEnPassant() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        execute(state, "e4");
        execute(state, "a6");
        execute(state, "e5");
        execute(state, "d5");
        execute(state, "Nf3");
        execute(state, "a5");

        assertNull(NotationConverter.stringToMove("exd6", state));
    }

    // EFFECTS: verifies en passant cannot capture a pawn that moved only one square
    @Test
    void rejectsEnPassantWithoutDoublePawnAdvance() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        execute(state, "e4");
        execute(state, "d6");
        execute(state, "e5");

        assertNull(NotationConverter.stringToMove("exd6", state));
    }

    // EFFECTS: verifies pawn promotion notation replaces the pawn with the chosen piece
    @Test
    void parsesAndExecutesPromotion() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new Pawn(Colour.WHITE), NotationConverter.algebraicToPosition("a7"));
        state.setCastlingRights((byte) 0);

        Move move = NotationConverter.stringToMove("a8=Q", state);
        assertNotNull(move);
        assertEquals(MoveType.PROMOTION, move.MOVE_TYPE);
        state.executeMove(move, false);

        assertTrue(state.getSquare(NotationConverter.algebraicToPosition("a8")) instanceof Queen);
    }

    // EFFECTS: verifies promotion requires an explicit legal promotion piece
    @Test
    void rejectsMissingAndInvalidPromotionPiece() {
        BoardState state = promotionReadyState();

        assertNull(NotationConverter.stringToMove("a8", state));
        assertNull(NotationConverter.stringToMove("a8=K", state));
        assertNull(NotationConverter.stringToMove("a8=P", state));
    }

    // EFFECTS: verifies promotion can capture on the final rank
    @Test
    void parsesCapturePromotion() throws ImpossibleMoveStateException {
        BoardState state = promotionReadyState();
        state.getBoard().setSquare(new Rook(Colour.BLACK), NotationConverter.algebraicToPosition("b8"));

        Move move = NotationConverter.stringToMove("axb8=N", state);
        assertNotNull(move);
        state.executeMove(move, false);

        assertEquals("N", state.getSquare(NotationConverter.algebraicToPosition("b8")).getSymbol());
        assertNull(state.getSquare(NotationConverter.algebraicToPosition("a7")));
    }

    // EFFECTS: verifies pawn moves and captures reset the half-move clock
    @Test
    void resetsHalfMoveClockOnPawnMoveAndCapture() throws ImpossibleMoveStateException {
        BoardState state = new BoardState();
        state.setHalfMoveClock(12);
        execute(state, "Nf3");
        assertEquals(13, state.getHalfMoveClock());

        execute(state, "d5");
        assertEquals(0, state.getHalfMoveClock());

        BoardState captureState = new BoardState();
        captureState.getBoard().clearBoard();
        captureState.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e1"));
        captureState.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("e8"));
        captureState.getBoard().setSquare(new Knight(Colour.WHITE), NotationConverter.algebraicToPosition("f3"));
        captureState.getBoard().setSquare(new Pawn(Colour.BLACK), NotationConverter.algebraicToPosition("e5"));
        captureState.setCastlingRights((byte) 0);
        captureState.setHalfMoveClock(8);

        execute(captureState, "Nxe5");
        assertEquals(0, captureState.getHalfMoveClock());
    }

    // EFFECTS: verifies ambiguous notation is rejected until disambiguated
    @Test
    void rejectsAmbiguousMoveButAcceptsDisambiguation() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e3"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("c8"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("a1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        state.setCastlingRights((byte) 0);

        assertNull(NotationConverter.stringToMove("Rd1", state));
        assertNotNull(NotationConverter.stringToMove("Rad1", state));
    }

    // REQUIRES: notation is legal in state
    // MODIFIES: state
    // EFFECTS: parses and executes notation
    private void execute(BoardState state, String notation) throws ImpossibleMoveStateException {
        Move move = NotationConverter.stringToMove(notation, state);
        assertNotNull(move, notation);
        state.executeMove(move, false);
    }

    // EFFECTS: returns a minimal position where white can castle either side
    private BoardState castleReadyState() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("a1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.setCastlingRights((byte) (BoardState.WK | BoardState.WQ));
        return state;
    }

    // EFFECTS: returns a minimal position where a white pawn can promote from a7
    private BoardState promotionReadyState() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new Pawn(Colour.WHITE), NotationConverter.algebraicToPosition("a7"));
        state.setCastlingRights((byte) 0);
        return state;
    }
}
