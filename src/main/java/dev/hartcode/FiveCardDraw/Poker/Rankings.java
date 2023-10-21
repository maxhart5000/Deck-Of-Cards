package dev.hartcode.FiveCardDraw.Poker;

// Enum representing different poker hand rankings
public enum Rankings {
    // Default ranking for no specific hand
    NONE,

    // One pair of cards with the same rank
    ONE_PAIR,

    // Two pairs of cards with the same rank
    TWO_PAIR,

    // Three cards of the same rank
    THREE_OF_A_KIND,

    // A hand consisting of two of a kind and three of a kind
    FULL_HOUSE,

    // Four cards of the same rank
    FOUR_OF_A_KIND;

    // Method to override toString for a more readable representation
    @Override
    public String toString() {
        // Replace underscores with spaces in enum names for readability
        return this.name().replace('_', ' ');
    }
}
