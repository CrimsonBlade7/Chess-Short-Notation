package model.pieces;

import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class Rook extends Piece {
    
    public Rook(Colour colour) {
        super(Type.ROOK, colour, "Rook", "R");
    }   
}