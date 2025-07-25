package ui;

import java.util.*;
import model.Board;
import model.misc_vars.Colour;

public class Main {
    public static Scanner input = new Scanner(System.in);
        
    public static boolean playAgain() {

        while (true) {
            switch (input.nextLine().toLowerCase()) {
                case "y", "yes" -> {
                    return true;
                }
                case "n", "no" -> {
                    return true;
                }
                    
                default -> {
                    System.out.println("Invalid Input: Must be [yes/y] or [no/n]");
                    System.out.println();
                    enterToContinue();
                }
            }
        }
    }
    
    // TODO: implement gameIsOver
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
    
    // TODO: Remove later, for testing the board
    public static void main(String[] args) {

        Board board = new Board();
        
        Colour currentColour = Colour.WHITE;
        boolean running = true;
        
        do {
            clearScreen();
            System.out.println(board);

            System.out.print("Move: ");
            int move = input.nextInt();
            int ix = move / 1000 - 1;
            int iy = move % 1000 / 100 - 1;
            int fx = move % 100 / 10 - 1;
            int fy = move % 10 - 1;
            System.out.println();

            // board.move(ix, iy, fx, fy, currentColour);
            currentColour = (currentColour == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
        } while(running);
    }

//     public static void main(String[] args) {

//         Board board = new Board();
        
//         Colour currentColour = Colour.WHITE;
//         boolean running = true;
        
//         do {
//             clearScreen();
//             System.out.println(board);

//             System.out.print("Move: ");
//             int move = input.nextInt();
//             int ix = move / 1000 - 1;
//             int iy = move % 1000 / 100 - 1;
//             int fx = move % 100 / 10 - 1;
//             int fy = move % 10 - 1;
//             System.out.println();

//             // board.move(ix, iy, fx, fy, currentColour);
//             currentColour = (currentColour == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
//         } while(running);
//     }
// }
}