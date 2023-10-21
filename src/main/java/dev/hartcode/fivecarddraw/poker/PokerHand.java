package dev.hartcode.fivecarddraw.poker;

import dev.hartcode.fivecarddraw.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerHand {
    // List to hold the cards in the poker hand
    private final List<Card> hand;

    // List to store the cards kept in the hand
    private final List<Card> keepers;

    // List to store the discarded cards
    private final List<Card> discard;

    // Ranking of the poker hand
    private Rankings score = Rankings.NONE;

    // Player number associated with the hand
    private final int playerNo;

    // Constructor to initialize a poker hand for a player
    public PokerHand(int playerNo, List<Card> hand) {
        // Sort the hand by rank and reversed suit
        hand.sort(Card.sortRankReversedSuit());
        this.hand = hand;
        this.playerNo = playerNo;
        keepers = new ArrayList<>(hand.size());
        discard = new ArrayList<>(hand.size());
    }

    // Method to evaluate the poker hand and determine its ranking
    public void evalHand() {
        // List to store the faces of the cards in the hand
        List<String> faceList = new ArrayList<>(hand.size());
        hand.forEach(card -> faceList.add(card.face()));

        // List to store duplicate face cards in the hand
        List<String> duplicateFaceCards = new ArrayList<>();
        faceList.forEach(face -> {
            if (!duplicateFaceCards.contains(face) && Collections.frequency(faceList, face) > 1) {
                duplicateFaceCards.add(face);
            }
        });

        // Iterate through duplicate face cards to determine hand ranking
        for (String duplicates : duplicateFaceCards) {
            int start = faceList.indexOf(duplicates);
            int last = faceList.lastIndexOf(duplicates);
            setRank(last - start + 1);
            List<Card> subList = hand.subList(start, last + 1);
            keepers.addAll(subList);
        }
        pickDiscards();
    }

    // Method to set the rank of the poker hand based on face counts
    private void setRank(int faceCount) {
        switch (faceCount) {
            case 4 -> score = Rankings.FOUR_OF_A_KIND;
            case 3 -> {
                if (score == Rankings.NONE) score = Rankings.THREE_OF_A_KIND;
                else score = Rankings.FULL_HOUSE;
            }
            case 2 -> {
                if (score == Rankings.NONE) score = Rankings.ONE_PAIR;
                else if (score == Rankings.THREE_OF_A_KIND) score = Rankings.FULL_HOUSE;
                else score = Rankings.TWO_PAIR;
            }
        }
    }

    // Method to pick discards based on the hand's ranking
    private void pickDiscards() {
        List<Card> temp = new ArrayList<>(hand);
        temp.removeAll(keepers);
        int rankedCards = keepers.size();
        Collections.reverse(temp);
        int index = 0;
        for (Card c : temp) {
            if (index++ < 3 && (rankedCards > 2 || c.rank() < 9)) discard.add(c);
            else keepers.add(c);
        }
    }

    // Method to override toString for displaying poker hand details
    @Override
    public String toString() {
        return "%d. %-16s Rank: %d %-40s Best:%-7s Worst:%-6s %s".formatted(
                playerNo, score, score.ordinal(), hand,
                Collections.max(hand, Comparator.comparing(Card::rank)),
                Collections.min(hand, Comparator.comparing(Card::rank)),
                !discard.isEmpty() ? "Discards:" + discard : ""
        );
    }
}
