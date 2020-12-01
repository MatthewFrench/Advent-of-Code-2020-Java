package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class Utility {
    final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
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
    public static String loadTextFile(final String name) throws Exception {
        return String.join("", loadTextFileAsArray(name));
    }
    public static List<String> loadTextFileAsArray(final String name) throws Exception {
        final URL path = ClassLoader.getSystemResource(name);
        if (path == null) {
            throw new Exception("Invalid text file: " + name);
        }
        File f = new File(path.toURI());
        BufferedReader reader = new BufferedReader(new FileReader(f));
        return reader.lines().collect(Collectors.toList());
    }
    public static List<Integer> loadTextFileAsIntArray(final String name) throws Exception {
        final URL path = ClassLoader.getSystemResource(name);
        if (path == null) {
            throw new Exception("Invalid text file: " + name);
        }
        File f = new File(path.toURI());
        BufferedReader reader = new BufferedReader(new FileReader(f));
        return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
    }
}