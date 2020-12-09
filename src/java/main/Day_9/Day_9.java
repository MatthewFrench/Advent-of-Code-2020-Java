package main.Day_9;

import java.util.List;

import static main.Utility.*;

class Day_9 {
    public static void main(String[] args) throws Exception {
        log(Day_9.class.getName());
        // Tripped here on making it load as a type. Then erroneously chose int over long
        final List<Long> input = loadTextFileAsTypeList(Day_9.class, "input.txt", Long::parseLong);

        var preamble = 25;
        long part1Answer = -1;
        // Tripped here, starting at 0 instead of preamble
        for (var i = preamble; i < input.size(); i++) {
            var preambleList = input.subList(Math.max(i - preamble, 0), i);
            var number = input.get(i);
            var noNumber = true;
            inner:
            for (var j = 0; j < preambleList.size(); j++) {
                var number2 = preambleList.get(j);
                for (var k = j + 1; k < preambleList.size(); k++) {
                    var number3 = preambleList.get(k);
                    // Tripped here, put if they equaled to exit, actually opposite the case
                    if (!number2.equals(number3) && number2 + number3 == number) {
                        noNumber = false;
                        break inner;
                    }
                }
            }
            if (noNumber) {
                log("Part 1 answer: " + number);
                part1Answer = number;
                break;
            }
        }

        // Had to re-arrange variables here. I suppose due to a lack of understanding of what I was doing.
        // Didn't stop to think enough.
        for (var i = 0; i < input.size(); i++) {
            var number = input.get(i);
            var total = number;
            var largestNumber = number;
            var smallestNumber = number;
            for (var j = i + 1; j < input.size(); j++) {
                var number2 = input.get(j);
                // Tripped on smallest/largest number, thought it was first/last
                largestNumber = Math.max(largestNumber, number2);
                smallestNumber = Math.min(smallestNumber, number2);
                total += number2;
                if (total == part1Answer) {
                    log("Part 2 answer: " + (largestNumber + smallestNumber));
                } else if (total > part1Answer) {
                    break;
                }
            }
        }
    }
}