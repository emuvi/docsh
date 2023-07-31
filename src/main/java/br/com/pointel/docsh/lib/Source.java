package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Source {

    private final File file;
    private final String source;
    private final List<String> contents;

    public Source(File file, String source) {
        this.file = file;
        this.source = source;
        this.contents = new ArrayList<>();
        var words = source.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            var word = Words.getNormalized(words[i]);
            if (!word.isBlank()) {
                contents.add(word);
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
