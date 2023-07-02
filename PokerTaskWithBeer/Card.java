package PokerTaskWithBeer;

public class Card {
    Suits cardSuit;
    Ranks cardRank;

    public Card(Suits suit, Ranks rank) {
        this.cardSuit = suit;
        this.cardRank = rank;
    }

    public int compareTo(Card other) {
        return this.cardRank.ordinal() - other.cardRank.ordinal();
    }

    @Override
    public String toString() {
        return cardRank + " of " + cardSuit;
    }

}
