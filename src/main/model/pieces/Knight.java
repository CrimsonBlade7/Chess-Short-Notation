package model.pieces;

import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class Knight extends Piece {

    public Knight(Colour colour) {
        super(Type.KNIGHT, colour, "Knight", "N");
    }
}