package dev.hartcode.FiveCardDraw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record Card(Suit suit, String face, int rank) {
    // Enum to represent card suits
    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE;
        public char getChar(){
            return switch(this){
                case CLUB -> (char) 9827;
                case DIAMOND -> (char) 9830;
                case HEART -> (char) 9829;
                case SPADE -> (char) 9824;
            };
        }
    }

    // Factory method to create a sorting comparator for cards based on rank and reversed suit
    public static Comparator<Card> sortRankReversedSuit() {
        return Comparator.comparing(Card::rank).reversed().thenComparing(Card::suit);
    }

    // Override toString to print card details
    @Override
    public String toString() {
        int index = face.equals("10") ? 2 : 1;
        String faceString = face.substring(0, index);
        return "%s%c(%d)".formatted(face, suit.getChar(), rank);
    }

    // Factory method to create a numeric card
    public static Card getNumericCard(Suit suit, int cardNumber) {
        if (cardNumber >= 2 && cardNumber <= 10) {
            return new Card(suit, String.valueOf(cardNumber), cardNumber - 2);
        }
        System.out.println("Invalid numeric card selected");
        return null;
    }

    // Factory method to create a face card
    public static Card getFaceCard(Suit suit, char abbrev) {
        int charIndex = "JQKA".indexOf(abbrev);
        if (charIndex > -1) {
            return new Card(suit, "" + abbrev, charIndex + 9);
        }
        System.out.println("Invalid face card selected");
        return null;
    }

    // Factory method to create a standard deck of cards
    public static List<Card> getStandardCards() {
        List<Card> cards = new ArrayList<>(52);
        for (Suit suit : Suit.values()) {
            for (int i = 2; i <= 10; i++) {
                cards.add(getNumericCard(suit, i));
            }
            for (char c : new char[] { 'J', 'Q', 'K', 'A' }) {
                cards.add(getFaceCard(suit, c));
            }
        }
        return cards;
    }

    // Helper method to print a deck of cards
    public static void printDeck(List<Card> cards) {
        printDeck("Current Deck", cards, 4);
    }

    // Helper method to print a deck of cards with a description and specified rows
    public static void printDeck(String description, List<Card> cards, int rows) {
        System.out.println("-".repeat(50));
        System.out.println(description);
        int cardsInRow = cards.size() / rows;
        for (int i = 0; i < rows; i++) {
            int startIndex = i * cardsInRow;
            int endIndex = startIndex + cardsInRow;
            cards.subList(startIndex, endIndex).forEach(s -> System.out.print(s + " "));
            System.out.println();
        }
        System.out.println("-".repeat(50));
    }
}
