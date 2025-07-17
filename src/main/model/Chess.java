package model;

import java.util.ArrayList;
import java.util.List;

import model.misc_vars.Colour;
import model.move_tools.Move;
import model.pieces.Piece;

public class Chess {

    Board board;
    List<Move> moves;
    Colour currentTurn;
    List<Piece> whitePieces;
    List<Piece> blackPieces;
    Boolean gameOver;

    public Chess() {
        this.board = new Board();
        this.moves = new ArrayList<>();
        this.currentTurn = Colour.WHITE; // White starts first
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.gameOver = false;
    }

    public boolean startGame() {
        // Initialize the board with pieces
        board.resetBoard();
        return true; // Game started successfully
    }
}
