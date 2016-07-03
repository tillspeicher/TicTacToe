package board;

public enum Kind {
    X, O, empty;

    public String toString() {
        if (this == X)
            return "x";
        if (this == O)
            return "o";
        return " ";
    }
    
    public Kind negate() {
        switch (this) {
        case O:
            return X;
        case X:
            return O;
        case empty:
            return empty;
        default:
            return null;
        }
    }

}
