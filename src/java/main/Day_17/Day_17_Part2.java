package main.Day_17;

import org.javatuples.Pair;
import org.javatuples.Quartet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.Utility.*;

class Day_17_Part2 {
    public static void main(String[] args) throws Exception {
        log(Day_17_Part2.class.getName());
        final List<String> input = loadTextFileAsList(Day_17.class, "input.txt");

        int cycles = 6;
        // Inclusive bounds min, max
        Pair<Quartet<Long, Long, Long, Long>, Quartet<Long, Long, Long, Long>> bounds = Pair.with(Quartet.with(0L, 0L, 0L, 0L), Quartet.with(0L, 0L, 0L, 0L));
        // x, y, z on/off
        Map<Quartet<Long, Long, Long, Long>, Boolean> grid = new HashMap<>();

        long x = 0, y = 0, z = 0, w = 0;
        for (var line : input) {
            for (var piece : splitString(line, "")) {
                bounds = setState(grid, x, y, z, w, piece.equals("#"), bounds);
                x += 1;
            }
            x = 0;
            y += 1;
        }
        var originalGrid = cloneGrid(grid);
        for (var i = 0; i < cycles; i++) {
            var newGrid = cloneGrid(grid);
            for (var entry : grid.keySet()) {
                var activeNeighbors = getActiveNeighbors(grid, entry.getValue0(), entry.getValue1(), entry.getValue2(), entry.getValue3());
                var isActive = grid.get(entry);
                if (isActive) {
                    if (activeNeighbors == 2 || activeNeighbors == 3) {
                        isActive = true;
                    } else {
                        isActive = false;
                    }
                } else {
                    if (activeNeighbors == 3) {
                        isActive = true;
                    } else {
                        isActive = false;
                    }
                }
                bounds = setState(newGrid, entry.getValue0(), entry.getValue1(), entry.getValue2(), entry.getValue3(), isActive, bounds);
            }
            grid = newGrid;
            log("Round: " + i + ", active: " + countActive(grid));
        }

        log("Part 2 active: " + countActive(grid));
    }
    public static int countActive(Map<Quartet<Long, Long, Long, Long>, Boolean> grid) {
        int active = 0;
        for (var entry : grid.keySet()) {
            if (grid.get(entry)) {
                active += 1;
            }
        }
        return active;
    }
    public static int getActiveNeighbors(Map<Quartet<Long, Long, Long, Long>, Boolean> grid, long x, long y, long z, long w) {
        int active = 0;
        for (var x1 = x - 1; x1 <= x + 1; x1++) {
            for (var y1 = y - 1; y1 <= y + 1; y1++) {
                for (var z1 = z - 1; z1 <= z + 1; z1++) {
                    for (var w1 = w - 1; w1 <= w + 1; w1++) {
                        if (x1 == x && y1 == y && z1 == z && w1 == w) {
                            continue;
                        }
                        var position = Quartet.with(x1, y1, z1, w1);
                        if (grid.containsKey(position) && grid.get(position)) {
                            active += 1;
                        }
                    }
                }
            }
        }
        return active;
    }
    public static Pair<Quartet<Long, Long, Long, Long>, Quartet<Long, Long, Long, Long>> setState(
            Map<Quartet<Long, Long, Long, Long>, Boolean> grid,
            long x, long y, long z, long w,
            boolean isActive,
            Pair<Quartet<Long, Long, Long, Long>, Quartet<Long, Long, Long, Long>> bounds) {
        grid.put(Quartet.with(x, y, z, w), isActive);
        // Activate nearby pieces
        for (var x1 = x - 1; x1 <= x + 1; x1++) {
            for (var y1 = y - 1; y1 <= y + 1; y1++) {
                for (var z1 = z - 1; z1 <= z + 1; z1++) {
                    for (var w1 = w - 1; w1 <= w + 1; w1++) {
                        var position = Quartet.with(x1, y1, z1, w1);
                        if (!grid.containsKey(position)) {
                            grid.put(position, false);
                        }
                    }
                }
            }
        }
        return Pair.with(
                Quartet.with(Math.min(x-1, bounds.getValue0().getValue0()),
                        Math.min(y-1, bounds.getValue0().getValue1()),
                        Math.min(z-1, bounds.getValue0().getValue2()),
                        Math.min(w-1, bounds.getValue0().getValue3())),
                Quartet.with(Math.max(x+1, bounds.getValue1().getValue0()),
                        Math.max(y+1, bounds.getValue1().getValue1()),
                        Math.max(z+1, bounds.getValue1().getValue2()),
                        Math.max(w+1, bounds.getValue1().getValue3())));
    }
    public static Map<Quartet<Long, Long, Long, Long>, Boolean> cloneGrid(Map<Quartet<Long, Long, Long, Long>, Boolean> grid) {
        Map<Quartet<Long, Long, Long, Long>, Boolean> newGrid = new HashMap<>();
        for (var entry : grid.keySet()) {
            newGrid.put(Quartet.with(entry.getValue0(), entry.getValue1(), entry.getValue2(), entry.getValue3()), grid.get(entry));
        }
        return newGrid;
    }
}