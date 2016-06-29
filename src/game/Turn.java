package game;

import board.Kind;

/**
 * Diese Klasse repräsentiert einen Spielzug.
 */
public class Turn {

    public final int x;
    public final int y;
    public final Kind kind;

    public Turn(int x, int y, Kind kind) {
        this.x = x;
        this.y = y;
        this.kind = kind;
    }

}
