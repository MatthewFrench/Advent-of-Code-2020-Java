package main.Day_3;

import java.util.List;
import java.util.stream.Stream;

import static main.Utility.*;

class Day_3 {
    public static void main(String[] args) throws Exception {
        log("Day 3");
        final List<String> input = loadTextFileAsList("main/Day_3/input.txt");
        log("Part 1: " + getTrees(input, Pair(3, 1)));
        log("Part 2: " + Stream.of(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
                .map(slope -> getTrees(input, slope)).reduce(1, Math::multiplyExact));
    }

    public static int getTrees(final List<String> input, final List<Integer> slope) {
        final int bottomOfMap = input.size();
        final int mapWidth = input.get(0).length();
        int x = 0;
        int y = 0;
        int trees = 0;
        while(y < bottomOfMap) {
            if (stringChunkExistsAtLocation(input.get(y), "#", x)) {
                trees += 1;
            }
            x += slope.get(0);
            y += slope.get(1);
            if (x >= mapWidth) {
                x -= mapWidth;
            }
        }
        return trees;
    }
}