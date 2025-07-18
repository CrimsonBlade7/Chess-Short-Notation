package model;
import model.misc_vars.Colour;
import model.move_tools.Move;
import model.pieces.*;

public class Board {

    private Piece[][] board;
    
    public Board() {

        //initialize board
        initializeBoard();
    }
    
    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    private void initializeBoard() {

        board = new Piece[8][8];
        board[0][0] = new Rook(Colour.WHITE, 0, 0);
        board[0][1] = new Knight(Colour.WHITE, 1, 0);
        board[0][2] = new Bishop(Colour.WHITE, 2, 0);
        board[0][3] = new Queen(Colour.WHITE, 3, 0);
        board[0][4] = new King(Colour.WHITE, 4, 0);
        board[0][5] = new Bishop(Colour.WHITE, 5, 0);
        board[0][6] = new Knight(Colour.WHITE, 6, 0);
        board[0][7] = new Rook(Colour.WHITE, 7, 0);
        
        board[7][0] = new Rook(Colour.BLACK, 0, 7);
        board[7][1] = new Knight(Colour.BLACK, 1, 7);
        board[7][2] = new Bishop(Colour.BLACK, 2, 7);
        board[7][3] = new Queen(Colour.BLACK, 3, 7);
        board[7][4] = new King(Colour.BLACK, 4, 7);
        board[7][5] = new Bishop(Colour.BLACK, 5, 7);
        board[7][6] = new Knight(Colour.BLACK, 6, 7);
        board[7][7] = new Rook(Colour.BLACK, 7, 7);
        
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Colour.WHITE, i, 1);
            board[6][i] = new Pawn(Colour.BLACK, i, 6);
        }
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    public void resetBoard() {
        initializeBoard();
    }
    
    @Override
    public String toString() {

        String result = "";
        result += "                  Chess\n";
        result += "    |---|---|---|---|---|---|---|---|\n";
        for (int y = 7; y >= 0; y--)
        {
            result += y+1 + "   | ";
            for (int x = 0; x < 8; x++)
            {
                Piece currentPiece = board[y][x];
                if (currentPiece == null) result += "  | ";
                else if (currentPiece.getColour() == Colour.BLACK) result += currentPiece.getSymbol().toLowerCase() + " | ";
                else result += currentPiece.getSymbol() + " | ";
            }
            result += "\n    |---|---|---|---|---|---|---|---|\n";
        }
        result += "\n      a   b   c   d   e   f   g   h\n\n";

        return result;
    }
    
    // REQUIRES: move is valid
    // MODIFIES: board
    // EFFECTS: Moves a piece from the initial square (ix, iy) to the final square (fx, fy)
    public boolean move(Move move) {

        int ix = move.getIx();
        int iy = move.getIy();
        int fx = move.getFx();
        int fy = move.getFy();

        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;

        board[fy][fx].setPosition(fx, fy);

        return true;
    }

    // TODO: remove later; temp method for testing
    public boolean move(int ix, int iy, int fx, int fy) {

        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;

        board[fy][fx].setPosition(fx, fy);

        return true;
    }
    
    public Piece[][] getBoard() { return board; }

    public Piece getSquare(int x, int y) { return board[y][x]; }
}