package main.Day_4;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static main.Utility.*;

class Day_4 {
    public static void main(String[] args) throws Exception {
        log("Day 4");
        final String input = loadTextFile(Day_4.class, "input.txt");
        List<Map<String, String>> credentials = Arrays.stream(input.split("\n\n"))
                .map(cred -> cred.replace("\n", " ").replace("\r", " "))
                .map(cred -> splitString(cred, " "))
                .map(credList -> credList.stream().map(piece -> {
                    var pieceSplit = splitString(piece, ":");
                    return pieceSplit;
                }).collect(Collectors.toMap(pieceSplit -> pieceSplit.get(0), pieceSplit -> pieceSplit.get(1).trim()))).collect(Collectors.toList());
        List<String> validFields = Pair("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        var valid = credentials.stream().filter(credential -> {
            for (var field : validFields) {
                if (credential.get(field) == null) {
                    return false;
                }
                var value = credential.get(field);
                if (field.equals("byr")) {
                    if (value.length() != 4) {
                        return false;
                    }
                    var num = Integer.parseInt(value);
                    if (num < 1920 || num > 2002) {
                        return false;
                    }
                }
                if (field.equals("iyr")) {
                    if (value.length() != 4) {
                        return false;
                    }
                    var num = Integer.parseInt(value);
                    if (num < 2010 || num > 2020) {
                        return false;
                    }
                }
                if (field.equals("eyr")) {
                    if (value.length() != 4) {
                        return false;
                    }
                    var num = Integer.parseInt(value);
                    if (num < 2020 || num > 2030) {
                        return false;
                    }
                }
                if (field.equals("hgt")) {
                    if (stringChunkExistsAtLocation(value, "cm", value.length() - 2)) {
                        var value2 = splitString(value, "cm").get(0);
                        var num = Integer.parseInt(value2);
                        if (num < 150 || num > 193) {
                            return false;
                        }
                    } else if (stringChunkExistsAtLocation(value, "in", value.length() - 2)) {
                        var value2 = splitString(value, "in").get(0);
                        var num = Integer.parseInt(value2);
                        if (num < 59 || num > 76) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                if (field.equals("hcl")) {
                    if (stringChunkExistsAtLocation(value, "#", 0)) {
                        var value2 = splitString(value, "#").get(1);
                        if (value2.length() == 6) {
                            var isValid = value2.chars().allMatch(x -> x == 'a' || x == 'b' || x == 'c' || x == 'd' || x == 'e' || x == 'f' || x == '0' || x == '1' || x == '2' || x == '3' || x == '4' || x == '5' || x == '6' || x == '7' || x == '8' || x == '9');
                            if (!isValid) {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                if (field.equals("ecl")) {
                    if (!(value.equals("amb") || value.equals("blu") || value.equals("brn") || value.equals("gry") || value.equals("grn") || value.equals("hzl") || value.equals("oth"))) {
                        return false;
                    }
                }
                if (field.equals("pid")) {
                    if (value.length() == 9) {
                        var isNumber = value.chars().allMatch(x -> Character.isDigit(x));
                        if (!isNumber) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
            return true;
        }).count();
        log("Part 1 valid: " + valid);
    }
}