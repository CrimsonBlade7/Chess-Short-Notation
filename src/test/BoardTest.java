import static org.junit.jupiter.api.Assertions.*;

import model.misc_vars.Colour;
import model.move_tools.Board;
import model.move_tools.Position;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Queen;
import model.pieces.Rook;
import org.junit.jupiter.api.Test;

public class BoardTest {

    // EFFECTS: verifies that a new board contains the standard starting pieces
    @Test
    void initializesStandardStartingPosition() {
        Board board = new Board();

        assertTrue(board.getSquare(new Position(4, 0)) instanceof King);
        assertEquals(Colour.WHITE, board.getSquare(new Position(4, 0)).getColour());
        assertTrue(board.getSquare(new Position(3, 7)) instanceof Queen);
        assertEquals(Colour.BLACK, board.getSquare(new Position(3, 7)).getColour());
        assertTrue(board.getSquare(new Position(0, 1)) instanceof Pawn);
        assertTrue(board.getSquare(new Position(7, 6)) instanceof Pawn);
    }

    // EFFECTS: verifies that moving a piece clears its start square
    @Test
    void moveSquareMovesPieceAndClearsStart() {
        Board board = new Board();
        Position start = new Position(0, 0);
        Position end = new Position(0, 3);

        board.setSquare(null, new Position(0, 1));
        board.moveSquare(start, end);

        assertNull(board.getSquare(start));
        assertTrue(board.getSquare(end) instanceof Rook);
    }

    // EFFECTS: verifies that copied boards do not share piece arrays
    @Test
    void copyConstructorCreatesIndependentBoard() {
        Board board = new Board();
        Board copy = new Board(board);

        copy.setSquare(null, new Position(4, 0));

        assertNotNull(board.getSquare(new Position(4, 0)));
        assertNull(copy.getSquare(new Position(4, 0)));
    }

    // EFFECTS: verifies position equality and hashing for set membership
    @Test
    void positionsCompareByCoordinates() {
        assertEquals(new Position(2, 5), new Position(2, 5));
        assertEquals(new Position(2, 5).hashCode(), new Position(2, 5).hashCode());
        assertNotEquals(new Position(2, 5), new Position(5, 2));
    }
}
