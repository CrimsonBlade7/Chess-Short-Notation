package model;

import java.util.ArrayList;
import java.util.List;
import model.exceptions.ImpossibleMoveStateException;
import model.misc_vars.Colour;
import model.move_tools.BoardState;
import model.move_tools.Move;
import model.move_tools.NotationConverter;

public class Chess {

    private final List<BoardState> history;
    private BoardState boardState;

    // EFFECTS: creates a new chess game in the standard starting position
    public Chess() {
        history = new ArrayList<>();
        startGame();
    }

    // MODIFIES: this
    // EFFECTS: resets the game to the standard starting position
    public boolean startGame() {
        boardState = new BoardState();
        history.clear();
        history.add(new BoardState(boardState));
        return true;
    }

    // REQUIRES: notation != null
    // MODIFIES: this
    // EFFECTS: applies notation as a legal short algebraic move; returns true if
    // successful and false if the notation is invalid or illegal
    public boolean makeMove(String notation) {
        Move move = NotationConverter.stringToMove(notation, boardState);
        if (move == null)
            return false;
        try {
            boardState.executeMove(move, false);
            history.add(new BoardState(boardState));
            return true;
        } catch (ImpossibleMoveStateException e) {
            return false;
        }
    }

    // EFFECTS: returns true if the current side to move is checkmated or stalemated
    public boolean isGameOver() {
        Colour turn = boardState.getCurrentTurn();
        return boardState.isCheckmate(turn) || boardState.isStalemate(turn);
    }

    // EFFECTS: returns a human-readable status for the current game state
    public String status() {
        Colour turn = boardState.getCurrentTurn();
        if (boardState.isCheckmate(turn))
            return "Checkmate. " + BoardState.opposite(turn) + " wins.";
        if (boardState.isStalemate(turn))
            return "Stalemate.";
        if (boardState.isInCheck(turn))
            return turn + " is in check.";
        return turn + " to move.";
    }

    // EFFECTS: returns the current board state
    public BoardState getBoardState() { return boardState; }

    // EFFECTS: returns snapshots of the game after every accepted move
    public List<BoardState> getHistory() { return history; }
}
