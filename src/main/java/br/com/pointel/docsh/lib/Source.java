package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Source {

    public static final Pattern SPACER = Pattern.compile("\\s+");

    public final File file;
    public final String source;
    public final List<WordMap> contents;

    public Source(File file, String source) {
        this.file = file;
        this.source = source;
        this.contents = new ArrayList<>();
        var matcher = SPACER.matcher(source);
        var done = 0;
        while (matcher.find()) {
            var start = done;
            var end = matcher.start();
            var part = source.substring(start, end);
            var word = Words.getNormalized(part);
            if (!word.isBlank()) {
                contents.add(new WordMap(word, start, end));
            }
            done = matcher.end();
        }
        if (done < source.length()) {
            var start = done;
            var end = source.length();
            var part = source.substring(start, end);
            var word = Words.getNormalized(part);
            if (!word.isBlank()) {
                contents.add(new WordMap(word, start, end));
            }
        }
    }

    public Found search(List<String> words) {
        List<Scored> scores = new ArrayList<>();
        for (var word : words) {
            var points = Words.search(contents, word);
            if (points.isEmpty()) {
                return null;
            }
            scores.add(new Scored(word, points));
        }
        return new Found(file, source, contents, scores);
    }

}
