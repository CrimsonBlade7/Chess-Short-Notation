import static org.junit.jupiter.api.Assertions.*;

import model.Chess;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.NotationConverter;
import model.move_tools.Position;
import model.pieces.King;
import model.pieces.Pawn;
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
}
