package model.pieces;

import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class Queen extends Piece {

    public Queen(Colour colour) {
        super(Type.QUEEN, colour, "Queen", "Q");
    }
}