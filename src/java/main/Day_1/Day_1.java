package main.Day_1;
import main.Utility;
import java.util.List;

class Day_1 {
    public static void main(String[] args) throws Exception {
        Utility.log("Day 1");
        final int targetValue = 2020;
        final List<Integer> input = Utility.loadTextFileAsIntList("main/Day_1/input.txt");
        for (var index1 = 0; index1 < input.size(); index1++) {
            var number1 = input.get(index1);
            for (var index2 = index1 + 1; index2 < input.size(); index2++) {
                var number2 = input.get(index2);
                if (number1 + number2 == targetValue) {
                    Utility.log("Solution 1");
                    Utility.log("Number 1: " + number1);
                    Utility.log("Number 2: " + number2);
                    Utility.log("Multiplied: " + (number1 * number2));
                }
                for (var index3 = index2 + 1; index3 < input.size(); index3++) {
                    var number3 = input.get(index3);
                    if (number1 + number2 + number3 == targetValue) {
                        Utility.log("Solution 2");
                        Utility.log("Number 1: " + number1);
                        Utility.log("Number 2: " + number2);
                        Utility.log("Number 3: " + number3);
                        Utility.log("Multiplied: " + (number1 * number2 * number3));
                    }
                }
            }
        }
    }
}