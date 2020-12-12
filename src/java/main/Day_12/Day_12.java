package main.Day_12;

import java.util.List;

import static main.Utility.*;

class Day_12 {
    // Clockwise
    static final int EAST = 0;
    static final int SOUTH = 90;
    static final int WEST = 180;
    static final int NORTH = 270;

    public static void main(String[] args) throws Exception {
        log(Day_12.class.getName());
        final List<String> input = loadTextFileAsList(Day_12.class, "input.txt");
        {
            var direction = EAST;
            var northSouthPosition = 0;
            var eastWestPosition = 0;
            for (var line : input) {
                var command = getStringChunk(line, 0, 1);
                var value = Integer.parseInt(stringRemoveStartChunk(line, command));
                if (command.equals("N")) {
                    northSouthPosition += value;
                } else if (command.equals("S")) {
                    northSouthPosition -= value;
                } else if (command.equals("E")) {
                    eastWestPosition += value;
                } else if (command.equals("W")) {
                    eastWestPosition -= value;
                } else if (command.equals("L")) {
                    direction -= value;
                    if (direction < 0) {
                        direction += 360;
                    }
                } else if (command.equals("R")) {
                    direction += value;
                    if (direction >= 360) {
                        direction -= 360;
                    }
                } else if (command.equals("F")) {
                    if (direction == EAST) {
                        eastWestPosition += value;
                    } else if (direction == WEST) {
                        eastWestPosition -= value;
                    } else if (direction == NORTH) {
                        northSouthPosition += value;
                    } else if (direction == SOUTH) {
                        northSouthPosition -= value;
                    } else {
                        log("UNKNOWN DIRECTION: " + direction);
                    }
                }
            }
            log("Part 1 Manhattan distance: " + (Math.abs(northSouthPosition) + Math.abs(eastWestPosition)));
        }

        var wayPointEastWestPosition = 10;
        var wayPointNorthSouthPosition = 1;
        var northSouthPosition = 0;
        var eastWestPosition = 0;
        for (var line : input) {
            var command = getStringChunk(line, 0, 1);
            var value = Integer.parseInt(stringRemoveStartChunk(line, command));
            if (command.equals("N")) {
                wayPointNorthSouthPosition += value;
            } else if (command.equals("S")) {
                wayPointNorthSouthPosition -= value;
            } else if (command.equals("E")) {
                wayPointEastWestPosition += value;
            } else if (command.equals("W")) {
                wayPointEastWestPosition -= value;
            } else if (command.equals("R")) {
                var runAmount = value / 90;
                for (var i = 0; i < runAmount; i++) {
                    var oldX = wayPointEastWestPosition;
                    var oldY = wayPointNorthSouthPosition;
                    wayPointEastWestPosition = oldY;
                    wayPointNorthSouthPosition = -oldX;
                }
            } else if (command.equals("L")) {
                var runAmount = value / 90;
                for (var i = 0; i < runAmount; i++) {
                    var oldX = wayPointEastWestPosition;
                    var oldY = wayPointNorthSouthPosition;
                    wayPointEastWestPosition = -oldY;
                    wayPointNorthSouthPosition = oldX;
                }
            } else if (command.equals("F")) {
                eastWestPosition += value * wayPointEastWestPosition;
                northSouthPosition += value * wayPointNorthSouthPosition;
            }
        }
        log("Part 2 Manhattan distance: " + (Math.abs(northSouthPosition) + Math.abs(eastWestPosition)));
    }
}