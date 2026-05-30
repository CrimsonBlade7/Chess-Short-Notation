package ui;

import java.util.Scanner;
import model.Chess;

public class Main {
    public static Scanner input = new Scanner(System.in);

    // EFFECTS: runs a terminal chess game using short algebraic notation
    public static void main(String[] args) {
        Chess chess = new Chess();

        while (true) {
            clearScreen();
            System.out.println(chess.getBoardState().getBoard());
            System.out.println(chess.status());
            if (chess.isGameOver())
                break;

            System.out.print("Move (or quit): ");
            String move = input.nextLine().trim();
            if (move.equalsIgnoreCase("quit") || move.equalsIgnoreCase("q"))
                break;
            if (!chess.makeMove(move)) {
                System.out.println("Invalid or illegal move.");
                enterToContinue();
            }
        }
    }

    // EFFECTS: returns true when the user answers yes and false when the user answers no
    public static boolean playAgain() {
        while (true) {
            switch (input.nextLine().toLowerCase()) {
                case "y", "yes" -> {
                    return true;
                }
                case "n", "no" -> {
                    return false;
                }
                default -> {
                    System.out.println("Invalid Input: Must be [yes/y] or [no/n]");
                    enterToContinue();
                }
            }
        }
    }

    // EFFECTS: waits for the user to press enter
    public static void enterToContinue() {
        System.out.println();
        System.out.println("----------[ENTER] to continue----------");
        System.out.println();
        input.nextLine();
    }

    // MODIFIES: terminal
    // EFFECTS: prints ANSI escape codes that clear many terminals
    public static void clearScreen() { System.out.println("\033[H\033[2J"); }
}
