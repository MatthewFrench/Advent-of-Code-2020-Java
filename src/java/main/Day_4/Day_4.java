package main.Day_4;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static main.Utility.*;

class Day_4 {
    static Set<Character> VALID_HCL_CHARACTERS = Set.of('a', 'b', 'c', 'd', 'e', 'f', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    static Set<String> VALID_ECL_VALUES = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    static Map<String, Function<String, Boolean>> FIELD_VALIDATION = Map(
            MapPair("byr", (value) -> {
                if (value.length() != 4) {
                    return false;
                }
                var num = Integer.parseInt(value);
                return num >= 1920 && num <= 2002;
            }),
            MapPair("iyr", (value) -> {
                if (value.length() != 4) {
                    return false;
                }
                var num = Integer.parseInt(value);
                return num >= 2010 && num <= 2020;
            }),
            MapPair("eyr", (value) -> {
                if (value.length() != 4) {
                    return false;
                }
                var num = Integer.parseInt(value);
                return num >= 2020 && num <= 2030;
            }),
            MapPair("hgt", (value) -> {
                if (stringChunkExistsAtEnd(value, "cm")) {
                    var value2 = stringRemoveEndChunk(value, "cm");
                    var num = Integer.parseInt(value2);
                    return num >= 150 && num <= 193;
                } else if (stringChunkExistsAtEnd(value, "in")) {
                    var value2 = stringRemoveEndChunk(value, "in");
                    var num = Integer.parseInt(value2);
                    return num >= 59 && num <= 76;
                } else {
                    return false;
                }
            }),
            MapPair("hcl", (value) -> {
                if (stringChunkExistsAtStart(value, "#")) {
                    var value2 = stringRemoveStartChunk(value, "#");
                    if (value2.length() == 6) {
                        return value2.chars().mapToObj(c -> (char)c).allMatch(VALID_HCL_CHARACTERS::contains);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }),
            MapPair("ecl", VALID_ECL_VALUES::contains),
            MapPair("pid", (value) -> {
                if (value.length() == 9) {
                    return value.chars().allMatch(Character::isDigit);
                } else {
                    return false;
                }
            })
    );

    public static void main(String[] args) throws Exception {
        log("Day 4");
        final String input = loadTextFile(Day_4.class, "input.txt");
        List<Map<String, String>> credentials = Arrays.stream(input.split("\n\n"))
                .map(cred -> cred.replace("\n", " ").replace("\r", " "))
                .map(cred -> splitString(cred, " "))
                .map(credList -> credList.stream()
                        .map(piece -> splitString(piece, ":"))
                        .collect(Collectors.toMap(pieceSplit -> pieceSplit.get(0), pieceSplit -> pieceSplit.get(1).trim()))
                ).collect(Collectors.toList());
        var firstPassValidCredentials = credentials.stream()
                .filter(credential -> credential.keySet().containsAll(FIELD_VALIDATION.keySet()))
                .peek(credential -> credential.keySet().removeIf(piece -> !FIELD_VALIDATION.containsKey(piece)))
                .collect(Collectors.toList());

        log("Part 1 valid: " + firstPassValidCredentials.size());

        var validCount = firstPassValidCredentials.stream()
                .filter(credential -> credential.entrySet().stream()
                        .allMatch(entry -> FIELD_VALIDATION.get(entry.getKey()).apply(entry.getValue()))).count();
        log("Part 2 valid: " + validCount);
    }
}