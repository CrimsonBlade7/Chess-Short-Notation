package model.move_tools;

import java.util.Set;
import model.misc_vars.MoveTag;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    public Piece piece;
    public Position pos;
    public Set<MoveTag> moveTags;
    private Piece altPiece;
    private Position altPos; // Alternative position for special moves like castling

    public Move(Piece piece, Position pos, Set<MoveTag> moveTags) {
        this.piece = piece;
        this.pos = pos;
        this.moveTags = moveTags;
    }

    public Move(Piece piece, Position pos, Set<MoveTag> moveTags, Piece altPiece, Position altPos) {
        this.piece = piece;
        this.pos = pos;
        this.moveTags = moveTags;
        this.altPiece = altPiece;
        this.altPos = altPos;
    }

    public Piece getPiece() { return piece; }

    public void setPiece(Piece piece) { this.piece = piece; }

    public Position getPos() { return pos; }

    public void setPos(Position pos) { this.pos = pos; }

    public int getTargetX() { return pos.getX(); }

    public int getTargetY() { return pos.getY(); }

    public Set<MoveTag> getMoveTags() { return moveTags; }

    public void setMoveTags(Set<MoveTag> moveTags) { this.moveTags = moveTags; }

    public Piece getAltPiece() { return altPiece; }

    public void setAltPiece(Piece altPiece) { this.altPiece = altPiece; }

    public Position getAltPos() { return altPos; }

    public void setAltPos(Position altPos) { this.altPos = altPos; }

    public void addMoveTag(MoveTag moveTag) {
        moveTags.add(moveTag); 
    }

}
