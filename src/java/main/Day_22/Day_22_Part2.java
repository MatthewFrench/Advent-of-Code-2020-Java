package main.Day_22;

import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static main.Utility.*;

class Day_22_Part2 {
    public static void main(String[] args) throws Exception {
        log(Day_22_Part2.class.getName());
        final String input = loadTextFile(Day_22.class, "input.txt");
        var rawPlayer1 = splitString(input, "\n\n").get(0);
        var rawPlayer2 = splitString(input, "\n\n").get(1);
        List<String> player1Cards = new ArrayList<>(splitString(stringRemoveStartChunk(rawPlayer1, "Player 1:\n"), "\n"));
        List<String> player2Cards = new ArrayList<>(splitString(stringRemoveStartChunk(rawPlayer2, "Player 1:\n"), "\n"));
        ArrayList<Integer> player1CardNumbers = player1Cards.stream().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> player2CardNumbers = player2Cards.stream().map(Integer::parseInt).collect(Collectors.toCollection(ArrayList::new));

        playGame(player1CardNumbers, player2CardNumbers);

        var cardsToCount = player1CardNumbers.size() > 0 ? player1CardNumbers : player2CardNumbers;
        long total = 0;
        for (var i = 0; i < cardsToCount.size(); i++) {
            long cardNumber = cardsToCount.get(i);
            total += cardNumber * (cardsToCount.size() - i);
        }
        log("Part 2: " + total);
    }
    static boolean playGame(ArrayList<Integer> player1CardNumbers, ArrayList<Integer> player2CardNumbers) throws Exception {
        // Track rounds in this game
        //Set<Pair<List<Integer>, List<Integer>>> previousRounds = new HashSet<>();
        var player1Won = false;
        Set<Pair<ArrayList<Integer>, ArrayList<Integer>>> previousRounds = new HashSet<>();
        while (player1CardNumbers.size() > 0 && player2CardNumbers.size() > 0) {
            // If there is a previous round in this game with the same order of player decks, player 1 wins
            if (previousRounds.contains(Pair.with(player1CardNumbers, player2CardNumbers))) {
                // Player 1 wins
                player1Won = true;
                break;
            }
            previousRounds.add(Pair.with(new ArrayList<>(player1CardNumbers), new ArrayList<>(player2CardNumbers)));

            int card1Number = player1CardNumbers.remove(0);
            int card2Number = player2CardNumbers.remove(0);

            // Determine if the players have as many cards remaining in their deck as the value they just drew
            if (card1Number <= player1CardNumbers.size() && card2Number <= player2CardNumbers.size()) {
                // New game of recursive combat
                ArrayList<Integer> newPlayer1CardNumbers = new ArrayList<>();
                for (var i = 0; i < card1Number; i++) {
                    newPlayer1CardNumbers.add(player1CardNumbers.get(i));
                }
                ArrayList<Integer> newPlayer2CardNumbers = new ArrayList<>();
                for (var i = 0; i < card2Number; i++) {
                    newPlayer2CardNumbers.add(player2CardNumbers.get(i));
                }
                var winner = playGame(newPlayer1CardNumbers, newPlayer2CardNumbers);
                if (winner) {
                    player1CardNumbers.add(card1Number);
                    player1CardNumbers.add(card2Number);
                } else {
                    player2CardNumbers.add(card2Number);
                    player2CardNumbers.add(card1Number);
                }
            } else {
                if (card1Number > card2Number) {
                    player1CardNumbers.add(card1Number);
                    player1CardNumbers.add(card2Number);
                } else if (card2Number > card1Number) {
                    player2CardNumbers.add(card2Number);
                    player2CardNumbers.add(card1Number);
                } else {
                    throw new Exception("Unhandled!");
                }
            }
            if (player2CardNumbers.size() == 0) {
                player1Won = true;
            } else if (player1CardNumbers.size() == 0) {
                player1Won = false;
            }
        }
        return player1Won;
    }
}