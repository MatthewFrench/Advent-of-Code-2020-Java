package main.Day_19;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.Utility.*;

class Day_19 {
    public static void main(String[] args) throws Exception {
        log(Day_19.class.getName());
        final String input = loadTextFile(Day_19.class, "input.txt");
        final List<String> rawRules = splitString(splitString(input, "\n\n").get(0), "\n");
        final List<String> messages = splitString(splitString(input, "\n\n").get(1), "\n");
        final Map<Integer, Rule> rules = new HashMap<>();
        for (var rawRule : rawRules) {
            var rule = new Rule(rawRule);
            rules.put(rule.index, rule);
        }

        // Count the invalid messages
        var matchingMessages = 0;
        for (var message : messages) {
            if (isValid(rules, List(0), message)) {
                matchingMessages += 1;
            }
        }
        log("Part 1/2 correct messages: " + matchingMessages);
    }
    // Returns true/false if matched, then returns the remainder
    static Boolean isValid(final Map<Integer, Rule> rules, final List<Integer> rulesToCompute, String message) {
        if (rulesToCompute.size() == 0) {
            if (message.length() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (message.length() == 0) {
            return false;
        }
        var firstRuleNumber = rulesToCompute.remove(0);
        var firstRule = rules.get(firstRuleNumber);
        if (firstRule.ruleLists.size() == 0) {
            if (stringChunkExistsAtStart(message, firstRule.value)) {
                message = stringRemoveStartChunk(message, firstRule.value);
                return isValid(rules, new ArrayList<>(rulesToCompute), message);
            } else {
                return false;
            }
        }
        // There are branches to consider in rule lists
        for (var ruleList : firstRule.ruleLists) {
            var newRulesToCompute = new ArrayList<>(rulesToCompute);
            newRulesToCompute.addAll(0, ruleList);
            if (isValid(rules, newRulesToCompute, message)) {
                return true;
            }
        }
        return false;
    }
    static class Rule {
        public int index;
        public List<List<Integer>> ruleLists = new ArrayList<>();
        public String value = "";
        Rule(String rawRule) {
            var split = splitString(rawRule, ": ");
            index = Integer.parseInt(split.get(0));
            var rightHalf = split.get(1);
            if (countStringInstanceInString(rightHalf, "\"") > 0) {
                var split2 = splitString(rightHalf, "\"");
                value = split2.get(1);
            } else {
                var split2 = splitString(split.get(1), " | ");
                for (var rawOrPath : split2) {
                    List<Integer> ruleList = new ArrayList<>();
                    var ruleNumbers = splitString(rawOrPath, " ");
                    for (var ruleNumber : ruleNumbers) {
                        var number = Integer.parseInt(ruleNumber);
                        ruleList.add(number);
                    }
                    ruleLists.add(ruleList);
                }
            }
        }
    }
}