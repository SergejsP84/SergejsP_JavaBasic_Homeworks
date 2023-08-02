package lesson5.Battleships;

public class Ship {

    private int decks;
    private boolean isVertical;

    public Ship(int decks) {
        this.decks = decks;
    }

    public int getDecks() {
        return decks;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public Ship(int decks, boolean isVertical) {
        this.decks = decks;
        this.isVertical = isVertical;
    }

    public void setDecks(int decks) {
        this.decks = decks;
    }
}
