package model.move_tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.misc_vars.Colour;
import model.misc_vars.MoveType;
import model.pieces.Bishop;
import model.pieces.King;
import model.pieces.Knight;
import model.pieces.Pawn;
import model.pieces.Piece;
import model.pieces.Queen;
import model.pieces.Rook;

public class NotationConverter {

    private static final Pattern SAN_PATTERN = Pattern.compile(
            "^([NBRQK])?([a-h])?([1-8])?(x)?([a-h][1-8])(=([NBRQ]))?(\\+|#)?$",
            Pattern.CASE_INSENSITIVE);

    // REQUIRES: moveString != null and board != null
    // EFFECTS: returns the legal move represented by moveString on board with white
    // to move, or null if the notation is invalid or illegal
    public static Move stringToMove(String moveString, Board board) {
        return stringToMove(moveString, new BoardState(board));
    }

    // REQUIRES: moveString != null and boardState != null
    // EFFECTS: returns the legal move represented by moveString, or null if the
    // notation is invalid, illegal, or ambiguous
    public static Move stringToMove(String moveString, BoardState boardState) {
        String notation = moveString.trim();
        if (notation.equals("O-O") || notation.equals("0-0"))
            return castleMove(boardState, true);
        if (notation.equals("O-O-O") || notation.equals("0-0-0"))
            return castleMove(boardState, false);

        Matcher matcher = SAN_PATTERN.matcher(notation);
        if (!matcher.matches())
            return null;

        String pieceSymbol = matcher.group(1) == null ? "P" : matcher.group(1).toUpperCase();
        String fileHint = matcher.group(2) == null ? null : matcher.group(2).toLowerCase();
        String rankHint = matcher.group(3);
        boolean captureMarked = matcher.group(4) != null;
        Position end = algebraicToPosition(matcher.group(5));
        String promotionSymbol = matcher.group(7);

        List<Move> legalMatches = new ArrayList<>();
        for (Position start : boardState.positionsFor(boardState.getCurrentTurn())) {
            Piece piece = boardState.getSquare(start);
            if (!piece.getSymbol().equalsIgnoreCase(pieceSymbol))
                continue;
            if (fileHint != null && start.X != fileHint.charAt(0) - 'a')
                continue;
            if (rankHint != null && start.Y != Character.getNumericValue(rankHint.charAt(0)) - 1)
                continue;

            Move move = buildMove(boardState, piece, start, end, promotionSymbol);
            if (move != null && captureMarkerMatches(boardState, move, captureMarked)
                    && MoveValidation.isLegalMove(boardState, move))
                legalMatches.add(move);
        }

        return legalMatches.size() == 1 ? legalMatches.get(0) : null;
    }

    // REQUIRES: symbol is one of N, B, R, Q and colour != null
    // EFFECTS: returns a new promotion piece for symbol and colour
    public static Piece promotionPiece(String symbol, Colour colour) {
        return switch (symbol.toUpperCase()) {
        case "N" -> new Knight(colour);
        case "B" -> new Bishop(colour);
        case "R" -> new Rook(colour);
        case "Q" -> new Queen(colour);
        default -> throw new IllegalArgumentException("Invalid promotion piece: " + symbol);
        };
    }

    // REQUIRES: boardState != null
    // EFFECTS: returns a legal castling move for the current turn, or null
    private static Move castleMove(BoardState boardState, boolean kingside) {
        Colour colour = boardState.getCurrentTurn();
        int y = colour == Colour.WHITE ? 0 : 7;
        Position kingStart = new Position(4, y);
        Position rookStart = new Position(kingside ? 7 : 0, y);
        Piece king = boardState.getSquare(kingStart);
        Piece rook = boardState.getSquare(rookStart);
        if (!(king instanceof King) || !(rook instanceof Rook))
            return null;

        Move move = new Move(king, kingStart, new Position(kingside ? 6 : 2, y), rook, rookStart,
                new Position(kingside ? 5 : 3, y), null, MoveType.CASTLING, boardState);
        return MoveValidation.isLegalMove(boardState, move) ? move : null;
    }

    // REQUIRES: square is valid algebraic notation such as e4
    // EFFECTS: converts square to a zero-based position
    public static Position algebraicToPosition(String square) {
        return new Position(Character.toLowerCase(square.charAt(0)) - 'a',
                Character.getNumericValue(square.charAt(1)) - 1);
    }

    // REQUIRES: pos is within the board
    // EFFECTS: converts pos to algebraic notation such as e4
    public static String positionToAlgebraic(Position pos) {
        return "" + (char) ('a' + pos.X) + (pos.Y + 1);
    }

    // REQUIRES: boardState != null, piece != null, start and end are in bounds
    // EFFECTS: returns the move object implied by the notation fields, or null
    private static Move buildMove(BoardState boardState, Piece piece, Position start, Position end,
            String promotionSymbol) {
        if (piece instanceof Pawn && end.equals(boardState.getEnpassantTarget())
                && boardState.getSquare(end) == null && start.X != end.X) {
            int dir = piece.getColour() == Colour.WHITE ? 1 : -1;
            Position capturedPos = new Position(end.X, end.Y - dir);
            return new Move(piece, start, end, boardState.getSquare(capturedPos), capturedPos, null,
                    null, MoveType.EN_PASSANT, boardState);
        }

        if (piece instanceof Pawn && (end.Y == 0 || end.Y == 7)) {
            if (promotionSymbol == null)
                return null;
            return new Move(piece, start, end, null, null, null,
                    promotionPiece(promotionSymbol, piece.getColour()), MoveType.PROMOTION, boardState);
        }

        if (promotionSymbol != null)
            return null;
        return new Move(piece, start, end, MoveType.NORMAL, boardState);
    }

    // REQUIRES: boardState != null and move != null
    // EFFECTS: returns true if capture marker agrees with whether move captures
    private static boolean captureMarkerMatches(BoardState boardState, Move move, boolean captureMarked) {
        boolean isCapture = move.MOVE_TYPE == MoveType.EN_PASSANT || boardState.getSquare(move.END_POS_1) != null;
        return captureMarked == isCapture;
    }
}
