package model.misc_vars;

// Information about the type of move being made in the game.
public enum MoveTag {
    CAPTURE, KINGSIDE_CASTLE, QUEENSIDE_CASTLE, PROMOTION, CHECK, CHECKMATE, EN_PASSANT, DRAW
}