package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Position;

public class King extends Piece {

    private boolean canCastle;

    public King(Colour colour, Position pos) {
        super(PieceType.KING, colour, "King", "K", pos);
        canCastle = true;
    }

    public King(Colour colour, Position pos, boolean canCastle) {
        super(PieceType.KING, colour, "King", "K", pos);
        this.canCastle = canCastle;
    }

    @Override
    public List<Position> validPositions(Board board) {

        List<Position> validPositionsList = new ArrayList<>();

        for (int y = -1; y <= 1; y++) {
            for (int x = -1; x <= 1; x++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Position newPos = new Position(this.getX() + x, this.getY() + y);
                if (super.isValidPosition(newPos, board)) {
                    validPositionsList.add(newPos);
                }
            }
        }
        return validPositionsList;
    }

    @Override
    public Piece copy() {
        return new King(COLOUR, this.pos, this.canCastle);
    }
}