package PokerTaskWithBeer;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Comparator;

public class PokerTaskWithBeer {
    public static void main(String[] args) {
        int numberOfCards = Suits.values().length * Ranks.values().length;
        int cardsForPlayer = 5;
        int players;
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        do {
            System.out.println("Please enter the number of players");
            players = scanner.nextInt();
        } while (players < 1 || players > numberOfCards / cardsForPlayer);
        System.out.println("The current number of players is " + players);
        System.out.println();
        Card[] deck = initializeDeck(numberOfCards);
//        System.out.println("Unshuffled deck: " + Arrays.toString(deck));

        Card[] shuffledDeck = shuffleTheDeck(numberOfCards, deck);
//        System.out.println("Shuffled deck: " + Arrays.toString(shuffledDeck));
        PlayersHand[] allPlayers = dealCards(players, cardsForPlayer, shuffledDeck);
        if (!scanForMultipleEquivalentHands(allPlayers)) declareWinner(allPlayers);
        else handleMultiples(allPlayers);
    }

    public static Card[] initializeDeck(int numberOfCards) {
        Card[] myDeck = new Card[numberOfCards];
        Ranks rankID;
        Suits suitID;
        numberOfCards = Suits.values().length * Ranks.values().length;
        for (int i = 0; i < Ranks.values().length; i++) {
            rankID = Ranks.values()[i];
            for (int j = 0; j < Suits.values().length; j++) {
                suitID = Suits.values()[j];
                myDeck[Suits.values().length * i + j] = new Card(suitID, rankID);
            }
        }
        return myDeck;
    }

    public static Card[] shuffleTheDeck(int numberOfCards, Card[] rawDeck) {
            Random random = new Random();
            for (int i = 0; i < numberOfCards; i++) {
            int card = i + (random.nextInt(numberOfCards - i));
                Card temp = rawDeck[card];
                rawDeck[card] = rawDeck[i];
                rawDeck[i] = temp;
        }
        return rawDeck;
    }

    public static PlayersHand[] dealCards(int players, int cardsForPlayer, Card[] shuffledDeck) {
        PlayersHand[] allPlayers = new PlayersHand[players];
        int extraIterator = 0;
        int playerIndex = 0;
        for (int i = 0; i < players * cardsForPlayer; i++) {
            if (extraIterator == 0) {
                allPlayers[playerIndex] = new PlayersHand();
            }
            switch (extraIterator) {
                case 0:
                    allPlayers[playerIndex].card1 = shuffledDeck[i];
                    break;
                case 1:
                    allPlayers[playerIndex].card2 = shuffledDeck[i];
                    break;
                case 2:
                    allPlayers[playerIndex].card3 = shuffledDeck[i];
                    break;
                case 3:
                    allPlayers[playerIndex].card4 = shuffledDeck[i];
                    break;
                case 4:
                    allPlayers[playerIndex].card5 = shuffledDeck[i];
                    break;
            }
            extraIterator++;
            if (extraIterator == cardsForPlayer) {
                extraIterator = 0;
                playerIndex++;
            }
        }
        for (int i = 0; i < players; i++) {
            System.out.println("Player " + (i + 1) + "'s hand: " + allPlayers[i].toString());
            displayHand(evaluateHand(allPlayers[i]), i, allPlayers[i]);
        }
        return allPlayers;
    }

    public static boolean checkForSequence (PlayersHand onePlayer) {
        boolean hasSequence = false;
        boolean hasTwo = false;
        boolean hasThree = false;
        boolean hasFour = false;
        boolean hasFive = false;
        boolean hasSix = false;
        boolean hasSeven = false;
        boolean hasEight = false;
        boolean hasNine = false;
        boolean hasTen = false;
        boolean hasJack = false;
        boolean hasQueen = false;
        boolean hasKing = false;
        boolean hasAce = false;
        Card currentCard = null;
        for (int i = 0; i < 5; i++) {
            if (i == 0) currentCard = onePlayer.card1;
            if (i == 1) currentCard = onePlayer.card2;
            if (i == 2) currentCard = onePlayer.card3;
            if (i == 3) currentCard = onePlayer.card4;
            if (i == 4) currentCard = onePlayer.card5;

            if (currentCard.cardRank == Ranks.TWO) hasTwo = true;
            if (currentCard.cardRank == Ranks.THREE) hasThree = true;
            if (currentCard.cardRank == Ranks.FOUR) hasFour = true;
            if (currentCard.cardRank == Ranks.FIVE) hasFive = true;
            if (currentCard.cardRank == Ranks.SIX) hasSix = true;
            if (currentCard.cardRank == Ranks.SEVEN) hasSeven = true;
            if (currentCard.cardRank == Ranks.EIGHT) hasEight = true;
            if (currentCard.cardRank == Ranks.NINE) hasNine = true;
            if (currentCard.cardRank == Ranks.TEN) hasTen = true;
            if (currentCard.cardRank == Ranks.JACK) hasJack = true;
            if (currentCard.cardRank == Ranks.QUEEN) hasQueen = true;
            if (currentCard.cardRank == Ranks.KING) hasKing = true;
            if (currentCard.cardRank == Ranks.ACE) hasAce = true;
        }
        if (hasAce && hasTwo && hasThree && hasFour && hasFive) hasSequence = true;
        if (hasTwo && hasThree && hasFour && hasFive && hasSix) hasSequence = true;
        if (hasThree && hasFour && hasFive && hasSix && hasSeven) hasSequence = true;
        if (hasFour && hasFive && hasSix && hasSeven && hasEight) hasSequence = true;
        if (hasFive && hasSix && hasSeven && hasEight && hasNine) hasSequence = true;
        if (hasSix && hasSeven && hasEight && hasNine && hasTen) hasSequence = true;
        if (hasSeven && hasEight && hasNine && hasTen && hasJack) hasSequence = true;
        if (hasEight && hasNine && hasTen && hasJack && hasQueen) hasSequence = true;
        if (hasNine && hasTen && hasJack && hasQueen && hasKing) hasSequence = true;
        if (hasTen && hasJack && hasQueen && hasKing && hasAce) hasSequence = true;
        return hasSequence;
    }

    public static boolean checkForFlush (PlayersHand onePlayer) {
        boolean hasFlush = false;
        if (onePlayer.card1.cardSuit == onePlayer.card2.cardSuit
                && onePlayer.card1.cardSuit == onePlayer.card3.cardSuit
                && onePlayer.card1.cardSuit == onePlayer.card4.cardSuit
                && onePlayer.card1.cardSuit == onePlayer.card5.cardSuit) hasFlush = true;
        return hasFlush;
    }

    public static boolean checkForRoyal (PlayersHand onePlayer) {
        boolean hasRoyal = false;
        boolean[] hasRank = new boolean[Ranks.values().length]; // запилил массив булевых для заполнения сверками с ранжирами карт из Енума Ranks, равный длине массива, сожержащего все значения з этого самого Енума
        Card[] cards = {onePlayer.card1, onePlayer.card2, onePlayer.card3, onePlayer.card4, onePlayer.card5};
        for (Card card : cards) {
            hasRank[card.cardRank.ordinal()] = true;
        }
        if (hasRank[Ranks.TEN.ordinal()] && hasRank[Ranks.JACK.ordinal()] && hasRank[Ranks.QUEEN.ordinal()] && hasRank[Ranks.KING.ordinal()] && hasRank[Ranks.ACE.ordinal()]) hasRoyal = true;
        return hasRoyal;
    }

    public static int determineHighCard (PlayersHand onePlayer) {
        int cardRank = 0;
        Card[] cards = {onePlayer.card1, onePlayer.card2, onePlayer.card3, onePlayer.card4, onePlayer.card5};
        for (Card card : cards) {
            if (card.cardRank.ordinal() > cardRank) cardRank = card.cardRank.ordinal();
        }
        return cardRank;
    }

    public static int checkForPair (PlayersHand onePlayer) {
        Card[] cards = {onePlayer.card1, onePlayer.card2, onePlayer.card3, onePlayer.card4, onePlayer.card5};
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                if (cards[i].cardRank == cards[j].cardRank) {
                    return cards[i].cardRank.ordinal();
                }
            }
        }
        return -1;
    }

    public static boolean checkForTwoPairs (PlayersHand onePlayer) {
        Card[] cards = {onePlayer.card1, onePlayer.card2, onePlayer.card3, onePlayer.card4, onePlayer.card5};
        Card firstPairCard1 = null;
        Card firstPairCard2 = null;
        byte iteratorAuxilia = 0;
        Card[] shortenedArray = new Card[3];
        boolean firstPairTrigger = false;
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                if (cards[i].cardRank == cards[j].cardRank) {
                    firstPairCard1 = cards[i];
                    firstPairCard2 = cards[j];
                    firstPairTrigger = true;
                }
            }
        }
        if (firstPairTrigger) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] != firstPairCard1 && cards[i] != firstPairCard2) {
                    shortenedArray[iteratorAuxilia] = cards[i];
                    iteratorAuxilia++;
                }
            }
            for (int i = 0; i < shortenedArray.length; i++) {
                for (int j = i + 1; j < shortenedArray.length; j++) {
                    if (shortenedArray[i].cardRank == shortenedArray[j].cardRank) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public static boolean checkForTrips (PlayersHand onePlayer) {
        Card[] cards = {onePlayer.card1, onePlayer.card2, onePlayer.card3, onePlayer.card4, onePlayer.card5};
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                for (int k = j + 1; k < cards.length; k++) {
                    if (cards[i].cardRank == cards[j].cardRank && cards[i].cardRank == cards[k].cardRank) {
                        return true;
                    }
                }
            }
        }
            return false;
        }


    public static boolean checkForFullHouse (PlayersHand onePlayer) {
        Card[] cards = {onePlayer.card1, onePlayer.card2, onePlayer.card3, onePlayer.card4, onePlayer.card5};
        Card tripsCard1 = null;
        Card tripsCard2 = null;
        Card tripsCard3 = null;
        Card[] shortenedArray = new Card[2];
        byte iteratorAuxilia = 0;
        boolean tripsExistsTrigger = false;
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                for (int k = j + 1; k < cards.length; k++) {
                    if (cards[i].cardRank == cards[j].cardRank && cards[i].cardRank == cards[k].cardRank) {
                        tripsCard1 = cards[i];
                        tripsCard2 = cards[j];
                        tripsCard3 = cards[k];
                        tripsExistsTrigger = true;
                    }
                }
            }
        }
        if (tripsExistsTrigger) {
            for (int i = 0; i < cards.length; i++) {
                if (cards[i] != tripsCard1 && cards[i] != tripsCard2 && cards[i] != tripsCard3) {
                    shortenedArray[iteratorAuxilia] = cards[i];
                    iteratorAuxilia++;
                }
                if (iteratorAuxilia == 2) {
                    break;
                }
            }
        if (shortenedArray[0].cardRank == shortenedArray[1].cardRank) return true;
        }
        return false;
    }

    public static boolean checkForQuad (PlayersHand onePlayer) {
        Card[] cards = {onePlayer.card1, onePlayer.card2, onePlayer.card3, onePlayer.card4, onePlayer.card5};
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                for (int k = j + 1; k < cards.length; k++) {
                    for (int l = k + 1; l < cards.length; l++) {
                    if (cards[i].cardRank == cards[j].cardRank && cards[i].cardRank == cards[k].cardRank && cards[i].cardRank == cards[l].cardRank ) {
                        return true;
                    }
                    }
                }
            }
        }
        return false;
    }

    public static int evaluateHand(PlayersHand onePlayer) {
        // Rank designations - reminder for myself
        // Royal Flush - 10, Straight Flush - 9, Four of a Kind - 8, Full House - 7
        // Flush - 6, Straight - 5, Three of a Kind - 4,
        // Two Pairs - 3, Pair - 2, High Card - 1
        int rank = 0;
        if (determineHighCard(onePlayer) > 0) rank = 1;
        if (checkForPair(onePlayer) > 0) rank = 2;
        if (checkForTwoPairs(onePlayer)) rank = 3;
        if (checkForTrips(onePlayer)) rank = 4;
        if (checkForSequence(onePlayer)) rank = 5;
        if (checkForFlush(onePlayer)) rank = 6;
        if (checkForFullHouse(onePlayer)) rank = 7;
        if (checkForQuad(onePlayer)) rank = 8;
        if (checkForSequence(onePlayer) && checkForFlush(onePlayer)) {
            rank = 9;
            if (checkForRoyal(onePlayer)) rank = 10;
        }
        return rank;
    }

    public static void displayHand (int rank, int playerNum, PlayersHand currentHand) {

        currentHand.currentCombination = Combinations.values()[rank - 1];
        System.out.println("Player " + (playerNum + 1) + " has a " + currentHand.currentCombination + "!");
    }

    public static boolean scanForMultipleEquivalentHands (PlayersHand[] allPlayers) {
        boolean multipleHandsPresent = false;
        PlayersHand topHand = null;
        int topHandRank = 0;
        for (int i = 0; i < allPlayers.length; i++) {
            if (evaluateHand(allPlayers[i]) > topHandRank) topHandRank = evaluateHand(allPlayers[i]);
        }

        for (int i = 0; i < allPlayers.length; i++) {
            for (int j = i + 1; j < allPlayers.length; j++) {
             if (evaluateHand(allPlayers[i]) == evaluateHand(allPlayers[j]) && evaluateHand(allPlayers[i]) == topHandRank) {
                 multipleHandsPresent = true;
                 break;
             }
            }
        }
        return multipleHandsPresent;
    }

    public static void declareWinner (PlayersHand[] allPlayers) {
        PlayersHand topHand = null;
        int topHandRank = 0;
        int winnerIndex = 0;
        for (int i = 0; i < allPlayers.length; i++) {
            if (evaluateHand(allPlayers[i]) > topHandRank) topHandRank = evaluateHand(allPlayers[i]);
        }
        for (int i = 0; i < allPlayers.length; i++) {
            if (evaluateHand(allPlayers[i]) == topHandRank) {
                winnerIndex = i + 1;
                topHand = allPlayers[i];
            }
        }
        System.out.println();
        System.out.println("Player " + winnerIndex + " wins with a " + topHand.currentCombination.values()[topHandRank - 1] + "!!!");
    }

    public static void handleMultiples(PlayersHand[] allPlayers) {
        PlayersHand topHand = null;
        int potentialWinnerCounter = 0;
        int topHandRank = 0;
        int iteratorAuxilia = 0;
        for (int i = 0; i < allPlayers.length; i++) {
            if (evaluateHand(allPlayers[i]) > topHandRank) topHandRank = evaluateHand(allPlayers[i]);
        }
        for (int i = 0; i < allPlayers.length; i++) {
            if (evaluateHand(allPlayers[i]) == topHandRank) topHand = allPlayers[i];
        }
        for (int i = 0; i < allPlayers.length; i++) {
            if (evaluateHand(allPlayers[i]) == evaluateHand(topHand)) potentialWinnerCounter++;
        }
        PlayersHand[] winningHands = new PlayersHand[potentialWinnerCounter];
        int[] winnerIndexes = new int[potentialWinnerCounter];
        for (int i = 0; i < allPlayers.length; i++) {
            if (evaluateHand(allPlayers[i]) == evaluateHand(topHand)) {
                winningHands[iteratorAuxilia] = allPlayers[i];
                winnerIndexes[iteratorAuxilia] = i+1;
                iteratorAuxilia++;
            }
        }
//        System.out.println("Found " + potentialWinnerCounter + " hands with a " + topHand.currentCombination.values()[topHandRank - 1]);
//        for (int i = 0; i < winningHands.length; i++) System.out.println(winningHands[i]);
//        System.out.println(Arrays.toString(winnerIndexes));
        // Rank designations - reminder for myself
        // Royal Flush - 10, Straight Flush - 9, Four of a Kind - 8, Full House - 7
        // Flush - 6, Straight - 5, Three of a Kind - 4,
        // Two Pairs - 3, Pair - 2, High Card - 1
        if (topHandRank == 1) handleHighCards(winningHands, winnerIndexes);
        if (topHandRank == 2) handlePairs(winningHands, winnerIndexes);
        if (topHandRank == 3) handleTwoPairs(winningHands, winnerIndexes);
        if (topHandRank == 4) handleTrips(winningHands, winnerIndexes);
        if (topHandRank == 5) handleStraights(winningHands, winnerIndexes);
        if (topHandRank == 6) handleFlushes(winningHands, winnerIndexes);
        if (topHandRank == 7) handleFullHouses(winningHands, winnerIndexes);
        if (topHandRank == 8) handleQuads(winningHands, winnerIndexes);
        if (topHandRank == 9) handleStraightFlushes(winningHands, winnerIndexes);
        if (topHandRank == 10) handleRoyalFlushes(winningHands, winnerIndexes);
    }
    
    public static void handleRoyalFlushes(PlayersHand[] winningHands, int[] winnerIndexes) {
        System.out.println("We have a TIE! The bank is to be divided between the following " + winningHands.length + " winners:");
        for (int i = 0; i < winningHands.length; i++) {
            System.out.println("Player " + winnerIndexes[i] + " wins with a Royal Flush!");
        }
    }

    public static void handleHighCards(PlayersHand[] winningHands, int[] winnerIndexes) {
        System.out.println();

        int winningPlayersRank = 0;
        for (int i = 0; i < winningHands.length; i++) {
            String prefix = "a";
            if (determineHighCard(winningHands[i]) == 6 || determineHighCard(winningHands[i]) == 12) prefix = "an";
            System.out.println("Player " + winnerIndexes[i] + "'s High Card is " + prefix + " " + Ranks.values()[determineHighCard(winningHands[i])] + "!");
            if (determineHighCard(winningHands[i]) > winningPlayersRank) {
                winningPlayersRank = determineHighCard(winningHands[i]);
                }
        }
        for (int i = 0; i < winningHands.length; i++) {
            if (determineHighCard(winningHands[i]) == winningPlayersRank) {
                System.out.println("Player " + winnerIndexes[i] + " wins by High Card!");
            }
        }
    }

    public static class CardRankComparator implements Comparator<Card> {
        @Override
        public int compare(Card card1, Card card2) {
            return card2.cardRank.ordinal() - card1.cardRank.ordinal();
        }

    }

    public static void handlePairs(PlayersHand[] winningHands, int[] winnerIndexes) {
        System.out.println();
        int indexAuxilia = 0;
        int winningIndex = 0;
        Card[] arrayAuxilia = new Card[winningHands.length];
        String postfix = "";
        for (int i = 0; i < winningHands.length; i++) {
            int playersPairRank = 0;
            Card[] cards = {winningHands[i].card1, winningHands[i].card2, winningHands[i].card3, winningHands[i].card4, winningHands[i].card5};
            for (int j = 0; j < cards.length; j++) {
                for (int k = j + 1; k < cards.length; k++) {
                    if (cards[j].cardRank == cards[k].cardRank) {
                        playersPairRank = cards[j].cardRank.ordinal();
                    }
                }
            }
            postfix = "S!";
            if (playersPairRank == 4) postfix = "ES!";
            System.out.println("Player " + winnerIndexes[i] + " has a pair of " + Ranks.values()[playersPairRank] + postfix);
            if (playersPairRank > indexAuxilia) {
                indexAuxilia = playersPairRank;
                winningIndex = i;
                arrayAuxilia[i] = new Card(cards[0].cardSuit, cards[0].cardRank);
            }
        }
        boolean requireKickers = false;
        for (int i = 0; i < arrayAuxilia.length; i++) {
            for (int j = i + 1; j < arrayAuxilia.length; j++) {
                if (arrayAuxilia[i] != null && arrayAuxilia[j] != null && arrayAuxilia[i].cardRank == arrayAuxilia[j].cardRank) {
                    requireKickers = true;
                }
            }
        }

        if (!requireKickers) {
            postfix = "S!";
            if (indexAuxilia == 4) postfix = "ES!";
            System.out.println("Player " + winnerIndexes[winningIndex] + " wins with a PAIR of " + Ranks.values()[indexAuxilia] + postfix);
        }

        else {
            Card[][] kickersArray = new Card[winningHands.length][3];
            for (int i = 0; i < winningHands.length; i++) {
                int iteratorAuxilia = 0;
                Card[] cards = {winningHands[i].card1, winningHands[i].card2, winningHands[i].card3, winningHands[i].card4, winningHands[i].card5};
                Card[] kickers = new Card[3];
                for (int j = 0; j < 5; j++) {
                    if (cards[j].cardRank != Ranks.values()[indexAuxilia]) {
                    kickers[iteratorAuxilia] = cards[j];
                    iteratorAuxilia++;
                    }
                }
                Arrays.sort(kickers, new CardRankComparator());
                kickersArray[i] = kickers;
            }
            for (int position = 0; position < 3; position++) {
                boolean isEqualKickers = true;
                Card highestKicker = null;
                int winningPlayerIndex = 0;
                for (int playerIndex = 0; playerIndex < kickersArray.length; playerIndex++) {
                    Card kicker = kickersArray[playerIndex][position];
                    if (kicker == null) {
                        isEqualKickers = false;
                        break;
                    }
                    if (playerIndex == 0) {
                        highestKicker = kicker;
                        winningPlayerIndex = playerIndex;
                    } else {
                        int comparison = kicker.compareTo(highestKicker);
                        if (comparison > 0) {
                            highestKicker = kicker;
                            winningPlayerIndex = playerIndex;
                        }
                        if (comparison != 0) {
                            isEqualKickers = false;
                            break;
                        }
                    }
                }
                if (isEqualKickers) {
                    if (position == 2) {
                        System.out.println("It's a tie!");
                    }
                    continue;
                }
                int pairRank = indexAuxilia;
                postfix = "S!";
                if (indexAuxilia == 4) postfix = "ES!";
                String winnerMessage = "Player " + winnerIndexes[winningPlayerIndex] + " wins with a PAIR of " +
                        Ranks.values()[pairRank] + postfix + " and the highest kicker of " + highestKicker.cardRank + "!";
                System.out.println(winnerMessage);
                break;
            }
        }
    }

    public static void handleStraights(PlayersHand[] winningHands, int[] winnerIndexes) {
        System.out.println();
        int winningPlayerIndex = 0;
        int[] topCards = new int[winningHands.length];

        for (int i = 0; i < winningHands.length; i++) {
            topCards[i] = determineHighCard(winningHands[i]);
        }
        int maxTopCard = Integer.MIN_VALUE;
        for (int i = 1; i < winningHands.length; i++) {
            if (topCards[i] > maxTopCard) {
                maxTopCard = topCards[i];
                winningPlayerIndex = i;
            }
        }
        boolean drawGame = false;
        for (int i = 0; i < winningHands.length; i++) {
            if (i != winningPlayerIndex && topCards[i] == maxTopCard) {
                drawGame = true;
                break;
            }
        }

        if (drawGame) {
            System.out.println("The game is a tie! The bank is distributed among multiple winners:");
            for (int i = 0; i < winningHands.length; i++) {
                if (topCards[i] == maxTopCard) {
                    System.out.println("Player " + winnerIndexes[i]);
                } else {
                    String prefix = (maxTopCard == 6 || maxTopCard == 12) ? "an" : "a";
                    System.out.println("Player " + winnerIndexes[winningPlayerIndex] + " wins with a Straight! (top card - " + maxTopCard + ").");
                }
            }
        }
    }

    public static void handleStraightFlushes(PlayersHand[] winningHands, int[] winnerIndexes) {
            System.out.println();
            int winningPlayerIndex = 0;
            int[] topCards = new int[winningHands.length];

            for (int i = 0; i < winningHands.length; i++) {
                topCards[i] = determineHighCard(winningHands[i]);
            }
            int maxTopCard = Integer.MIN_VALUE;
            for (int i = 1; i < winningHands.length; i++) {
                if (topCards[i] > maxTopCard) {
                    maxTopCard = topCards[i];
                    winningPlayerIndex = i;
                }
            }
            boolean drawGame = false;
            for (int i = 0; i < winningHands.length; i++) {
                if (i != winningPlayerIndex && topCards[i] == maxTopCard) {
                    drawGame = true;
                    break;
                }
            }

            if (drawGame) {
                System.out.println("The game is a tie! The bank is distributed among multiple winners:");
                for (int i = 0; i < winningHands.length; i++) {
                    if (topCards[i] == maxTopCard) {
                        System.out.println("Player " + winnerIndexes[i]);
                    } else {
                        String prefix = (maxTopCard == 6 || maxTopCard == 12) ? "an" : "a";
                        System.out.println("Player " + winnerIndexes[winningPlayerIndex] + " wins with a Straight Flush! (top card - " + maxTopCard + ").");
                    }
                }
            }
        }

    public static void handleFlushes(PlayersHand[] winningHands, int[] winnerIndexes) {
        System.out.println();
        Card[][] sortedHands = new Card[winningHands.length][5];
        for (int i = 0; i < winningHands.length; i++) {
            Card[] cards = {winningHands[i].card1, winningHands[i].card2, winningHands[i].card3, winningHands[i].card4, winningHands[i].card5};
            Arrays.sort(cards, new CardRankComparator().reversed());
            sortedHands[i] = cards;
        }
        int winningPlayerIndex = 0;
        for (int cardIndex = 0; cardIndex < 5; cardIndex++) {
            Card highestCard = sortedHands[winningPlayerIndex][cardIndex];
            boolean isTie = false;
            for (int i = 0; i < winningHands.length; i++) {
                if (i != winningPlayerIndex) {
                    int comparison = highestCard.compareTo(sortedHands[i][cardIndex]);
                    if (comparison < 0) {
                        winningPlayerIndex = i;
                        highestCard = sortedHands[i][cardIndex];
                        isTie = false;
                    } else if (comparison == 0) {
                        isTie = true;
                    }
                }
            }
            if (!isTie) {
                break;
            }
        }
        System.out.print("Player " + winnerIndexes[winningPlayerIndex] + " wins with a Flush! Cards: ");
        for (Card card : sortedHands[winningPlayerIndex]) {
            System.out.print(card + " ");
        }
        System.out.println();
    }

    public static void handleTwoPairs(PlayersHand[] winningHands, int[] winnerIndexes) {
        int[] playersHighPairRanks = new int[winningHands.length];
        int[] playersLowPairRanks = new int[winningHands.length];
        Card[] playersKickerCards = new Card[winningHands.length];
        int smallerPairValue = 0;
        int largerPairValue = 0;
        for (int i = 0; i < winningHands.length; i++) {
            Card[] cards = {winningHands[i].card1, winningHands[i].card2, winningHands[i].card3, winningHands[i].card4, winningHands[i].card5};
            Card[] aPlayersPairCards = new Card[4];
            int iteratorAuxilia = 0;
            for (int j = 0; j < cards.length; j++) {
                for (int k = j + 1; k < cards.length; k++) {
                    if (cards[j].cardRank == cards[k].cardRank) {
                        aPlayersPairCards[iteratorAuxilia] = cards[j];
                        iteratorAuxilia++;
                    }
                }
            }
            boolean cardPresentInPairs;
            for (int l = 0; l < cards.length; l++) {
                cardPresentInPairs = false;
                for (int m = 0; m < aPlayersPairCards.length; m++) {
                    if (cards[l].equals(aPlayersPairCards[m])) {
                        cardPresentInPairs = true;
                        break;
                    }
                }
                if (!cardPresentInPairs) {
                    playersKickerCards[i] = cards[l];
                }
            }

            for (int n = 0; n < aPlayersPairCards.length; n++) {
                if (aPlayersPairCards[n] != null) { // здесь забит костыль
                int currentPairValue = aPlayersPairCards[n].cardRank.ordinal();
                if (currentPairValue > largerPairValue) {
                    smallerPairValue = largerPairValue;
                    largerPairValue = currentPairValue;
                } else if (currentPairValue > smallerPairValue) {
                    smallerPairValue = currentPairValue;
                }
            }
            }
            playersHighPairRanks[i] = largerPairValue;
            playersLowPairRanks[i] = smallerPairValue;
        }
        int largestPairCounter = 0;
        int smallerPairCounter = 0;
        int largestPairRank = -1;
        for (int i = 0; i < winningHands.length; i++) {
            if (playersHighPairRanks[i] > largestPairRank) {
                largestPairRank = playersHighPairRanks[i];
                largestPairCounter++;
            }
            if (largestPairCounter < 2) {
                System.out.println("Player " + winnerIndexes[i] + " wins with a pair of " + Ranks.values()[playersHighPairRanks[i]] + " and a pair of " + Ranks.values()[playersLowPairRanks[i]] + "!");
                break;
            } else {
                int smallerPairRank = -1;
                for (int j = 0; j < winningHands.length; j++) {
                    if (playersLowPairRanks[i] > smallerPairRank) {
                        smallerPairRank = playersLowPairRanks[i];
                        smallerPairCounter++;
                    }
                }
            }
            if (smallerPairCounter < 2) {
                System.out.println("Player " + winnerIndexes[i] + " wins with a pair of " + Ranks.values()[playersHighPairRanks[i]] + "amd a pair of " + Ranks.values()[playersLowPairRanks[i]] + "!");
                break;
            } else {
                int maxKickerRank = -1;
                int maxKickerCounter = 0;
                for (int j = 0; j < winningHands.length; j++) {
                    if (playersKickerCards[j].cardRank.ordinal() > maxKickerRank) {
                        maxKickerRank = playersKickerCards[j].cardRank.ordinal();
                        maxKickerCounter++;
                    }
                }

                if (maxKickerCounter < 2) {
                    for (int k = 0; k < winningHands.length; k++) {
                        if (playersKickerCards[k].cardRank.ordinal() == maxKickerRank) {
                            System.out.println("Player " + winnerIndexes[i] + " wins with a pair of " + Ranks.values()[playersHighPairRanks[i]] + "amd a pair of " + Ranks.values()[playersLowPairRanks[i]] + ", plus the kicker card " + Ranks.values()[maxKickerRank] + "!");
                            break;
                        }
                        else {
                            System.out.println("The game is a draw! The bank is distributed between the following players");
                            for (int l = 0; l < winningHands.length; l++) {
                                if (playersKickerCards[l].cardRank.ordinal() == maxKickerRank) System.out.println("Player " + winnerIndexes[l]);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void handleTrips(PlayersHand[] winningHands, int[] winnerIndexes) {
        int[] playersTripsRanks = new int[winningHands.length];
        int[] largeKickerRanks = new int[winningHands.length];
        int[] smallKickerRanks = new int[winningHands.length];
        String postfix = "";
        Card[] tripCards = new Card[winningHands.length];
        for (int h = 0; h < winningHands.length; h++) {
        Card[] cards = {winningHands[h].card1, winningHands[h].card2, winningHands[h].card3, winningHands[h].card4, winningHands[h].card5};
        for (int i = 0; i < cards.length; i++) {
            for (int j = i + 1; j < cards.length; j++) {
                for (int k = j + 1; k < cards.length; k++) {
                    if (cards[i].cardRank == cards[j].cardRank && cards[i].cardRank == cards[k].cardRank) {
                        playersTripsRanks[h] = cards[i].cardRank.ordinal();
                        tripCards[h] = cards[i];
                    }
                }
            }
        }
            Card[] remainingCards = new Card[2];
            int remainingCardIndex = 0;
            for (int i = 0; i < cards.length; i++) {
                if (cards[i].cardRank.ordinal() != playersTripsRanks[h]) {
                    remainingCards[remainingCardIndex] = cards[i];
                    remainingCardIndex++;
                }
            }
            largeKickerRanks[h] = remainingCards[0].cardRank.ordinal();
            smallKickerRanks[h] = remainingCards[1].cardRank.ordinal();
    }


        int largestTripRank = -1;
        int largestTripCounter = 0;
        for (int i = 0; i < winningHands.length; i++) {
            if (playersTripsRanks[i] > largestTripRank) {
                largestTripRank = playersTripsRanks[i];
                largestTripCounter++;
            }
        }
        if (largestTripCounter < 2) {
            for (int i = 0; i < winningHands.length; i++) {
                postfix = "S!";
                if (tripCards[i].cardRank.ordinal() == 4) postfix = "ES!";
                if (playersTripsRanks[i] == largestTripRank) {
                    System.out.println("PLayer " + winnerIndexes[i] + "wins with a TRIPS of " + Ranks.values()[largestTripRank] + postfix + "!");
                    break;
                }
            }
        } else {
            int largeKickerCounter = 0;
            int largestFirstKicker = -1;
            for (int i = 0; i < winningHands.length; i++) {
                if (largeKickerRanks[i] > largestFirstKicker) {
                    largestFirstKicker = largeKickerRanks[i];
                    largeKickerCounter++;
                }
            }
            if (largeKickerCounter < 2) {
                for (int i = 0; i < winningHands.length; i++) {
                    postfix = "S!";
                    if (tripCards[i].cardRank.ordinal() == 4) postfix = "ES!";
                    if (playersTripsRanks[i] == largestTripRank && largeKickerRanks[i] == largestFirstKicker) {
                        System.out.println("PLayer " + winnerIndexes[i] + "wins with a TRIPS of " + Ranks.values()[largestTripRank] + postfix + " and the first kicker " + Ranks.values()[largestFirstKicker] + "!");
                        break;
                    }
                }
            } else {
                int smallKickerCounter = 0;
                int largestSecondKicker = -1;
                for (int i = 0; i < winningHands.length; i++) {
                    if (smallKickerRanks[i] > largestSecondKicker) {
                        largestSecondKicker = smallKickerRanks[i];
                        smallKickerCounter++;
                    }
                }
                if (smallKickerCounter < 2) {
                    for (int i = 0; i < winningHands.length; i++) {
                        postfix = "S!";
                        if (tripCards[i].cardRank.ordinal() == 4) postfix = "ES!";
                        if (playersTripsRanks[i] == largestTripRank && smallKickerRanks[i] == largestSecondKicker) {
                            System.out.println("PLayer " + winnerIndexes[i] + "wins with a TRIPS of " + Ranks.values()[largestTripRank] + postfix + ", the first kicker " + Ranks.values()[largestFirstKicker] + "and the second kicker " + Ranks.values()[largestSecondKicker] + "!");
                            break;
                        }
                    }
                } else {
                    System.out.println("The game is a tie! The bank will be distributed between the following players:");
                    for (int i = 0; i < winningHands.length; i++) {
                        if (playersTripsRanks[i] == largestTripRank && largeKickerRanks[i] == largestFirstKicker && smallKickerRanks[i] == largestSecondKicker) {
                            System.out.println("Player " + winnerIndexes[i]);;
                        }
                    }
                }
            }
        }
    }

    public static void handleFullHouses(PlayersHand[] winningHands, int[] winnerIndexes) {
        int[] playersTripsRanks = new int[winningHands.length];
        int[] playersPairRanks = new int[winningHands.length];
        String postfix = "";
        String postfix2 = "";
        Card[] tripCards = new Card[winningHands.length];
        for (int h = 0; h < winningHands.length; h++) {
            Card[] cards = {winningHands[h].card1, winningHands[h].card2, winningHands[h].card3, winningHands[h].card4, winningHands[h].card5};
            for (int i = 0; i < cards.length; i++) {
                for (int j = i + 1; j < cards.length; j++) {
                    for (int k = j + 1; k < cards.length; k++) {
                        if (cards[i].cardRank == cards[j].cardRank && cards[i].cardRank == cards[k].cardRank) {
                            playersTripsRanks[h] = cards[i].cardRank.ordinal();
                            tripCards[h] = cards[i];
                        }
                    }
                }
            }
        }
        int[] remainingCardIndex = new int[winningHands.length];
        for (int i = 0; i < winningHands.length; i++) {
            Card[] cards = {winningHands[i].card1, winningHands[i].card2, winningHands[i].card3, winningHands[i].card4, winningHands[i].card5};
            if (cards[i].cardRank.ordinal() != playersTripsRanks[i]) {
                remainingCardIndex[i] = cards[i].cardRank.ordinal();
            }
        }
        int largestTripRank = -1;
        int largestTripCounter = 0;
        for (int i = 0; i < winningHands.length; i++) {
            if (playersTripsRanks[i] > largestTripRank) {
                largestTripRank = playersTripsRanks[i];
                largestTripCounter++;
            }
        }
        if (largestTripCounter < 2) {
            for (int i = 0; i < winningHands.length; i++) {
                postfix = "S!";
                postfix2 = "S!";
                if (tripCards[i].cardRank.ordinal() == 4) postfix = "ES!";
                if (remainingCardIndex[i] == 4) postfix2 = "ES!";
                if (playersTripsRanks[i] == largestTripRank) {
                    System.out.println("PLayer " + winnerIndexes[i] + "wins with a FULL HOUSE of " + Ranks.values()[largestTripRank] + postfix + " and " + Ranks.values()[remainingCardIndex[i]] + postfix2 + "!");
                    break;
                }
            }
        } else {
            int largestPairIndex = -1;
            int largestPairCounter = 0;
            for (int i = 0; i < winningHands.length; i++) {
                if (remainingCardIndex[i] > largestPairIndex) largestPairIndex = remainingCardIndex[i];
                largestPairCounter++;
            }
            if (largestPairCounter < 2) {
                for (int i = 0; i < winningHands.length; i++) {
                    postfix = "S!";
                    postfix2 = "S!";
                    if (tripCards[i].cardRank.ordinal() == 4) postfix = "ES!";
                    if (remainingCardIndex[i] == 4) postfix2 = "ES!";
                    if (playersTripsRanks[i] == largestTripRank && remainingCardIndex[i] == largestPairIndex) {
                        System.out.println("PLayer " + winnerIndexes[i] + "wins with a FULL HOUSE of " + Ranks.values()[largestTripRank] + postfix + " and " + Ranks.values()[largestPairIndex] + postfix2 + "!");
                        break;
                    }
                }
            } else {
                System.out.println("Draw game! The bank is distributed between the following players:");
                for (int i = 0; i < winningHands.length; i++) {
                    if (playersTripsRanks[i] == largestTripRank && remainingCardIndex[i] == largestPairIndex) {
                        System.out.println("Player " + winnerIndexes[i]);;
                    }
                }
            }
        }
    }

    public static void handleQuads(PlayersHand[] winningHands, int[] winnerIndexes) {
        int[] quadCardRank = new int[winningHands.length];
        int[] kickerRanks = new int[winningHands.length];
        String postfix = "";
        for (int h = 0; h < winningHands.length; h++) {
            Card[] cards = {winningHands[h].card1, winningHands[h].card2, winningHands[h].card3, winningHands[h].card4, winningHands[h].card5};
            for (int i = 0; i < cards.length; i++) {
                for (int j = i + 1; j < cards.length; j++) {
                    for (int k = j + 1; k < cards.length; k++) {
                        for (int l = k + 1; l < cards.length; l++) {
                            if (cards[i].cardRank == cards[j].cardRank && cards[i].cardRank == cards[k].cardRank && cards[i].cardRank == cards[l].cardRank) {
                                quadCardRank[h] = cards[i].cardRank.ordinal();
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < winningHands.length; i++) {
            Card[] cards = {winningHands[i].card1, winningHands[i].card2, winningHands[i].card3, winningHands[i].card4, winningHands[i].card5};
            if (cards[i].cardRank.ordinal() != quadCardRank[i]) kickerRanks[i] = cards[i].cardRank.ordinal();
        }
        int largestQuadRank = -1;
        int largestQuadCounter = 0;
        for (int i = 0; i < winningHands.length; i++) {
            if (quadCardRank[i] > largestQuadRank) {
                largestQuadRank = quadCardRank[i];
                largestQuadCounter++;
            }
        }
        if (largestQuadCounter < 2) {
            for (int i = 0; i < winningHands.length; i++) {
                postfix = "S!";
                if (largestQuadRank == 4) postfix = "ES!";
                if (quadCardRank[i] == largestQuadRank) {
                    System.out.println("PLayer " + winnerIndexes[i] + "wins with a 4 OF A KIND of " + Ranks.values()[largestQuadRank] + postfix + "!");
                    break;
                }
            }
        } else {
            int largestKickerIndex = -1;
            int largestKickerCounter = 0;
            for (int i = 0; i < winningHands.length; i++) {
                if (kickerRanks[i] > largestKickerIndex) {
                    largestKickerIndex = kickerRanks[i];
                    largestKickerCounter++;
                }
            }
            if (largestKickerCounter < 2) {
                for (int i = 0; i < winningHands.length; i++) {
                    postfix = "S!";
                    if (largestQuadRank == 4) postfix = "ES!";
                    if (quadCardRank[i] == largestQuadRank) {
                        System.out.println("PLayer " + winnerIndexes[i] + "wins with a 4 OF A KIND of " + Ranks.values()[largestQuadRank] + postfix + " plus " + Ranks.values()[largestKickerIndex] + "as the kicker!");
                        break;
                    }
                }
            } else {
                System.out.println("We're tied! The bank is distributed between the following players:");
                for (int i = 0; i < winningHands.length; i++) {
                    if (quadCardRank[i] == largestQuadRank && kickerRanks[i] == largestKickerIndex) {
                        System.out.println("Player " + winnerIndexes[i]);;
                    }
                }
            }
        }
    }
}
