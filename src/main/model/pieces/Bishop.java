package model.pieces;

import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class Bishop extends Piece {

    public Bishop(Colour colour) {
        super(Type.BISHOP, colour, "Bishop", "B");
    }
}