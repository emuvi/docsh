package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Search {

    private final File path;
    private final List<String> words;

    public Search(File path, String[] words) {
        this.path = path;
        this.words = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            var word = Words.getNormalized(words[i]);
            if (!word.isBlank()) {
                this.words.add(word);
            }
        }
    }

    public List<Found> start() throws Exception {
        var sources = new SourcesLoader(path).start();
        List<Found> results = new ArrayList<>();
        for (var source : sources) {
            var found = source.search(words);
            if (found != null) {
                results.add(found);
            }
        }
        Collections.sort(results, (a, b) -> b.ponderScores().compareTo(a.ponderScores()));
        return results;
    }

}
