package model;

import java.util.ArrayList;
import java.util.List;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.move_tools.Move;
import model.move_tools.Position;
import model.pieces.*;

public class Board implements Cloneable {

    private Piece[][] board;
    private List<Piece> pieceList;

    public Board() {
        initializeBoard();
    }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    private void initializeBoard() {

        board = new Piece[8][8];
        // Initialize white pieces
        pieceList = new ArrayList<>();

        Rook whiteRook1 = new Rook(Colour.WHITE, new Position(0, 0));
        Knight whiteKnight1 = new Knight(Colour.WHITE, new Position(1, 0));
        Bishop whiteBishop1 = new Bishop(Colour.WHITE, new Position(2, 0));
        Queen whiteQueen = new Queen(Colour.WHITE, new Position(3, 0));
        King whiteKing = new King(Colour.WHITE, new Position(4, 0));
        Bishop whiteBishop2 = new Bishop(Colour.WHITE, new Position(5, 0));
        Knight whiteKnight2 = new Knight(Colour.WHITE, new Position(6, 0));
        Rook whiteRook2 = new Rook(Colour.WHITE, new Position(7, 0));

        board[0][0] = whiteRook1;
        board[0][1] = whiteKnight1;
        board[0][2] = whiteBishop1;
        board[0][3] = whiteQueen;
        board[0][4] = whiteKing;
        board[0][5] = whiteBishop2;
        board[0][6] = whiteKnight2;
        board[0][7] = whiteRook2;

        pieceList.add(whiteRook1);
        pieceList.add(whiteKnight1);
        pieceList.add(whiteBishop1);
        pieceList.add(whiteQueen);
        pieceList.add(whiteKing);
        pieceList.add(whiteBishop2);
        pieceList.add(whiteKnight2);
        pieceList.add(whiteRook2);

        for (int i = 0; i < 8; i++) {
            Pawn whitePawn = new Pawn(Colour.WHITE, new Position(i, 1));
            Pawn blackPawn = new Pawn(Colour.BLACK, new Position(i, 6));
            board[1][i] = whitePawn;
            board[6][i] = blackPawn;
            pieceList.add(whitePawn);
            pieceList.add(blackPawn);
        }

        // Initialize black pieces
        Rook blackRook1 = new Rook(Colour.BLACK, new Position(0, 7));
        Knight blackKnight1 = new Knight(Colour.BLACK, new Position(1, 7));
        Bishop blackBishop1 = new Bishop(Colour.BLACK, new Position(2, 7));
        Queen blackQueen = new Queen(Colour.BLACK, new Position(3, 7));
        King blackKing = new King(Colour.BLACK, new Position(4, 7));
        Bishop blackBishop2 = new Bishop(Colour.BLACK, new Position(5, 7));
        Knight blackKnight2 = new Knight(Colour.BLACK, new Position(6, 7));
        Rook blackRook2 = new Rook(Colour.BLACK, new Position(7, 7));

        board[7][0] = blackRook1;
        board[7][1] = blackKnight1;
        board[7][2] = blackBishop1;
        board[7][3] = blackQueen;
        board[7][4] = blackKing;
        board[7][5] = blackBishop2;
        board[7][6] = blackKnight2;
        board[7][7] = blackRook2;

        pieceList.add(blackRook1);
        pieceList.add(blackKnight1);
        pieceList.add(blackBishop1);
        pieceList.add(blackQueen);
        pieceList.add(blackKing);
        pieceList.add(blackBishop2);
        pieceList.add(blackKnight2);
        pieceList.add(blackRook2);
    }

    public List<Piece> getPieceList() { return pieceList; }

    // MODIFIES: board
    // EFFECTS: sets the board to the initial chess setup
    public void resetBoard() { initializeBoard(); }

    public Piece[][] getBoard() { return board; }

    // REQUIRES: pos is within the bounds of the board (0 <= x, y < 8)
    // board != null
    // EFFECTS: returns the piece at the specified position, or null if the square
    // is
    public Piece getSquare(Position pos) { return board[pos.Y][pos.X]; }

    @Override
    public String toString() {

        String result = "";
        result += "                  Chess\n";
        result += "    |---|---|---|---|---|---|---|---|\n";
        for (int y = 7; y >= 0; y--) {
            result += y + 1 + "   | ";
            for (int x = 0; x < 8; x++) {
                Piece currentPiece = board[y][x];
                if (currentPiece == null)
                    result += "  | ";
                else if (currentPiece.getColour() == Colour.BLACK)
                    result += currentPiece.getSymbol().toLowerCase() + " | ";
                else
                    result += currentPiece.getSymbol() + " | ";
            }
            result += "\n    |---|---|---|---|---|---|---|---|\n";
        }
        result += "\n      a   b   c   d   e   f   g   h\n\n";

        return result;
    }

    // REQUIRES: move is legal and MoveType is not null
    // MODIFIES: board
    // EFFECTS: Moves a piece from the initial square (ix, iy) to the final square
    // (fx, fy)
    public void executeMove(Move move) {
        switch (move.MOVETYPE) {
            case NORMAL -> handleNormalMove(move);
            case KINGSIDE_CASTLE, QUEENSIDE_CASTLE -> handleCastling(move);
            case EN_PASSANT -> handleEnPassant(move);
            case PROMOTION -> handlePromotion(move);
            default -> throw new IllegalArgumentException("Invalid move type: " + move.MOVETYPE);
        }
    }

    // REQUIRES: move.MOVETYPE == MoveType.NORMAL, move is legal
    // MODIFIES: board
    // EFFECTS: Repositions the piece on the board and updates its position
    private void repositionPiece(int ix, int iy, int fx, int fy) {
        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;
        board[fy][fx].setPos(new Position(fx, fy));
    }

    // REQUIRES: move.MOVETYPE == MoveType.NORMAL, move is legal
    // MODIFIES: board
    // EFFECTS: Handles normal moves for the specified move with no captures
    private void handleNormalMove(Move move) {
        int ix = move.PIECE.getX();
        int iy = move.PIECE.getY();
        int fx = move.POS.X;
        int fy = move.POS.Y;

        repositionPiece(ix, iy, fx, fy);
    }

    // REQUIRES: move.MOVETYPE == MoveTag.KINGSIDE_CASTLE or
    // MoveTag.QUEENSIDE_CASTLE and casting is legal
    // MODIFIES: board
    // EFFECTS: Handles castling moves for the specified move
    private void handleCastling(Move move) {
        int row = move.PIECE.getColour() == Colour.WHITE ? 0 : 7;
        if (null == move.MOVETYPE) {
            throw new IllegalArgumentException("Invalid castling move type: " + move.MOVETYPE);
        }
        else switch (move.MOVETYPE) {
            case KINGSIDE_CASTLE:
                repositionPiece(4, row, 6, row);
                repositionPiece(7, row, 5, row);
                break;
            case QUEENSIDE_CASTLE:
                repositionPiece(4, row, 2, row);
                repositionPiece(0, row, 3, row);
                break;
            default:
                throw new IllegalArgumentException("Invalid castling move type: " + move.MOVETYPE);
        }

    }

    // TODO: add method to handle en passant
    // REQUIRES: move.getMoveTags == MoveTag.EN_PASSANT and move is legal
    // MODIFIES: board
    // EFFECTS: Handles en passant moves for the specified move
    private void handleEnPassant(Move move) {
        int ix = move.PIECE.getX();
        int iy = move.PIECE.getY();
        int fx = move.POS.X;
        int fy = move.POS.Y;

        // Remove the captured pawn
        if (move.PIECE.getColour() == Colour.WHITE) {
            board[fy - 1][fx] = null; // Capture the black pawn
        }
        else {
            board[fy + 1][fx] = null; // Capture the white pawn
        }

        // Move the piece to the target square
        board[fy][fx] = board[iy][ix];
        board[iy][ix] = null;

        board[fy][fx].setPos(new Position(fx, fy));
    }

    // TODO: handle promotion, add pieces to list
    // REQUIRES: move.getMoveTag == MoveTag.PROMOTION and move is legal
    // MODIFIES: board
    // EFFECTS: Handles promotion moves for the specified move
    private void handlePromotion(Move move) {
        int fx = move.POS.X;
        int fy = move.POS.Y;
        int ix = move.PIECE.getX();
        int iy = move.PIECE.getY();

        // Remove the pawn from the board
        board[iy][ix] = null;
        board[fy][fx] = null;

        // Create a new piece based on the promotion type
        if (move.MOVETYPE == MoveType.PROMOTION) {
            // board[move.PIECE.getColour() == Colour.WHITE ? 7 : 0][fx] = move.getPromotePiece();
            // Promote to the specified piece
        }
    }

    // REQUIRES: board != null
    // MODIFIES: board
    // EFFECTS: Returns a deep clone of the board
    @Override
    public Board clone() throws CloneNotSupportedException {
        Board newBoard = new Board();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = newBoard.getSquare(new Position(x, y));
                this.board[y][x] = piece != null ? piece.clone() : null;
            }
        }

        for (Piece piece : newBoard.pieceList) {
            this.pieceList.add(piece.clone());
        }
        return null;
    }
}