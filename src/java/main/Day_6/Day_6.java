package main.Day_6;


import java.util.*;
import java.util.stream.Collectors;

import static main.Utility.*;

class Day_6 {
    public static void main(String[] args) throws Exception {
        log(Day_6.class.getName());
        final String input = loadTextFile(Day_6.class, "input.txt");
        {
            // Todo split into StringUtil
            List<String> groups = splitString(input, "\n\n");
            int sum = 0;
            for (var group : groups) {
                // Todo make an easy function
                group = group.replaceAll("\\s+", "");
                // Todo make an easy function
                var set = new HashSet<>(group.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
                sum += set.size();
            }
            log("Sum: " + sum);
        }
        List<String> groups = splitString(input,"\n\n");
        int sum = 0;
        for (var group : groups) {
            // Todo determine if there is a way to union sets or make this more succinct
            var map = new HashMap<Character, Integer>();
            var count = 0;
            for (var person : splitString(group, "\n")) {
                count += 1;
                // Todo, make an easy way to loop through letters, it is a pain using chars
                for (char c : person.toCharArray()) {
                    map.put(c, map.getOrDefault(c, 0)+1);
                }
            }
            for (var key : map.keySet()) {
                if (map.get(key) == count) {
                    sum += 1;
                }
            }
        }
        log("Sum: "+sum);
    }
}