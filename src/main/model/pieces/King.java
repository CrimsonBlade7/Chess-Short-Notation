package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class King extends Piece {
    
    public King(Colour colour) {
        super(PieceType.KING, colour, "King", "K");
    }

    @Override
    public List<Move> validMoves(Board board, int ix, int iy) {
        List<Move> validMoves = new ArrayList<>();

        for (int fy = -1; fy <= 1; fy++) {
            for (int fx = -1; fx <= 1; fx++) {
                if (fx == 0 && fy == 0) { continue; } // Skip the current position
                addMove(ix, iy, fx, fy, board, validMoves);
            }
        }

        return validMoves;
    }
}