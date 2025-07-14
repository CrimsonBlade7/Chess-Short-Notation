package model;
import java.util.Scanner;

import model.pieces.*;
import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class Board {

    private Piece[][] board;
    private int[] pawnMoved2Pos;
    private int turnsSincePawnMoved2;
    private Scanner input = new Scanner(System.in);
    
    public Board()
    {
        resetBoard();
        pawnMoved2Pos = new int[3];
        pawnMoved2Pos[0] = -1;
        pawnMoved2Pos[1] = -1;
        turnsSincePawnMoved2 = 0;
    }
    
    public void resetBoard()
    {
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
        
        for (int i = 0; i < 8; i++) 
        {
            board[1][i] = new Pawn(Colour.WHITE);
            board[6][i] = new Pawn(Colour.BLACK);
        }
    }
    
    public void printBoard()
    {
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
            result += "\n    |---|---|---|---|---|---|---|---|";
        }
        result += "\n      a   b   c   d   e   f   g   h\n\n";
    }
    
    public boolean move(Move move, Colour currentColour)
    {
        if (move.getIY() == move.getFY() && move.getIX() == move.getIY()) return false;
        if (move.getIY() < 0 || move.getIY() > 7) return false;
        if (move.getIX() < 0 || move.getIX() > 7) return false;
        if (move.getFY() < 0 || move.getFY() > 7) return false;
        if (move.getFX() < 0 || move.getFX() > 7) return false;
            
        //check if the piece on the initial square matches the piece from the move
        if (move.getType() != board[move.getIY()][move.getIX()].getType()) return false; 
        
        //check turn
        if (board[move.getIY()][move.getIX()].getColour() != currentColour) return false;
        
        //check capture is true and dest not null
        if (move.isCapture() == (board[move.getFY()][move.getFX()] == null)) 
        {
            System.out.println("test");
            //Main.enterToContinue();
            return false;
        }
        
        //check if dest not null and diff colour
        if (board[move.getFY()][move.getFX()] != null &&
            board[move.getIY()][move.getIX()].getColour() != board[move.getFY()][move.getFX()].getColour())
            return false; 
            
        //check valid square
        if (!board[move.getIY()][move.getIX()].isValidMove(board, move, pawnMoved2Pos, turnsSincePawnMoved2)) return false;
        
        int yDirection = (currentColour == Type.WHITE) ? 1 : -1;
        
        //check enpassant
        if (isEnPassant(board, move)) board[move.getFY() - 1*yDirection][move.getFX()] = null;
        
        //check castle
        else if (isCastle(board, move))
        {
            if (move.getFX() == 6)
            {
                //disable castle
                King king = (King)board[move.getIY()][move.getIX()];
                Rook rook = (Rook)board[move.getIY()][7];
                king.disableCastle();
                rook.disableCastle();
                
                board[move.getIY()][5] = board[move.getIY()][7];
                board[move.getIY()][7] = null;
            }
            else
            {
                //disable castle
                King king = (King)board[move.getIY()][move.getIX()];
                Rook rook = (Rook)board[move.getIY()][0];
                king.disableCastle();
                rook.disableCastle();
                
                board[move.getIY()][3] = board[move.getIY()][0];
                board[move.getIY()][0] = null;
            }
        }
        
        //check if pawn move 2 square
        else if ((move.getFY() - move.getIY()) == 2*yDirection && 
            board[move.getFY()][move.getFX()] == null && 
            board[move.getFY() - 1*yDirection][move.getFX()] == null)
        {
            turnsSincePawnMoved2 = 0;
            pawnMoved2Pos[0] = move.getFY();
            pawnMoved2Pos[1] = move.getFX();
        }
        
        //disable castle if king or rook move
        if (board[move.getIY()][move.getIX()] instanceof King)
        {
            King king = (King)board[move.getIY()][move.getIX()];
            king.disableCastle();
        }
        else if (board[move.getIY()][move.getIX()] instanceof Rook)
        {
            Rook rook = (Rook)board[move.getIY()][7];
            rook.disableCastle();
        }
        
        board[move.getFY()][move.getFX()] = board[move.getIY()][move.getIX()];
        board[move.getIY()][move.getIX()] = null;
        
        if (board[move.getFY()][move.getFX()] instanceof Pawn && (move.getFY() == 7 || move.getFY() == 0)) 
            board[move.getFY()][move.getFX()] = selectPromotion(currentColour);
            
        turnsSincePawnMoved2++;
        return true;
    }
    
    public Piece selectPromotion(Colour colour)
    {
        while (true)
        {
            printBoard();
            System.out.println("Q: Queen, R: Rook, B: Bishop, K/N: Knight");
            System.out.print("Select Promotion [Q/R/B/K or N]: ");
            switch (input.nextLine().toLowerCase())
            {
                case "q":
                case "queen":
                    return new Queen(colour);

                case "r":
                case "rook":
                    return new Rook(colour);
                    
                case "b":
                case "bishop":
                    return new Bishop(colour);
                    
                case "k":
                case "n":
                case "knight":
                    return new Knight(colour);
                    
                default:
                    System.out.println();
                    System.out.println("Invalid Selection. Please choose [Q/R/B/N].");
                    Main.enterToContinue();
                    Main.clearScreen();
            }
        }
    }

    public boolean isEnPassant(Piece[][] board, Move move)
    {
        //assign direction
        int yDirection = (board[move.getIY()][move.getIX()].getColour() == Type.WHITE) ? 1 : -1;
        
        //check if both are pawns
        if (!(board[move.getIY()][move.getIX()] instanceof Pawn) || !(board[move.getFY() - 1*yDirection][move.getFX()] instanceof Pawn)) return false;

        //return true if conditions are met
        return (move.getFY() - move.getIY()) == 1*yDirection && 
            Math.abs(move.getFX() - move.getIX()) == 1 &&
            (move.getIY() == 4 && move.getFY() == 5) || (move.getIY() == 3 && move.getFY() == 2) && 
            pawnMoved2Pos[0] == move.getFY() && 
            pawnMoved2Pos[1] == move.getFX() - 1*yDirection &&
            turnsSincePawnMoved2 == 1;
    }
    
    public boolean isCastle(Piece[][] board, Move move)
    {
        if (!(board[move.getIY()][move.getIX()] instanceof King)) return false;
        
        King king = (King)board[move.getIY()][move.getIX()];
        
        if (king.getCanCastle() && move.getIY() == move.getFY())
        {
            //check if can short castle
            if (move.getFX() == 6 && board[move.getIY()][7] instanceof Rook) 
            {
                Rook rook = (Rook)board[move.getIY()][7];
                
                if (rook.getCanCastle() &&
                
                    //check for obstructions
                    board[move.getIY()][5] == null &&
                    board[move.getIY()][6] == null)
                return true;
            }
            
            //check if can long castle
            if (move.getFX() == 2 && board[move.getIY()][0] instanceof Rook) 
            {
                Rook rook = (Rook)board[move.getIY()][0];
                
                if (rook.getCanCastle() &&
                
                    //check for obstructions
                    board[move.getIY()][1] == null &&
                    board[move.getIY()][2] == null &&
                    board[move.getIY()][3] == null)
                return true;
            }
        }
        return false;
    }

    public int[] getPawnMoved2Pos() {return pawnMoved2Pos;}
    
    public Piece[][] getBoard() {return board;}
}