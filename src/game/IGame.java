package game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import board.Board;
import board.IBoard;
import board.Kind;
import player.Player;

public class IGame implements Game {
    Kind winner = null;
    Board board;
    ArrayList<Observer> observerList;

    Player player1;
    Player player2;

    public void run() throws IOException {

        player1.setKind(Kind.X); // Spieler erhalten ihr Kind
        player2.setKind(Kind.O);

        Random r = new Random();
        boolean playerTurn = r.nextBoolean(); // Der Spieler soll zufällig
                                              // starten

        Turn t;
        showBoard();
        while (!isOver()) { // Spiel Schleife
            if (playerTurn) {
                t = getValidTurn(player1);
                board.setPiece(player1.getKind(), t.x, t.y);
                playerTurn = false;
            } else {
                t = getValidTurn(player2);
                board.setPiece(player2.getKind(), t.x, t.y);
                playerTurn = true;
            }
            // BroadCast an alle Observer
            for (Observer o : observerList) {
                o.PieceSetted(t);
            }
            showBoard();
        }

        // BroadCast an alle Observer
        for (Observer o : observerList) {
            o.BoardIsFull();
        }

    }

    public IGame() {
        this.board = new IBoard();
        this.observerList = new ArrayList<Observer>();
    }

    @Override
    public void addObserver(Observer observer) {
        this.observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    @Override
    public boolean hasWinner() {
        return GameUtil.hasWinner(getBoard());
    }

    @Override
    public Kind getWinner() {
        return GameUtil.getWinner(getBoard());
    }

    @Override
    public boolean isOver() {
        return this.board.isFull() || this.hasWinner();
    }

    @Override
    public void showBoard() {
        for (int i = 0; i < 3; i++) {
            String row = "|";
            for (int j = 0; j < 3; j++) {
                row = row + this.board.getPiece(j, i) + "|";
            }
            System.out.println(row);
        }
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public void joinPlayer1(Player player) {
        this.player1 = player;

    }

    @Override
    public void joinPlayer2(Player player) {
        this.player2 = player;
    }

    private Turn getValidTurn(Player p) {
        Turn t = p.getNextTurn();
        while (!(board.canSetPiece(t.x, t.y))) {
            t = p.getNextTurn();
        }
        return t;
    }

}
