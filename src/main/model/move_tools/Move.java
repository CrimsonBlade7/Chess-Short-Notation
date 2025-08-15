package model.move_tools;

import model.misc_vars.MoveType;
import model.pieces.Piece;

// Represents a move in the chess game.
public class Move {

    private Piece piece;
    private Position pos;
    private boolean capture;
    private boolean check;
    private MoveType moveType;
    private Piece altPiece;
    private Position altPos; // Alternative position for special moves like castling

    public Move(Piece piece, Position pos, boolean capture, MoveType moveType) {
        this.piece = piece;
        this.pos = pos;
        this.capture = capture;
        this.moveType = moveType;
    }

    public Move(Piece piece, Position pos, boolean capture, MoveType moveType, Piece altPiece, Position altPos) {
        this.piece = piece;
        this.pos = pos;
        this.capture = capture;
        this.moveType = moveType;
        this.altPiece = altPiece;
        this.altPos = altPos;
    }

    public Piece getPiece() { return piece; }

    public void setPiece(Piece piece) { this.piece = piece; }

    public Position getPos() { return pos; }

    public void setPos(Position pos) { this.pos = pos; }

    public boolean isCapture() { return capture; }

    public void setCapture(boolean capture) { this.capture = capture; }

    public boolean isCheck() { return check; }

    public void setCheck(boolean check) { this.check = check; }

    public MoveType getMoveType() { return moveType; }

    public void setMoveType(MoveType moveType) { this.moveType = moveType; }

    public Piece getAltPiece() { return altPiece; }

    public void setAltPiece(Piece altPiece) { this.altPiece = altPiece; }

    public Position getAltPos() { return altPos; }

    public void setAltPos(Position altPos) { this.altPos = altPos; }

    public int getTargetX() { return pos.getX(); }

    public int getTargetY() { return pos.getY(); }

}
