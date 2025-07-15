package model;
import java.util.Scanner;

import model.misc_vars.Colour;
import model.pieces.*;

public class Board {

    private Piece[][] board;
    private Scanner input = new Scanner(System.in);
    
    public Board() {

        //initialize board
        resetBoard();
    }
    
    public void resetBoard() {

        board = new Piece[8][8];
        board[0][0] = new Rook(Colour.WHITE);
        board[0][1] = new Knight(Colour.WHITE);
        board[0][2] = new Bishop(Colour.WHITE);
        board[0][3] = new Queen(Colour.WHITE);
        board[0][4] = new King(Colour.WHITE);
        board[0][5] = new Bishop(Colour.WHITE);
        board[0][6] = new Knight(Colour.WHITE);
        board[0][7] = new Rook(Colour.WHITE);
        
        board[7][0] = new Rook(Colour.BLACK);
        board[7][1] = new Knight(Colour.BLACK);
        board[7][2] = new Bishop(Colour.BLACK);
        board[7][3] = new Queen(Colour.BLACK);
        board[7][4] = new King(Colour.BLACK);
        board[7][5] = new Bishop(Colour.BLACK);
        board[7][6] = new Knight(Colour.BLACK);
        board[7][7] = new Rook(Colour.BLACK);
        
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Colour.WHITE);
            board[6][i] = new Pawn(Colour.BLACK);
        }
    }
    
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
    
    public boolean move(int ix, int iy, int fx, int fy, Colour currentColour) {
        //check if the initial and final squares are different
        if (iy == fy && ix == iy) return false;

        //check if the initial and final squares are within bounds
        if (iy < 0 || iy > 7) return false;
        if (ix < 0 || ix > 7) return false;
        if (fy < 0 || fy > 7) return false;
        if (fx < 0 || fx > 7) return false;
        
        //check if the initial square is empty
        if (board[iy][ix] == null) return false;

        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;

        return true;
    }
    
    public Piece[][] getBoard() { return board; }

    public Piece getSquare(int x, int y) { return board[y][x]; }
}