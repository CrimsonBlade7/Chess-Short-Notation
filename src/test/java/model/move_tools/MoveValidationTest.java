package model.move_tools;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Rook;
import org.junit.jupiter.api.Test;

public class MoveValidationTest {

    // EFFECTS: verifies moving the wrong side or capturing a friendly piece is illegal
    @Test
    void rejectsWrongTurnAndFriendlyCapture() {
        BoardState state = new BoardState();
        Pawn blackPawn = (Pawn) state.getSquare(pos("e7"));
        Rook whiteRook = (Rook) state.getSquare(pos("a1"));

        assertFalse(MoveValidation.isLegalMove(state,
                new Move(blackPawn, pos("e7"), pos("e5"), MoveType.NORMAL, state)));
        assertFalse(MoveValidation.isLegalMove(state,
                new Move(whiteRook, pos("a1"), pos("a2"), MoveType.NORMAL, state)));
    }

    // EFFECTS: verifies a move that exposes the moving side's king is illegal
    @Test
    void rejectsMoveThatLeavesKingInCheck() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        Rook pinnedRook = new Rook(Colour.WHITE);
        state.getBoard().setSquare(new King(Colour.WHITE), pos("e1"));
        state.getBoard().setSquare(pinnedRook, pos("e2"));
        state.getBoard().setSquare(new Rook(Colour.BLACK), pos("e8"));
        state.getBoard().setSquare(new King(Colour.BLACK), pos("a8"));
        state.setCastlingRights((byte) 0);

        assertFalse(MoveValidation.isLegalMove(state,
                new Move(pinnedRook, pos("e2"), pos("a2"), MoveType.NORMAL, state)));
    }

    // EFFECTS: verifies attacked-square detection uses pawn attack squares, not pawn pushes
    @Test
    void pawnAttackDetectionIgnoresForwardPushes() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), pos("e1"));
        state.getBoard().setSquare(new King(Colour.BLACK), pos("h8"));
        state.getBoard().setSquare(new Pawn(Colour.BLACK), pos("d4"));
        state.setCastlingRights((byte) 0);

        assertTrue(MoveValidation.isSquareAttacked(state, pos("e3"), Colour.BLACK));
        assertFalse(MoveValidation.isSquareAttacked(state, pos("d3"), Colour.BLACK));
    }

    // EFFECTS: verifies generated legal moves include castling when rights and path allow it
    @Test
    void legalMovesIncludesAvailableCastling() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        state.getBoard().setSquare(new King(Colour.WHITE), pos("e1"));
        state.getBoard().setSquare(new Rook(Colour.WHITE), pos("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), pos("h8"));
        state.setCastlingRights(BoardState.WK);

        List<Move> moves = MoveValidation.legalMoves(state, Colour.WHITE);

        assertTrue(moves.stream().anyMatch(move -> move.MOVE_TYPE == MoveType.CASTLING
                && move.START_POS_1.equals(pos("e1"))
                && move.END_POS_1.equals(pos("g1"))
                && move.START_POS_2.equals(pos("h1"))
                && move.END_POS_2.equals(pos("f1"))));
    }

    // EFFECTS: verifies castling is illegal when the king would land on attack
    @Test
    void rejectsCastlingIntoCheck() {
        BoardState state = new BoardState();
        state.getBoard().clearBoard();
        King king = new King(Colour.WHITE);
        Rook rook = new Rook(Colour.WHITE);
        state.getBoard().setSquare(king, pos("e1"));
        state.getBoard().setSquare(rook, pos("h1"));
        state.getBoard().setSquare(new King(Colour.BLACK), pos("a8"));
        state.getBoard().setSquare(new Rook(Colour.BLACK), pos("g8"));
        state.setCastlingRights(BoardState.WK);

        Move castle = new Move(king, pos("e1"), pos("g1"), rook, pos("h1"), pos("f1"),
                null, MoveType.CASTLING, state);

        assertFalse(MoveValidation.isLegalMove(state, castle));
    }

    // EFFECTS: converts algebraic notation to a position
    private Position pos(String square) {
        return NotationConverter.algebraicToPosition(square);
    }
}
