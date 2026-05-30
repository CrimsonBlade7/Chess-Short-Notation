package model.move_tools;

import java.util.ArrayList;
import java.util.List;
import model.exceptions.ImpossibleMoveStateException;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.King;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Rook;

public class MoveValidation {

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if move is legal for the current position
    public static boolean isLegalMove(BoardState boardState, Move move) {
        if (!basicMoveStateValid(boardState, move))
            return false;
        if (!moveShapeValid(boardState, move))
            return false;

        BoardState copy = new BoardState(boardState);
        try {
            copy.executeMove(move, true);
        } catch (ImpossibleMoveStateException e) {
            return false;
        }
        return !copy.isInCheck(move.PIECE_1.getColour());
    }

    // REQUIRES: boardState != null and colour != null
    // EFFECTS: returns every legal move available to colour
    public static List<Move> legalMoves(BoardState boardState, Colour colour) {
        List<Move> moves = new ArrayList<>();
        Colour previousTurn = boardState.getCurrentTurn();
        boardState.setCurrentTurn(colour);

        for (Position start : boardState.positionsFor(colour)) {
            Piece piece = boardState.getSquare(start);
            addPieceMoves(boardState, moves, piece, start);
        }

        boardState.setCurrentTurn(previousTurn);
        return moves;
    }

    // REQUIRES: boardState != null, target is in bounds, attackingColour != null
    // EFFECTS: returns true if target is attacked by attackingColour
    public static boolean isSquareAttacked(BoardState boardState, Position target, Colour attackingColour) {
        for (Position start : boardState.positionsFor(attackingColour)) {
            Piece piece = boardState.getSquare(start);
            if (attacksSquare(boardState, piece, start, target))
                return true;
        }
        return false;
    }

    // REQUIRES: boardState != null, piece != null, start is in bounds
    // MODIFIES: moves
    // EFFECTS: adds legal moves for piece at start to moves
    private static void addPieceMoves(BoardState boardState, List<Move> moves, Piece piece, Position start) {
        for (Position end : piece.validPositions(boardState, start)) {
            MoveType type = promotionType(piece, end) ? MoveType.PROMOTION : MoveType.NORMAL;
            Move move = type == MoveType.PROMOTION
                    ? new Move(piece, start, end, null, null, null,
                            NotationConverter.promotionPiece("Q", piece.getColour()), type, boardState)
                    : new Move(piece, start, end, type, boardState);
            if (isLegalMove(boardState, move))
                moves.add(move);
        }

        if (piece instanceof King) {
            addCastleMove(boardState, moves, piece, start, true);
            addCastleMove(boardState, moves, piece, start, false);
        }
    }

    // REQUIRES: boardState != null, piece is a king, start is in bounds
    // MODIFIES: moves
    // EFFECTS: adds a legal castling move when available
    private static void addCastleMove(BoardState boardState, List<Move> moves, Piece piece, Position start,
            boolean kingside) {
        int y = piece.getColour() == Colour.WHITE ? 0 : 7;
        int rookX = kingside ? 7 : 0;
        int kingEndX = kingside ? 6 : 2;
        int rookEndX = kingside ? 5 : 3;
        Position rookStart = new Position(rookX, y);
        Move move = new Move(piece, start, new Position(kingEndX, y), boardState.getSquare(rookStart), rookStart,
                new Position(rookEndX, y), null, MoveType.CASTLING, boardState);
        if (isLegalMove(boardState, move))
            moves.add(move);
    }

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if move's start piece, turn, and destination are plausible
    private static boolean basicMoveStateValid(BoardState boardState, Move move) {
        if (!inBounds(move.START_POS_1) || !inBounds(move.END_POS_1))
            return false;
        Piece piece = boardState.getSquare(move.START_POS_1);
        if (piece == null || piece != move.PIECE_1)
            return false;
        if (piece.getColour() != boardState.getCurrentTurn())
            return false;
        Piece target = boardState.getSquare(move.END_POS_1);
        return target == null || target.getColour() != piece.getColour();
    }

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if move follows the piece's movement rules
    private static boolean moveShapeValid(BoardState boardState, Move move) {
        return switch (move.MOVE_TYPE) {
        case NORMAL -> normalShapeValid(boardState, move);
        case PROMOTION -> promotionShapeValid(boardState, move);
        case EN_PASSANT -> enPassantShapeValid(boardState, move);
        case CASTLING -> castlingShapeValid(boardState, move);
        };
    }

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if a normal move's shape is pseudo-legal
    private static boolean normalShapeValid(BoardState boardState, Move move) {
        return move.PIECE_1.validPositions(boardState, move.START_POS_1).contains(move.END_POS_1)
                && !promotionType(move.PIECE_1, move.END_POS_1)
                && !(move.PIECE_1 instanceof Pawn && move.END_POS_1.equals(boardState.getEnpassantTarget()));
    }

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if a promotion move is pseudo-legal and has a promotion piece
    private static boolean promotionShapeValid(BoardState boardState, Move move) {
        return move.PIECE_1 instanceof Pawn
                && move.PROMOTION_PIECE != null
                && promotionType(move.PIECE_1, move.END_POS_1)
                && move.PIECE_1.validPositions(boardState, move.START_POS_1).contains(move.END_POS_1);
    }

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if an en passant move is legal by shape and target state
    private static boolean enPassantShapeValid(BoardState boardState, Move move) {
        if (!(move.PIECE_1 instanceof Pawn) || !move.END_POS_1.equals(boardState.getEnpassantTarget()))
            return false;
        int dir = move.PIECE_1.getColour() == Colour.WHITE ? 1 : -1;
        Position capturedPos = new Position(move.END_POS_1.X, move.END_POS_1.Y - dir);
        Piece captured = boardState.getSquare(capturedPos);
        return move.START_POS_2 != null
                && move.START_POS_2.equals(capturedPos)
                && captured instanceof Pawn
                && captured == move.PIECE_2
                && captured.getColour() != move.PIECE_1.getColour()
                && Math.abs(move.END_POS_1.X - move.START_POS_1.X) == 1
                && move.END_POS_1.Y - move.START_POS_1.Y == dir
                && boardState.getSquare(move.END_POS_1) == null;
    }

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if a castling move satisfies castling rights and path rules
    private static boolean castlingShapeValid(BoardState boardState, Move move) {
        if (!(move.PIECE_1 instanceof King) || !(move.PIECE_2 instanceof Rook))
            return false;
        Colour colour = move.PIECE_1.getColour();
        int y = colour == Colour.WHITE ? 0 : 7;
        boolean kingside = move.END_POS_1.equals(new Position(6, y));
        boolean queenside = move.END_POS_1.equals(new Position(2, y));
        if (!move.START_POS_1.equals(new Position(4, y)) || (!kingside && !queenside))
            return false;
        if (kingside && !boardState.canCastleKingside(colour))
            return false;
        if (queenside && !boardState.canCastleQueenside(colour))
            return false;

        Position rookStart = new Position(kingside ? 7 : 0, y);
        Position rookEnd = new Position(kingside ? 5 : 3, y);
        if (!rookStart.equals(move.START_POS_2) || !rookEnd.equals(move.END_POS_2))
            return false;
        if (boardState.getSquare(rookStart) != move.PIECE_2 || move.PIECE_2.getColour() != colour)
            return false;

        int step = kingside ? 1 : -1;
        for (int x = 4 + step; x != rookStart.X; x += step) {
            if (boardState.getSquare(new Position(x, y)) != null)
                return false;
        }
        Colour enemy = BoardState.opposite(colour);
        Position transit = new Position(kingside ? 5 : 3, y);
        return !boardState.isInCheck(colour)
                && !isSquareAttacked(boardState, transit, enemy)
                && !isSquareAttacked(boardState, move.END_POS_1, enemy);
    }

    // REQUIRES: piece != null and end is in bounds
    // EFFECTS: returns true if piece must promote by moving to end
    private static boolean promotionType(Piece piece, Position end) {
        return piece instanceof Pawn && (end.Y == 0 || end.Y == 7);
    }

    // REQUIRES: boardState != null, piece != null, start and target are in bounds
    // EFFECTS: returns true if piece attacks target regardless of whose turn it is
    private static boolean attacksSquare(BoardState boardState, Piece piece, Position start, Position target) {
        if (piece instanceof Pawn) {
            int dir = piece.getColour() == Colour.WHITE ? 1 : -1;
            return target.Y - start.Y == dir && Math.abs(target.X - start.X) == 1;
        }
        if (piece instanceof King)
            return Math.max(Math.abs(target.X - start.X), Math.abs(target.Y - start.Y)) == 1;
        return piece.validPositions(boardState, start).contains(target);
    }

    // EFFECTS: returns true if pos is within the board
    private static boolean inBounds(Position pos) {
        return pos != null && 0 <= pos.X && pos.X < 8 && 0 <= pos.Y && pos.Y < 8;
    }
}
