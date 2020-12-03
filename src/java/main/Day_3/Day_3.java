package main.Day_3;

import java.util.List;
import java.util.stream.Stream;

import static main.Utility.*;

class Day_3 {
    public static void main(String[] args) throws Exception {
        log("Day 3");
        final List<String> input = loadTextFileAsList("main/Day_3/input.txt");
        log("Part 1: " + getTrees(input, IntPair(3, 1)));
        log("Part 2: " + Stream.of(IntPair(1, 1), IntPair(3, 1), IntPair(5, 1), IntPair(7, 1), IntPair(1, 2))
                .map(slope -> getTrees(input, slope)).reduce(1, Math::multiplyExact));
    }

    public static int getTrees(final List<String> input, final Pair<Integer> slope) {
        final int bottomOfMap = input.size();
        final int mapWidth = input.get(0).length();
        int x = 0;
        int y = 0;
        int trees = 0;
        while(y < bottomOfMap) {
            if (stringChunkExistsAtLocation(input.get(y), "#", x)) {
                trees += 1;
            }
            x += slope.x;
            y += slope.y;
            if (x >= mapWidth) {
                x -= mapWidth;
            }
        }
        return trees;
    }
}