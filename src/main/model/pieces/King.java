package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;

public class King extends Piece {

    public King(Colour colour, Position pos) { super(colour, "King", "K", pos); }

    @Override
    public List<Move> validMoves(Board board) {

        List<Move> validMoveList = new ArrayList<>();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Position newPos = new Position(this.getX() + x, this.getY() + y);
                if (super.isValidPosition(newPos, super.COLOUR, board)) {
                    if (board.getSquare(newPos) != null) {
                        validMoveList.add(new Move(this, newPos, true, false, MoveType.NORMAL));
                    }
                    else {
                        validMoveList.add(new Move(this, newPos, false, false, MoveType.NORMAL));
                    }
                }
            }
        }

        if (this.pos. )

        return validMoveList;
    }

    @Override
    public Piece clone() { return new King(COLOUR, this.pos); }
}