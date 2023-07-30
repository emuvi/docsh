package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Search {

    private final File folder;
    private final String[] words;

    public Search(File folder, String[] words) {
        this.folder = folder;
        this.words = words;
        for (int i = 0; i < words.length; i++) {
            words[i] = Words.getNormalized(words[i]);
        }
    }

    public List<Found> start() throws Exception {
        var sources = new SourcesLoader(folder).start();
        List<Found> results = new ArrayList<>();
        for (var source : sources) {
            results.add(source.search(words));
        }
        return results;
    }
    
}
