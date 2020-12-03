package main.Day_3;

import java.util.List;
import java.util.stream.IntStream;

import static main.Utility.*;

class Day_3 {
    public static void main(String[] args) throws Exception {
        log("Day 3");
        final List<String> input = loadTextFileAsList(Day_3.class, "input.txt");
        log("Part 1: " + getTrees(input, Pair(3, 1)));
        log("Part 2: " + MultiPair(2, 1, 1, 3, 1, 5, 1, 7, 1, 1, 2).stream()
                .map(slope -> (long) getTrees(input, slope)).reduce(1L, Math::multiplyExact));
    }

    static int getTrees(final List<String> input, final List<Integer> slope) {
        final int mapWidth = input.get(0).length(), mapHeight = input.size();
        final int slopeX = slope.get(0), slopeY = slope.get(1);
        final int maxPossibleSteps = (int) Math.ceil((double) mapHeight / slopeY);
        return IntStream.range(1, maxPossibleSteps)
                .map(step -> stringChunkExistsAtLocation(input.get(step * slopeY), "#", step * slopeX % mapWidth) ? 1 : 0).sum();
    }
}