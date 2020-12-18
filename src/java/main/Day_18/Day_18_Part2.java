package main.Day_18;

import java.util.List;

import static main.Utility.*;

class Day_18_Part2 {
    public static void main(String[] args) throws Exception {
        log(Day_18_Part2.class.getName());
        final List<String> input = loadTextFileAsList(Day_18.class, "input.txt");

        long totalSum = 0;
        for (var line : input) {
            totalSum += evaluateInstruction(line.replaceAll("\\s+",""));
        }
        log("Part 2 total sum: " + totalSum);
    }
    public static long evaluateInstruction(String line) {
        if (countStringInstanceInString(line, "(") > 0) {
            // First split instructions by top level parenthesis
            var newEquation = "";
            var currentLine = "";
            var parenthesis = 0;
            for (var i = 0; i < line.length(); i++) {
                var character = getStringChunk(line, i, 1);
                if (parenthesis == 0 && character.equals("(")) {
                    parenthesis += 1;
                    if (currentLine.length() > 0) {
                        newEquation += currentLine;
                    }
                    currentLine = "";
                } else if (character.equals("(")) {
                    parenthesis += 1;
                    currentLine += character;
                } else if (character.equals(")") && parenthesis == 1) {
                    parenthesis = 0;
                    newEquation += evaluateInstruction(currentLine);
                    currentLine = "";
                } else if (character.equals(")")) {
                    parenthesis -= 1;
                    currentLine += character;
                } else {
                    currentLine += character;
                }
            }
            if (currentLine.length() > 0) {
                newEquation += currentLine;
            }
            line = newEquation;
        }
        // Process any addition
        if (countStringInstanceInString(line, "+") > 0) {
            var firstValueString = getNextValue(line, 0);
            var index = firstValueString.length();
            var newEquation = "";
            while (index < line.length()) {
                var character = getStringChunk(line, index, 1);
                var secondValueString = getNextValue(line, index + 1);
                if (character.equals("+")) {
                    firstValueString = Long.toString(Long.parseLong(firstValueString) + Long.parseLong(secondValueString));
                } else if (character.equals("*")) {
                    newEquation += firstValueString + character;
                    firstValueString = secondValueString;
                } else {
                    log("Uh oh mistake in: " + line);
                }
                index += character.length() + secondValueString.length();
            }
            newEquation += firstValueString;
            line = newEquation;
        }
        {
            var firstValueString = getNextValue(line, 0);
            if (firstValueString.length() == line.length()) {
                return Long.parseLong(firstValueString);
            }
            var currentValue = Long.parseLong(firstValueString);
            var index = firstValueString.length();
            while (index < line.length()) {
                var character = getStringChunk(line, index, 1);
                var value2 = getNextValue(line, index + 1);
                if (character.equals("*")) {
                    currentValue = currentValue * Long.parseLong(value2);
                } else {
                    log("Uh oh mistake in: " + line);
                }
                index += character.length() + value2.length();
            }
            return currentValue;
        }
    }
    static String getNextValue(String line, int index) {
        String value = "";
        for (var i = index; i < line.length(); i++) {
            var character = getStringChunk(line, i, 1);
            if (!character.equals("+") && !character.equals("*")) {
                value += character;
            } else {
                break;
            }
        }
        return value;
    }
}