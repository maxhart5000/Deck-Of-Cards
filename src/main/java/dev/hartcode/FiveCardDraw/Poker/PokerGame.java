package dev.hartcode.FiveCardDraw.Poker;

import dev.hartcode.FiveCardDraw.Card;

import java.util.*;
import java.util.function.Consumer;

public class PokerGame {
    // Deck of cards used for the game
    private final List<Card> deck = Card.getStandardCards();

    // Deck to hold remaining cards after dealing to players
    private List<Card> remainingDeck;

    // List of poker hands for players
    private final List<PokerHand> pokerHands;

    // Number of players in the game
    private final int numOfPlayers;

    // Number of cards in each player's hand
    private final int handSize;

    // Constructor for initializing the poker game
    public PokerGame(int numOfPlayers, int handSize) {
        this.numOfPlayers = numOfPlayers;
        this.handSize = handSize;
        pokerHands = new ArrayList<>(handSize);
    }

    // Method to start playing the poker game
    public void startPlay() {
        // Shuffle the deck
        Collections.shuffle(deck);
        Card.printDeck("SHUFFLED DECK", deck, 4);

        // Split and shuffle the deck
        int randomMiddle = new Random().nextInt(20, 32);
        Collections.rotate(deck, randomMiddle);
        Card.printDeck("SPLIT AND SHUFFLED DECK", deck, 4);

        // Deal cards to players
        dealCards();

        System.out.println("PLAYER'S HANDS");

        // Evaluate hands for each player
        Consumer<PokerHand> checkHands = PokerHand::evalHand;
        pokerHands.forEach(checkHands.andThen(System.out::println));

        // Prepare the remaining deck
        int cardsDealt = numOfPlayers * handSize;
        int remainingCards = deck.size() - cardsDealt;
        remainingDeck = new ArrayList<>(Collections.nCopies(remainingCards, null));
        remainingDeck.replaceAll(c -> deck.get(cardsDealt + remainingDeck.indexOf(c)));
        Card.printDeck("REMAINING CARDS ", remainingDeck, 2);
    }

    // Method to deal cards to players
    private void dealCards() {
        // Create a 2D array to represent hands for each player
        Card[][] cards = new Card[numOfPlayers][handSize];

        int cardIndex = 0;
        for (int i = 0; i < handSize; i++) {
            for (int j = 0; j < numOfPlayers; j++) {
                cards[j][i] = deck.get(cardIndex++);
            }
        }

        int playerNo = 1;
        for (Card[] deal : cards) {
            pokerHands.add(new PokerHand(playerNo++, Arrays.asList(deal)));
        }
    }
}
