package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Queen extends Piece {

    public Queen(Colour colour) {
        super(Type.QUEEN, colour, "Queen", "Q");
    }

    @Override
    public Move[] validMoves() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validMoves'");
    }
}