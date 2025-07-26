package model.pieces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Board;
import model.misc_vars.*;
import model.move_tools.Move;

public class Bishop extends Piece {

    public Bishop(Colour colour, int x, int y) {
        super(PieceType.BISHOP, colour, "Bishop", "B", x, y);
    }

    @Override
    public List<Move> possibleSquares(int ix, int iy, Board board) {

        List<int[]> possibleSquares = new ArrayList<>();

        int[][] directions = { { -1, 1 }, // Top-left
                { 1, 1 }, // Top-right
                { -1, -1 }, // Bottom-left
                { 1, -1 } // Bottom-right
        };

        for (int[] dir : directions) {
            int fx = ix;
            int fy = iy;
            boolean continueSearch = true;
            do {
                fx += dir[0];
                fy += dir[1];

                if (super.isValidMove(fx, fy, board)) {
                    int[] square = { fx, fy };
                    possibleSquares.add(square);
                }

            } while (continueSearch);
        }

        return possibleMoves;
    }

    @Override
    public List<Move> possibleMoves(int ix, int iy, Board board) {

        List<Move> possibleMoves = new ArrayList<>();

        int[][] directions = { { -1, 1 }, // Top-left
                { 1, 1 }, // Top-right
                { -1, -1 }, // Bottom-left
                { 1, -1 } // Bottom-right
        };

        for (int[] dir : directions) {
            int fx = ix;
            int fy = iy;
            boolean continueSearch = true;
            do {
                fx += dir[0];
                fy += dir[1];

                if (super.isValidMove(fx, fy, board)) {
                    Board newBoard = board.copy();
                    newBoard.move(x, y, fx, fy);
                    addMove(fx, fy, board, newBoard, possibleMoves);
                } else {
                    continueSearch = false; // Stop searching in this direction if the move is invalid
                }

            } while (continueSearch);
        }

        return possibleMoves;
    }

    // REQUIRES: ix, iy, fx, fy are within the bounds of the board (0 <= ix, iy, fx,
    // fy < 8), board != null, board.getSquare(ix, iy) != <friendly piece>
    // MODIFIES: possibleMoves
    // EFFECTS: adds a move from (ix, iy) to (fx, fy) to possibleMoves
    protected void addMove(int x, int y, Board board, Board newBoard, List<Move> possibleMoves) {
        Move move = new Move(this, x, y, new HashSet<>());
        if (board.getSquare(x, y) != null) {
            move.addMoveTag(MoveTag.CAPTURE);
        }
        if (newBoard.getSquare(x, y).) {
            possibleMoves.add(move);
    }

    @Override
    public Piece copy() {
        return new Bishop(COLOUR, this.x, this.y);
    }
}