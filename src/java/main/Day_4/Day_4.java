package main.Day_4;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static main.Utility.*;

class Day_4 {
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
                if (stringChunkExistsAtLocation(value, "cm", value.length() - 2)) {
                    var value2 = splitString(value, "cm").get(0);
                    var num = Integer.parseInt(value2);
                    return num >= 150 && num <= 193;
                } else if (stringChunkExistsAtLocation(value, "in", value.length() - 2)) {
                    var value2 = splitString(value, "in").get(0);
                    var num = Integer.parseInt(value2);
                    return num >= 59 && num <= 76;
                } else {
                    return false;
                }
            }),
            MapPair("hcl", (value) -> {
                if (stringChunkExistsAtLocation(value, "#", 0)) {
                    var value2 = splitString(value, "#").get(1);
                    if (value2.length() == 6) {
                        return value2.chars().allMatch(x -> x == 'a' || x == 'b' || x == 'c' || x == 'd' || x == 'e' || x == 'f' || x == '0' || x == '1' || x == '2' || x == '3' || x == '4' || x == '5' || x == '6' || x == '7' || x == '8' || x == '9');
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }),
            MapPair("ecl", (value) -> value.equals("amb") || value.equals("blu") || value.equals("brn") || value.equals("gry") || value.equals("grn") || value.equals("hzl") || value.equals("oth")),
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