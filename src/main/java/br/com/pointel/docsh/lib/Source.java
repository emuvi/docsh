package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, List<Integer>> points = new HashMap<>();
        for (var word : words) {
            var indexes = Words.search(contents, word);
            if (indexes.isEmpty()) {
                return null;
            }
            points.put(word, indexes);
        }
        return new Found(file, source, contents, points);
    }

}
