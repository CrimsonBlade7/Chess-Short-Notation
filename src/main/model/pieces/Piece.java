package model.pieces;

import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public abstract class Piece {
    protected final Type type;
    protected final Colour colour;
    private String name, symbol;

    public Piece(Type type, Colour colour, String name, String symbol) {
        this.type = type;
        this.colour = colour;
        this.name = name;
        this.symbol = symbol;
    }
    
    public Type getType() { return type; }
    public Colour getColour() { return colour; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
}