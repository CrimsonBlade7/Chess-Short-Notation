package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class King extends Piece {

    public King(Colour colour, int x, int y) {
        super(PieceType.KING, colour, "King", "K", x, y);
    }

    @Override
    public List<Move> possibleMoves(Board board) {

        List<Move> possibleMoves = new ArrayList<>();

        for (int fy = -1; fy <= 1; fy++) {
            for (int fx = -1; fx <= 1; fx++) {
                if (fx == 0 && fy == 0) {
                    continue;
                } // Skip the current position
                // TODO: can't move to a square that is under attack
                addMove(this, x, y, fx, fy, board, possibleMoves);
            }
        }

        return possibleMoves;
    }
}