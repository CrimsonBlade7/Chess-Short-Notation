package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Pawn extends Piece {
    
    public Pawn(Colour colour) {
        super(Type.PAWN, colour, "Pawn", "P");
    }

    @Override
    public Move[] validMoves() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validMoves'");
    }
}