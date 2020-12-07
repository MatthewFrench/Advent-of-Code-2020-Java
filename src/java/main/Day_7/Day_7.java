package main.Day_7;


import java.util.*;
import java.util.stream.Collectors;

import static main.Utility.*;

class Day_7 {
    public static void main(String[] args) throws Exception {
        // Needs lots of cleanup still
        log(Day_7.class.getName());
        final List<String> input = loadTextFileAsList(Day_7.class, "input.txt");
        HashMap<String, Bag> bagMap = new HashMap<>();
        for (var line : input) {
            var splitLine = splitString(line, " contain ");
            var bagName = splitLine.get(0);
            if (stringChunkExistsAtEnd(bagName, "s")) {
                bagName = stringRemoveEndChunk(bagName, "s");
            }
            var contentsUnparsed = splitLine.get(1);
            contentsUnparsed = stringRemoveEndChunk(contentsUnparsed, ".");
            if (contentsUnparsed.equals("no other bags")) {
                bagMap.put(bagName, new Bag(bagName, new ArrayList<String>()));
            } else {
                var contents = splitString(contentsUnparsed, ", ");
                bagMap.put(bagName, new Bag(bagName, contents));
            }
        }
        var targetBag = "shiny gold bag";
        long numberContainingTarget = 0;
        for (var bagName : bagMap.keySet()) {
            if (getNumberOfBagTypeInBag(bagMap, bagName, targetBag) > 0) {
                numberContainingTarget += 1;
            }
        }
        log("Result: " + numberContainingTarget);

        log("Result: " + (countTotalBagsInBag(bagMap, targetBag)));
    }
    public static long getNumberOfBagTypeInBag(HashMap<String, Bag> bagMap, final String bagName, final String lookupBag) {
        var bag = bagMap.get(bagName);
        var contains = 0;
        for (String innerBagName : bag.containsBags.keySet()) {
            long numberOfBags = bag.containsBags.get(innerBagName);
            if (innerBagName.equals(lookupBag)) {
                contains += numberOfBags;
            } else {
                contains += getNumberOfBagTypeInBag(bagMap, innerBagName, lookupBag) * numberOfBags;
            }
        }
        return contains;
    }
    public static long countTotalBagsInBag(HashMap<String, Bag> bagMap, final String bagName) {
        var bag = bagMap.get(bagName);
        var contains = 0;
        for (String innerBagName : bag.containsBags.keySet()) {
            long numberOfBags = bag.containsBags.get(innerBagName);
            contains += numberOfBags * ( 1 + countTotalBagsInBag(bagMap, innerBagName));
        }
        return contains;
    }
    public static class Bag {
        public String bagName;
        public Map<String, Integer> containsBags;
        public Bag(String name, List<String> containsContents) {
            bagName = name;
            containsBags = new HashMap<>();
            for (var content : containsContents) {
                var amountString = splitString(content, " ").get(0);
                var amount = Integer.parseInt(amountString);
                var innerName = content.substring(amountString.length() + 1);
                if (stringChunkExistsAtEnd(innerName, "s")) {
                    innerName = stringRemoveEndChunk(innerName, "s");
                }
                containsBags.put(innerName, amount);
            }
        }
    }
}