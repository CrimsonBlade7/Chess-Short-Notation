package model.pieces;

import java.util.ArrayList;
import java.util.List;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Rook extends Piece {
    
    public Rook(Colour colour) {
        super(Type.ROOK, colour, "Rook", "R");
    }

    @Override
    public Move[] validMoves() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validMoves'");
    }   
}