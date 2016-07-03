package player;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import board.Kind;
import game.Game;
import game.GameUtil;
import game.Observer;
import game.Turn;

public class AutoPlayer implements Player, Observer {
    
    private final Game game;
    private Kind kind;
    
    public AutoPlayer(Game game) {
        this.game = game;
    }

    @Override
    public Turn getNextTurn() {
        List<Turn> possibleTurns = generateTurns(game.getBoard(), true);
        
        int zeroPos = -1;
        int i = 0;
        for (Turn t : possibleTurns) {
            Board newBoard = game.getBoard().copy();
            newBoard.setPiece(t.kind, t.x, t.y);
            int score = evaluateState(newBoard, false);
            if (score == 1)
                return t;
            else if (score == 0)
                zeroPos = i;
            i++;
        }
        if (zeroPos >= 0) {
            return possibleTurns.get(zeroPos);
        }
        return possibleTurns.get(0);
    }
    
    private int evaluateState(Board b, boolean max) {
        if (GameUtil.hasWinner(b)) {
            return GameUtil.getWinner(b) == kind ? 1 : -1;
        }
        if (b.isFull()) {
           return 0;
        }
        
        List<Turn> turns = generateTurns(b, max);
        
        int bestRes = max ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Turn t : turns) {
            Board newBoard = b.copy();
            newBoard.setPiece(t.kind, t.x, t.y);
            int turnRes = evaluateState(newBoard, !max);
            if (max) {
                bestRes = turnRes > bestRes ? turnRes : bestRes;
            } else {
                bestRes = turnRes < bestRes ? turnRes : bestRes;
            }
        }
        return bestRes;
    }
    
    private List<Turn> generateTurns(Board b, boolean max) {
        List<Turn> possibleTurns = new ArrayList<>();
        
        Kind[][] boardFields = b.getBoard();
        for (int i = 0; i < boardFields.length; i++) {
            for (int j = 0; j < boardFields[i].length; j++) {
                if (boardFields[i][j] == Kind.empty) {
                    Turn t = new Turn(i, j, max ? kind : kind.negate());
                    possibleTurns.add(t);
                }
            }
        }
        return possibleTurns;
    }

    @Override
    public void setKind(Kind k) {
        this.kind = k;
    }

    @Override
    public Kind getKind() {
        return this.kind;
    }

    @Override
    public void PieceSetted(Turn t) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void BoardIsFull() {
        // TODO Auto-generated method stub
        
    }

}
