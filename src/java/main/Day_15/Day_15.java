package main.Day_15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static main.Utility.*;

class Day_15 {
    public static void main(String[] args) throws Exception {
        log(Day_15.class.getName());
        final String input = loadTextFile(Day_15.class, "input.txt");
        List<Long> inputNumbers = splitString(input, ",").stream().map(Long::parseLong).collect(Collectors.toList());
        var max = 30000000;
        Map<Long, List<Long>> spokenMap = new HashMap<>();
        for (long i = 0; i < inputNumbers.size(); i++) {
            spokenMap.put(inputNumbers.get((int) i), List(i+1));
        }
        for (long i = inputNumbers.size() - 1; i < max - 1; i++) {
            long newNumber = 0;
            long lastNumber = inputNumbers.get((int) i);
            if (spokenMap.get(lastNumber).size() > 1) {
                var list = spokenMap.get(lastNumber);
                var last = list.get(list.size() - 1);
                var beforeLast = list.get(list.size() - 2);
                newNumber = last - beforeLast;
            }
            if (!spokenMap.containsKey(newNumber)) {
                spokenMap.put(newNumber, List());
            }
            spokenMap.get(newNumber).add(i+2);
            inputNumbers.add(newNumber);
        }
        log("Result: " + inputNumbers.get(inputNumbers.size() - 1));
    }
}