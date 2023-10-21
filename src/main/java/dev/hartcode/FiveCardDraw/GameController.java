package dev.hartcode.FiveCardDraw;

import dev.hartcode.FiveCardDraw.Poker.PokerGame;

public class GameController {
    public static void main(String[] args) {
        // Create a PokerGame instance with 5 players and a hand size of 5
        PokerGame fiveCardDraw = new PokerGame(5, 5);

        // Start the poker game
        fiveCardDraw.startPlay();
    }
}
