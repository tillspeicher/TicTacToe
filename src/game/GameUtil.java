package game;

import board.Board;
import board.Kind;

public class GameUtil {
    
    public enum GameStatus {
        X_WON, O_WON, DRAW, NOT_FINISHED
    }
    
    public static GameStatus getStatus(Board b) {
        return aggregateGameStatus(check(b, 0, 0, 0, 1, 0, 2), check(b, 1, 0, 1, 1, 1, 2),
                check(b, 2, 0, 2, 1, 2, 2), check(b, 0, 0, 1, 0, 2, 0),
                check(b, 0, 1, 1, 1, 2, 1), check(b, 0, 2, 1, 2, 2, 2),
                check(b, 0, 0, 1, 1, 2, 2), check(b, 0, 2, 1, 1, 2, 0));
    }
    
    public static boolean hasWinner(Board b) {
        GameStatus s = GameUtil.getStatus(b);
        return s == GameStatus.X_WON || s == GameStatus.O_WON;
    }
    
    public static Kind getWinner(Board b) {
        GameStatus s = GameUtil.getStatus(b);
        if (s == GameStatus.X_WON)
            return Kind.X;
        else if (s == GameStatus.O_WON)
            return Kind.O;
        else
            return Kind.empty;
    }
    
    private static GameStatus check(Board board, int x1, int y1, int x2, int y2, int x3, int y3) {
        Kind kind = board.getPiece(x1, y1);
        if (kind == Kind.empty)
            return GameStatus.NOT_FINISHED;
        
        boolean res = (kind == board.getPiece(x2, y2) && kind == board.getPiece(x3, y3) && kind != Kind.empty);

        if (res) {
            if (kind == Kind.X)
                return GameStatus.X_WON;
            else
                return GameStatus.O_WON;
        }
        return GameStatus.DRAW;
    }
    
    private static GameStatus aggregateGameStatus(GameStatus... states) {
        boolean finished = true;
        for (GameStatus s : states) {
            switch (s) {
            case X_WON: return GameStatus.X_WON;
            case O_WON: return GameStatus.O_WON;
            case NOT_FINISHED: finished = false;
            }
        }
        return finished ? GameStatus.DRAW : GameStatus.NOT_FINISHED;
    }

}
