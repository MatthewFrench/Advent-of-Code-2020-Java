package main.Day_25;

import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.Utility.*;

class Day_25 {
    public static void main(String[] args) throws Exception {
        log(Day_25.class.getName());
        final List<String> input = loadTextFileAsList(Day_25.class, "input.txt");
        // Card uses a specific secret loop size
        // Door uses a different secret loop size.
        long subjectNumber = 7;
        // Card public key = transformSubjectNumber based on the loop size with subject number 7
        // Door public key = transformSubjectNumber based on door secret loop size with subject number 7
        // You have public keys but neither device's loop size
        // Door public key gets transformed by subject Number to the card's loop size. Creating the encryption key
        // Card public key gets transformed by subject number to the door's loop size. Creating the encryption key

        // Use the 2 public keys to find each loop size. Then calculate the encryption keys
        long initialSubjectNumber = 7;

        long cardPublicKey = 15628416; //5764801;
        log("Calculating card loop size");
        // find card loop size
        long cardLoopSize = findLoopSize(cardPublicKey, initialSubjectNumber);

        long doorPublicKey = 11161639; //17807724;
        log("Calculating door loop size");
        //find door loop size
        long doorLoopSize = findLoopSize(doorPublicKey, initialSubjectNumber);

        log("Calculating encryption key 1");
        long encryptionKeyOne = transformSubjectNumber(cardLoopSize, doorPublicKey); // Should produce 14897079
        log("Calculating encryption key 2");
        long encryptionKeyTwo = transformSubjectNumber(doorLoopSize, cardPublicKey); // Should produce 14897079

        log("Encryption keys: " + encryptionKeyOne + ", " + encryptionKeyTwo);
    }
    public static long transformSubjectNumber(long loopSize, long subjectNumber) {
        return transformSubjectNumber(loopSize, subjectNumber, 0, 1);
    }
    public static long transformSubjectNumber(long loopSize, long subjectNumber, long startAtLoopSize, long startValue) {
        long value = startValue;
        for (var i = startAtLoopSize; i < loopSize; i++) {
            value = value * subjectNumber;
            value = value % 20201227;
        }
        return value;
    }
    public static long findLoopSize(long publicKey, long subjectNumber) {
        long loopSize = 0;
        long lastCheckedValue = 1;
        while (true) {
            loopSize += 1;
            var value = transformSubjectNumber(loopSize, subjectNumber, loopSize - 1, lastCheckedValue);
            lastCheckedValue = value;
            if (value == publicKey) {
                return loopSize;
            }
        }
    }
}