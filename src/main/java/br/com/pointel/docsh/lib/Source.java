package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Source {

    public static final String SPACER_REGEX = "([^\\p{L}\\p{N}])+";
    public static final Pattern SPACER_PATTERN = Pattern.compile(SPACER_REGEX);

    public final File file;
    public final String source;
    public final List<WordMap> contents;

    public Source(File file, String source) {
        this.file = file;
        this.source = source;
        this.contents = new ArrayList<>();
        var matcher = SPACER_PATTERN.matcher(source);
        var done = 0;
        while (matcher.find()) {
            var start = done;
            var end = matcher.start();
            var origin = source.substring(start, end);
            var word = Words.getNormalized(origin);
            if (!word.isBlank()) {
                contents.add(new WordMap(origin, word, start, end));
            }
            done = matcher.end();
        }
        if (done < source.length()) {
            var start = done;
            var end = source.length();
            var origin = source.substring(start, end);
            var word = Words.getNormalized(origin);
            if (!word.isBlank()) {
                contents.add(new WordMap(origin, word, start, end));
            }
        }
    }

    public Found search(List<String> words, double tolerance) {
        List<Scored> scores = new ArrayList<>();
        for (var word : words) {
            var points = Words.search(contents, word, tolerance);
            if (points.isEmpty()) {
                return null;
            }
            scores.add(new Scored(word, points));
        }
        
        var penalty = 0.0;
        if (words.size() > 1) {
            var allDistance = 0;
            for (int iA = 0; iA < scores.size(); iA++) {
                var scoredA = scores.get(iA);
                for (int iB = iA + 1; iB < scores.size(); iB++) {
                    var scoredB = scores.get(iB);
                    allDistance += getShortestDistance(scoredA, scoredB);
                }
            }
            penalty = (double) allDistance / (double) source.length();
            if (penalty > 1) { 
                penalty = 1;
            }
        }
        return new Found(file, source, contents, scores, penalty);
    }
    
    private int getShortestDistance(Scored scoredA, Scored scoredB) {
        var result = source.length();
        for (var pointA : scoredA.points) {
            for (var pointB : scoredB.points) {
                result = Math.min(result, getDistance(pointA, pointB));
            }
        }
        return result;
    }
    
    private int getDistance(Score pointA, Score pointB) {
        if (pointA.wordMap.start > pointB.wordMap.start) {
            return pointA.wordMap.start - pointB.wordMap.end;
        } else {
            return pointB.wordMap.start - pointA.wordMap.end;
        }
    }

}
