package model.move_tools;

import model.Board;
import model.misc_vars.Colour;
import model.misc_vars.PieceType;

public class Notation {

    public static void stringToNotation(Board board, String notation, Colour colour) {
        // Detect castling moves
        int row = (colour == Colour.WHITE) ? 0 : 7;
        boolean isCapture = false, isCheck = false, isMate = false;
        
        if (notation.contains("x")) 
        {
            notation = notation.replace("x", "");
            isCapture = true;
        }
        
        if (notation.contains("+"))
        {
            notation = notation.replace("+", "");
            isCheck = true;
        }
        
        if (notation.contains("#"))
        {
            notation = notation.replace("#", "");
            isMate = true;
        }
        // if (notation.equals("O-O") || notation.equals("O-O-O")) return convertCastle(board, notation, colour, isCapture, isCheck, isMate);
        
        System.out.println(isCapture + " " + isCheck + " " + isMate); //temp
        
        // Parse move
        // Move move = parseNotation(board, notation, colour, isCapture, isCheck, isMate);
        // if (move == null) return null;

        // return move;
    }

    private static void convertCastle(Board board, String notation, Colour colour, boolean isCapture, boolean isCheck, boolean isMate)  {
        int fX = (notation.length() == 3) ? 6 : 2;
        int y = (colour == Colour.WHITE) ? 0 : 7;
            
        // return new Move(Type.KING, 4, y, fX, y, false, false, false);
    }

    private static void parseNotation(Board board, String notation, Colour colour, boolean isCapture, boolean isCheck, boolean isMate)  {
        // check if valid string
        // for (int i = 0; i < notation.length(); i++) if ("PNBRQKabcdefgh12345678x+=#O-".indexOf(notation.charAt(i)) == -1) return null;
        // if (notation.length() < 2 || notation.length() > 9) return null;
        
        PieceType type;
        int iX = -1, iY = -1, fX = -1, fY = -1;
        
        // Determine piece type
        switch (notation.charAt(0)) 
        {
            case 'K':
                type = PieceType.KING;
                notation = notation.substring(1);
                break;
            case 'Q':
                type = PieceType.QUEEN;
                notation = notation.substring(1);
                break;
            case 'R':
                type = PieceType.ROOK;
                notation = notation.substring(1);
                break;
            case 'B':
                type = PieceType.BISHOP;
                notation = notation.substring(1);
                break;
            case 'N':
                type = PieceType.KNIGHT;
                notation = notation.substring(1);
                break;
            default:
                type = PieceType.PAWN;
        }
        
        // Determine the end squre
        if ("abcdefgh".indexOf(notation.charAt(0)) != -1) fX = fileToX(notation.charAt(notation.length() - 2));
        if ("12345678".indexOf(notation.charAt(1)) != -1) fY = rankToY(notation.charAt(notation.length() - 1));
        
        //set initial square if disambiguated
        if (notation.length() == 3) {
            if ("abcdefgh".indexOf(notation.charAt(0)) != -1) iX = fileToX(notation.charAt(0));
            else iY = rankToY(notation.charAt(0));
        }
        
        else if (notation.length() == 4) {
            if ("12345678".indexOf(notation.charAt(0)) != -1) iX = fileToX(notation.charAt(0));
            if ("abcdefgh".indexOf(notation.charAt(1)) != -1) iY = rankToY(notation.charAt(1));
        }
        
        if (iX == -1 && iY != -1) {
            for (int i = 0; i < 8; i++) {
                if (board.getBoard()[iY][i] != null &&
                    board.getBoard()[iY][i].getType() == type && 
                    board.getBoard()[iY][i].getColour() == colour) 
                    iX = i;
            }
        }
        
        else if (iY == -1 && iX != -1) {
            for (int i = 0; i < 8; i++) {
                if (board.getBoard()[i][iX] != null &&
                    board.getBoard()[i][iX].getType() == type && 
                    board.getBoard()[i][iX].getColour() == colour) 
                    iY = i;
            }
        }
        
        else if (iX == -1 && iY == -1) {
            int[] startPos = findStartPos(board, colour, type, fX, fY);
            // if (startPos == null) return null;
            iX = startPos[0];
            iY = startPos[1];
        }
        // if (iX == -1 || iY == -1 || fX == -1 || fY == -1) return null;
        // return new Move(type, iX, iY, fX, fY, isCapture, isCheck, isMate);
    }
    
    private static int fileToX(char file) { return file - 'a'; }

    // Helper method to convert rank ('1'-'8') to y coordinate (0-7)
    private static int rankToY(char rank) { return rank - '1'; }
    
    private static int[] findStartPos(Board board, Colour colour, Type type, int fX, int fY) {
        int[] result = new int[2];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (board.getBoard()[y][x] != null && board.getBoard()[y][x].getType() == type){
                    // TODO: add logic to check if the piece can move to the destination square
                    switch (type) {
                        case KING:
                            if (true) {
                                result[0] = x;
                                result[1] = y;
                            }
                            break;
                            
                        case QUEEN:
                            if (true) {
                                result[0] = x;
                                result[1] = y;
                            }
                            break;
                            
                        case ROOK:
                            if (true) {
                                result[0] = x;
                                result[1] = y;
                            }
                            break;
                            
                        case BISHOP:
                            if (true) {
                                result[0] = x;
                                result[1] = y;
                            }
                            break;
                            
                        case KNIGHT:
                            if (true)
                            {
                                result[0] = x;
                                result[1] = y;
                            }
                            break;
                            
                        case PAWN:
                            if (true)
                            {
                                result[0] = x;
                                result[1] = y;
                            }
                            break;
                    }
                }
            }
        }
        return result;
    }
}