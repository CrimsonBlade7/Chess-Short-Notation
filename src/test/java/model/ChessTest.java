package model;

import static org.junit.jupiter.api.Assertions.*;

import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.NotationConverter;
import model.pieces.King;
import model.pieces.Queen;
import model.pieces.Rook;
import org.junit.jupiter.api.Test;

public class ChessTest {

    // EFFECTS: verifies that common SAN moves are accepted and update turn order
    @Test
    void acceptsShortAlgebraicOpeningMoves() {
        Chess chess = new Chess();

        assertTrue(chess.makeMove("e4"));
        assertEquals(Colour.BLACK, chess.getBoardState().getCurrentTurn());
        assertTrue(chess.makeMove("e5"));
        assertTrue(chess.makeMove("Nf3"));

        assertNotNull(chess.getBoardState().getSquare(NotationConverter.algebraicToPosition("f3")));
        assertEquals(Colour.BLACK, chess.getBoardState().getCurrentTurn());
    }

    // EFFECTS: verifies illegal SAN does not mutate the board or turn
    @Test
    void rejectsIllegalMoveAndPreservesTurn() {
        Chess chess = new Chess();

        assertFalse(chess.makeMove("e5"));

        assertNotNull(chess.getBoardState().getSquare(NotationConverter.algebraicToPosition("e2")));
        assertNull(chess.getBoardState().getSquare(NotationConverter.algebraicToPosition("e5")));
        assertEquals(Colour.WHITE, chess.getBoardState().getCurrentTurn());
    }

    // EFFECTS: verifies that moves exposing the king to check are rejected
    @Test
    void rejectsMoveThatLeavesOwnKingInCheck() {
        Chess chess = new Chess();
        BoardState state = chess.getBoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("a8"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("e2"));
        state.getBoard().setSquare(new Rook(Colour.BLACK), NotationConverter.algebraicToPosition("e8"));
        state.setCastlingRights((byte) 0);

        assertFalse(chess.makeMove("Ra2"));
        assertTrue(state.getBoard().getSquare(NotationConverter.algebraicToPosition("e2")) instanceof Rook);
    }

    // EFFECTS: verifies Fool's Mate is detected as checkmate
    @Test
    void detectsCheckmateAfterFoolsMate() {
        Chess chess = new Chess();

        assertTrue(chess.makeMove("f3"));
        assertTrue(chess.makeMove("e5"));
        assertTrue(chess.makeMove("g4"));
        assertTrue(chess.makeMove("Qh4#"));

        assertTrue(chess.isGameOver());
        assertTrue(chess.getBoardState().isCheckmate(Colour.WHITE));
    }

    // EFFECTS: verifies stalemate detection in a known drawn position
    @Test
    void detectsStalemate() {
        Chess chess = new Chess();
        BoardState state = chess.getBoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("h8"));
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("f7"));
        state.getBoard().setSquare(new Queen(Colour.WHITE), NotationConverter.algebraicToPosition("g6"));
        state.setCurrentTurn(Colour.BLACK);
        state.setCastlingRights((byte) 0);

        assertTrue(state.isStalemate(Colour.BLACK));
        assertTrue(chess.isGameOver());
    }

    // EFFECTS: verifies that game history stores the initial state and accepted moves
    @Test
    void recordsHistorySnapshotsForAcceptedMoves() {
        Chess chess = new Chess();

        assertEquals(1, chess.getHistory().size());
        assertTrue(chess.makeMove("e4"));
        assertFalse(chess.makeMove("e4"));

        assertEquals(2, chess.getHistory().size());
    }

    // EFFECTS: verifies the fifty-move rule ends the game as a draw
    @Test
    void detectsFiftyMoveRuleDraw() {
        Chess chess = new Chess();
        chess.getBoardState().setHalfMoveClock(100);

        assertTrue(chess.isGameOver());
        assertEquals("Draw by fifty-move rule.", chess.status());
    }

    // EFFECTS: verifies repeated positions are not draws until the third occurrence
    @Test
    void detectsThreefoldRepetitionOnlyOnThirdOccurrence() {
        Chess chess = new Chess();

        repeatKnightCycle(chess);
        assertFalse(chess.isThreefoldRepetition());
        assertFalse(chess.isGameOver());

        repeatKnightCycle(chess);
        assertTrue(chess.isThreefoldRepetition());
        assertTrue(chess.isGameOver());
        assertEquals("Draw by threefold repetition.", chess.status());
    }

    // EFFECTS: verifies repetition includes side to move
    @Test
    void repetitionKeyIncludesSideToMove() {
        BoardState whiteToMove = kingsOnlyState();
        BoardState blackToMove = kingsOnlyState();
        blackToMove.setCurrentTurn(Colour.BLACK);

        assertNotEquals(whiteToMove.repetitionKey(), blackToMove.repetitionKey());
    }

    // EFFECTS: verifies repetition includes castling rights
    @Test
    void repetitionKeyIncludesCastlingRights() {
        BoardState canCastle = kingsOnlyState();
        BoardState cannotCastle = kingsOnlyState();
        canCastle.getBoard().setSquare(new Rook(Colour.WHITE), NotationConverter.algebraicToPosition("h1"));
        canCastle.setCastlingRights(BoardState.WK);
        cannotCastle.setCastlingRights((byte) 0);

        assertNotEquals(canCastle.repetitionKey(), cannotCastle.repetitionKey());
    }

    // MODIFIES: chess
    // EFFECTS: moves both knights out and back to repeat the initial position
    private void repeatKnightCycle(Chess chess) {
        assertTrue(chess.makeMove("Nf3"));
        assertTrue(chess.makeMove("Nf6"));
        assertTrue(chess.makeMove("Ng1"));
        assertTrue(chess.makeMove("Ng8"));
    }

    // EFFECTS: returns a minimal legal board with only kings
    private BoardState kingsOnlyState() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), NotationConverter.algebraicToPosition("e1"));
        state.getBoard().setSquare(new King(Colour.BLACK), NotationConverter.algebraicToPosition("e8"));
        state.setCastlingRights((byte) 0);
        return state;
    }
}
