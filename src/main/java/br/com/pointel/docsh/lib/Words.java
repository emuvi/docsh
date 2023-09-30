package br.com.pointel.docsh.lib;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class Words {

    public static Double DEFAULT_TOLERANCE = 0.72;

    public static List<Score> search(List<WordMap> contents, String word) {
        return search(contents, word, DEFAULT_TOLERANCE);
    }

    public static List<Score> search(List<WordMap> contents, String word, Double tolerance) {
        List<Score> results = new ArrayList<>();
        for (var mapped : contents) {
            var similarity = getSimilarity(mapped.word, word);
            if (similarity > tolerance) {
                results.add(new Score(mapped, similarity));
            }
        }
        return results;
    }

    public static boolean isMatch(String s1, String s2) {
        return isMatch(s1, s2, DEFAULT_TOLERANCE);
    }

    public static boolean isMatch(String s1, String s2, Double limit) {
        return getSimilarity(s1, s2) > limit;
    }

    public static double getSimilarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }
        var result = (longerLength - getEditDistance(longer, shorter)) / (double) longerLength;
        return result;
    }

    public static int getEditDistance(String s1, String s2) {
        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }

    public static String getNormalized(String str) {
        str = str.toLowerCase();
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        str = str.replaceAll("\\W+", "");
        return str;
    }

}
