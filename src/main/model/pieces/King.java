package model.pieces;

import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class King extends Piece {
    
    public King(Colour colour) {
        super(Type.KING, colour, "King", "K");
    }
}