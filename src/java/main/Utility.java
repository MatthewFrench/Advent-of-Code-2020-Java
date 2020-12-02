package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utility {
    final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /*
    String utilities
     */
    public static List<String> splitString(String target, String splitBy) {
        return Arrays.asList(target.split(Pattern.quote(splitBy)).clone());
    }
    public static String getStringChunk(String target, int index, int length) {
        return target.substring(index, index + length);
    }
    public static int countStringInstanceInString(String target, String count) {
        return target.length() - target.replaceAll(Pattern.quote(count), "").length();
    }
    public static boolean stringChunkEqualsAtLocation(String target, String chunk, int position) {
        if (target.length() >= position + chunk.length()) {
            var chunk1 = getStringChunk(target, position, chunk.length());
            return chunk1.equals(chunk);
        }
        return false;
    }
    /*
    Logging
     */
    public static void log(final String text) {
        System.out.println(text);
    }
    public static void logObject(final Object object) {
        try {
            log(OBJECT_MAPPER.writeValueAsString(object));
        } catch (final JsonProcessingException e) {
            log("Failed to turn object into JSON: " + object.toString());
        }
    }
    /*
    Loading text
     */
    public static String loadTextFile(final String name) throws Exception {
        return String.join("", loadTextFileAsList(name));
    }
    public static List<String> loadTextFileAsList(final String name) throws Exception {
        final URL path = ClassLoader.getSystemResource(name);
        if (path == null) {
            throw new Exception("Invalid text file: " + name);
        }
        File f = new File(path.toURI());
        BufferedReader reader = new BufferedReader(new FileReader(f));
        return reader.lines().collect(Collectors.toList());
    }
    public static List<Integer> loadTextFileAsIntList(final String name) throws Exception {
        final URL path = ClassLoader.getSystemResource(name);
        if (path == null) {
            throw new Exception("Invalid text file: " + name);
        }
        File f = new File(path.toURI());
        BufferedReader reader = new BufferedReader(new FileReader(f));
        return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
    }
}