package ui;
import java.util.*;

import model.Board;
import model.pieces.piece_vars.Colour;
import model.pieces.piece_vars.Type;

public class Main {
    public static Scanner input = new Scanner(System.in);
        
    public static boolean playAgain() {
        while (true) {
            switch (input.nextLine().toLowerCase()) {
                case "y":
                case "yes":
                    return true;
                    
                case "n":
                case "no":
                    return true;
                    
                default:
                    System.out.println("Invalid Input: Must be [yes/y] or [no/n]");
                    System.out.println();
                    enterToContinue();
            }
        }
    }
    
    public static boolean gameIsOver() {
        return false;
    }
    
    public static void enterToContinue() {
        System.out.println();
        System.out.println("----------[ENTER] to continue----------");
        System.out.println();
        input.nextLine();
    }
    
    public static void clearScreen() { System.out.println("\033[H\033[2J"); }
    
    public static void main(String[] args) {

        Board board = new Board();
        
        Colour currentColour = Colour.WHITE;
        String colour = "White";
        boolean running = true;
        
        do {
            do {
                boolean badMove = true;
                while (badMove) {
                    clearScreen();
                    System.out.println(board.boardString());
                    System.out.print("    " + colour + " to move: ");
                    
                    if (move == null)
                    {
                        System.out.println();
                        System.out.println("             Illegal Notation");
                        enterToContinue();
                    }
                }
                
                if (board.move(move, currentColour))
                {
                    colour = (currentColour == Colour.WHITE) ? "Black" : "White";
                    currentColour = (currentColour == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
                }
                else
                {
                    System.out.println();
                    System.out.println("             Illegal Move");
                    enterToContinue();
                }
            }
            while (!gameIsOver());
        }
        while(playAgain());
    }
    
}