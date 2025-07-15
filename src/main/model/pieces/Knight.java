package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Knight extends Piece {

    public Knight(Colour colour) {
        super(PieceType.KNIGHT, colour, "Knight", "N");
    }

    @Override
    public List<Move> validMoves(Board board, int ix, int iy) {
        List<Move> validMoves = new ArrayList<>();

        addMove(ix, iy, ix - 2, iy + 1, board, validMoves);
        addMove(ix, iy, ix - 1, iy + 2, board, validMoves);
        addMove(ix, iy, ix + 1, iy + 2, board, validMoves);
        addMove(ix, iy, ix + 2, iy + 1, board, validMoves);
        addMove(ix, iy, ix + 2, iy - 1, board, validMoves);
        addMove(ix, iy, ix + 1, iy - 2, board, validMoves);
        addMove(ix, iy, ix - 1, iy - 2, board, validMoves);
        addMove(ix, iy, ix - 2, iy - 1, board, validMoves);

        return validMoves;
    }
}