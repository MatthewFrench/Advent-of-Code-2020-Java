package main.Day_16;

import java.util.*;
import java.util.stream.Collectors;

import static main.Utility.*;

class Day_16 {
    public static void main(String[] args) throws Exception {
        log(Day_16.class.getName());
        final String input = loadTextFile(Day_16.class, "input.txt");
        final List<String> section = splitString(input, "\n\n");
        List<String> rawDefinitions = splitString(section.get(0), "\n");
        String yourTicket = section.get(1);
        List<String> otherTickets = splitString(section.get(2), "\n");
        List<Definition> definitions = new ArrayList<>();
        for (var rawDefinition : rawDefinitions) {
            definitions.add(new Definition(rawDefinition));
        }
        List<Integer> invalidValues = new ArrayList<>();
        List<String> validTickets = new ArrayList<>();
        for (var i = 1; i < otherTickets.size(); i++) {
            var rawTicket = otherTickets.get(i);
            List<String> rawValues = splitString(rawTicket, ",");
            var isValidTicket = true;
            for (var rawValue : rawValues) {
                var value = Integer.parseInt(rawValue);
                var isValid = false;
                for (var definition : definitions) {
                    if (definition.isValid(value)) {
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    invalidValues.add(value);
                    isValidTicket = false;
                }
            }
            if (isValidTicket) {
                validTickets.add(rawTicket);
            }
        }
        var totalErrorRate = 0;
        for (var value : invalidValues) {
            totalErrorRate += value;
        }
        log("Part 1 error rate: " + totalErrorRate);

        Map<Integer, List<Definition>> definitionsForRow = new HashMap<>();
        for (var i = 0; i < splitString(validTickets.get(0), ",").size(); i++) {
            definitionsForRow.put(i, new ArrayList<>(definitions));
        }
        validTickets.add(splitString(yourTicket, "\n").get(1));
        for (var i = 0; i < validTickets.size(); i++) {
            var rawTicket = validTickets.get(i);
            List<String> rawValues = splitString(rawTicket, ",");
            int row = 0;
            for (var rawValue : rawValues) {
                List<Definition> applicableDefinitions = definitionsForRow.get(row);
                var value = Integer.parseInt(rawValue);
                applicableDefinitions.removeIf(definition -> !definition.isValid(value));
                row += 1;
            }
        }
        // Prune definitions now
        Set<Definition> prunedDefinitions = new HashSet<>();
        var isChanging = true;
        while (isChanging) {
            isChanging = false;
            for (var row : definitionsForRow.keySet()) {
                List<Definition> definitionList = definitionsForRow.get(row);
                if (definitionList.size() == 1) {
                    var definition = definitionList.get(0);
                    if (!prunedDefinitions.contains(definition)) {
                        prunedDefinitions.add(definition);
                        isChanging = true;
                        // remove definition from all other rows
                        for (var row2 : definitionsForRow.keySet()) {
                            if (!row.equals(row2)) {
                                definitionsForRow.get(row2).remove(definition);
                            }
                        }
                    }
                } else {
                    // Check each definition to see if all other rows do not contain it
                    // If they don't, it is only for this row
                    for (var definition : definitionList) {
                        var existsInOtherRow = false;
                        for (var row2 : definitionsForRow.keySet()) {
                            if (!row.equals(row2)) {
                                if (definitionsForRow.get(row2).contains(definition)) {
                                    existsInOtherRow = true;
                                    break;
                                }
                            }
                        }
                        if (!existsInOtherRow) {
                            // Remove from all others
                            prunedDefinitions.add(definition);
                            // remove definition from all other rows
                            for (var row2 : definitionsForRow.keySet()) {
                                if (!row.equals(row2)) {
                                    if (definitionsForRow.get(row2).remove(definition)) {
                                        isChanging = true;
                                    }
                                }
                            }
                        }
                    }
                    //if (definitionList.size() > 1) {
                    //    definitionList.removeAll(prunedDefinitions);
                    //}
                }
            }
        }


        for (var row : definitionsForRow.keySet()) {
            List<Definition> definitionSet = definitionsForRow.get(row);
            if (definitionSet.size() > 1 || definitionSet.size() == 0) {
                log("Uh oh on row: " + row);
                logObject(definitionSet);
            }
        }


        List<String> yourTicketSplit = splitString(splitString(yourTicket, "\n").get(1), ",");
        long part2Value = 1;
        for (var row : definitionsForRow.keySet()) {
            Definition definition = definitionsForRow.get(row).get(0);
            if (definition.name.contains("departure")) {
                part2Value *= Long.parseLong(yourTicketSplit.get(row));
            }
        }

        log("Part 2: " + part2Value);
    }
    public static class Definition {
        public String name;
        public List<Range> ranges;
        public Definition(String input) {
            var split = splitString(input, ": ");
            name = split.get(0);
            var ranges = splitString(split.get(1), " or ");
            this.ranges = new ArrayList<>();
            for (var range : ranges) {
                var splitRange = splitString(range, "-");
                var newRange = new Range();
                newRange.start = Integer.parseInt(splitRange.get(0));
                newRange.end = Integer.parseInt(splitRange.get(1));
                this.ranges.add(newRange);
            }
        }
        public boolean isValid(int value) {
            for (var range : ranges) {
                if (value >= range.start && value <= range.end) {
                    return true;
                }
            }
            return false;
        }
    }
    public static class Range {
        public int start;
        public int end;
    }
}