package main.Day_22;

import java.util.List;
import java.util.Map;
import java.util.*;

import static main.Utility.*;

class Day_22 {
    public static void main(String[] args) throws Exception {
        log(Day_22.class.getName());
        final String input = loadTextFile(Day_22.class, "input.txt");
        var rawPlayer1 = splitString(input, "\n\n").get(0);
        var rawPlayer2 = splitString(input, "\n\n").get(1);
        List<String> player1Cards = new ArrayList<>(splitString(stringRemoveStartChunk(rawPlayer1, "Player 1:\n"), "\n"));
        List<String> player2Cards = new ArrayList<>(splitString(stringRemoveStartChunk(rawPlayer2, "Player 1:\n"), "\n"));


        long round = 0;
        while (player1Cards.size() > 0 && player2Cards.size() > 0) {
            round += 1;
            var card1 = player1Cards.remove(0);
            var card2 = player2Cards.remove(0);
            int card1Number = Integer.parseInt(card1);
            int card2Number = Integer.parseInt(card2);
            if (card1Number > card2Number) {
                player1Cards.add(card1);
                player1Cards.add(card2);
            } else if (card2Number > card1Number) {
                player2Cards.add(card2);
                player2Cards.add(card1);
            } else {
                throw new Exception("Unhandled!");
            }
        }
        var cardsToCount = player1Cards.size() > 0 ? player1Cards : player2Cards;
        long total = 0;
        for (var i = 0; i < cardsToCount.size(); i++) {
            var card= cardsToCount.get(i);
            long cardNumber = Long.parseLong(card);
            total += cardNumber * (cardsToCount.size() - i);
        }
        log("Part 1: " + total);
    }
}