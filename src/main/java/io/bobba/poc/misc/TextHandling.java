package io.bobba.poc.misc;

public class TextHandling {
    public static String getFloatString(double k) {
        return String.valueOf(k).replace(',', '.');
    }
}
