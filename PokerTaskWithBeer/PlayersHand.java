package PokerTaskWithBeer;

public class PlayersHand {
    Card card1;
    Card card2;
    Card card3;
    Card card4;
    Card card5;
    Combinations currentCombination;
    @Override
    public String toString() {
        return card1 + ", " + card2 + ", " + card3 + ", " + card4 + ", " + card5 ;
    }

}
