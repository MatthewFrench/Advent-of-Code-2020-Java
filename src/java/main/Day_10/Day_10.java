package main.Day_10;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.Utility.*;

class Day_10 {
    public static void main(String[] args) throws Exception {
        log(Day_10.class.getName());
        final List<Long> input = loadTextFileAsTypeList(Day_10.class, "input.txt", Long::parseLong);
        Collections.sort(input);
        logObject(input);
        {
            long current = 0;
            long ending = input.get(input.size() - 1) + 3;
            Map<Long, Integer> joltDifferences = new HashMap<>();
            for (var i = 0; i < input.size(); i++) {
                long jolt = input.get(i);
                long difference = jolt - current;
                joltDifferences.put(difference, joltDifferences.getOrDefault(difference, 0) + 1);
                current = jolt;
            }
            long difference = ending - current;
            joltDifferences.put(difference, joltDifferences.getOrDefault(difference, 0) + 1);
            logObject(joltDifferences);
            log("Part 1: " + (joltDifferences.get(1L) * joltDifferences.get(3L)));
        }

        // Add start and end
        input.add(0, 0L);
        input.add(input.get(input.size() - 1) + 3);
        List<Long> builtInput = new ArrayList<>();
        builtInput.add(input.get(0));
        Map<Long, Long> cachedPaths = new HashMap<>();
        var count = countAllPaths(input, builtInput, 0, 0, cachedPaths);

        log("Part 2 total number of ways to connect adapters: " + count);
    }
    static public long countAllPaths(List<Long> input, List<Long> builtInput, int currentInputIndex, int currentBuildIndex, Map<Long, Long> cachedPaths) {
        long totalCount = 0;
        if (currentInputIndex == input.size() - 1) {
            return 1;
        }
        long jolt = input.get(currentInputIndex);
        if (cachedPaths.containsKey(jolt)) {
            return cachedPaths.get(jolt);
        }
        List<Long> nextPossibleJolts = getAvailableJoltsSublist(input, currentInputIndex, jolt);
        // For these, recursively find the next ones

        for (var i = 0; i < nextPossibleJolts.size(); i++) {
            var nextJolt = nextPossibleJolts.get(i);
            List<Long> newBuilt = new ArrayList<>(builtInput);
            newBuilt.add(nextJolt);
            totalCount += countAllPaths(input, newBuilt, currentInputIndex + i + 1, currentBuildIndex + 1, cachedPaths);
        }
        cachedPaths.put(jolt, totalCount);
        return totalCount;
    }
    static public List<Long> getAvailableJoltsSublist(List<Long> input, int index, long jolt) {
        var max = jolt + 3;
        int endIndex = index;
        for (var i = index + 1; i < input.size(); i++) {
            var newJolt = input.get(i);
            if (newJolt <= max) {
                endIndex = i;
            } else {
                break;
            }
        }
        return input.subList(index + 1, endIndex+1);
    }
}