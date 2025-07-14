package model.pieces;

import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class Pawn extends Piece {
    
    public Pawn(Colour colour) {
        super(Type.PAWN, colour, "Pawn", "P");
    }
}