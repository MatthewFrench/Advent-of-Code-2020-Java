package main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utility {
    final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /*
    Object oriented utilities
     */
    // Creates multiple pairs using the given list of values and dimension size.
    @SafeVarargs
    public static <T> List<List<T>> MultiPair(final int dimensions, final T... a) {
        final ArrayList<T> elementList = new ArrayList<>(Arrays.asList(a));
        List<List<T>> lists = new ArrayList<>();
        for (int i = 0; i < elementList.size(); i += dimensions) {
            int end = Math.min(elementList.size(), i + dimensions);
            lists.add(elementList.subList(i, end));
        }
        return lists;
    }
    @SafeVarargs
    public static <T> List<T> Pair(final T... a) {
        return new ArrayList<>(Arrays.asList(a));
    }
    /*
    String utilities
     */
    public static List<String> splitString(final String target, final String splitBy) {
        return Arrays.asList(target.split(Pattern.quote(splitBy)).clone());
    }
    public static String getStringChunk(final String target, final int index, final int length) {
        return target.substring(index, index + length);
    }
    public static int countStringInstanceInString(final String target, final String count) {
        return target.length() - target.replaceAll(Pattern.quote(count), "").length();
    }
    public static boolean stringChunkExistsAtLocation(final String target, final String chunk, final int location) {
        if (target.length() >= location + chunk.length()) {
            return getStringChunk(target, location, chunk.length()).equals(chunk);
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
    public static Stream<String> loadTextFileStream(final Class<?> loadFromClass, final String name) throws Exception {
        final InputStream stream = loadFromClass.getResourceAsStream(name);
        if (stream == null) {
            throw new Exception("Invalid text file: " + name);
        }
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        return reader.lines();
    }
    public static String loadTextFile(final Class<?> loadFromClass, final String name) throws Exception {
        return loadTextFileStream(loadFromClass, name).collect(Collectors.joining("\n"));
    }
    public static List<String> loadTextFileAsList(final Class<?> loadFromClass, final String name) throws Exception {
        return loadTextFileStream(loadFromClass, name).collect(Collectors.toList());
    }
    public static <R> List<R> loadTextFileAsTypeList(final Class<?> loadFromClass, final String name, Function<? super String, ? extends R> mapperFunction) throws Exception {
        return loadTextFileStream(loadFromClass, name).map(mapperFunction).collect(Collectors.toList());
    }
}