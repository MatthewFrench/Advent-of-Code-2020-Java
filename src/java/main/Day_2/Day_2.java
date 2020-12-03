package main.Day_2;

import main.Utility;
import java.util.List;

class Day_2 {
    public static void main(String[] args) throws Exception {
        Utility.log("Day 2");
        final List<String> input = Utility.loadTextFileAsList(Day_2.class, "input.txt");
        int validPart1 = 0;
        int validPart2 = 0;
        for (String value : input) {
            var sections = Utility.splitString(value, " ");
            var rangeNumbers = Utility.splitString(sections.get(0), "-");
            var startingNumber = Integer.parseInt(rangeNumbers.get(0));
            var endingNumber = Integer.parseInt(rangeNumbers.get(1));
            var letter = Utility.getStringChunk(sections.get(1), 0, 1);
            var password = sections.get(2);
            int count = Utility.countStringInstanceInString(password, letter);
            if (count >= startingNumber && count <= endingNumber) {
                validPart1 += 1;
            }

            // Part 2
            var firstLocation = false;
            var secondLocation = false;
            if (Utility.stringChunkExistsAtLocation(password, letter, startingNumber - 1)) {
                firstLocation = true;
            }
            if (Utility.stringChunkExistsAtLocation(password, letter, endingNumber - 1)) {
                secondLocation = true;
            }
            if (firstLocation != secondLocation) {
                validPart2 += 1;
            }
        }
        Utility.log("Part 1");
        Utility.log("Valid passwords: " + validPart1);
        Utility.log("Total passwords: " + input.size());
        Utility.log("Part 2");
        Utility.log("Valid passwords: " + validPart2);
        Utility.log("Total passwords: " + input.size());
    }
}