package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.List;

public class Search {

    private final File folder;
    private final String[] words;
    
    private List<Source> sources;

    public Search(File folder, String[] words) {
        this.folder = folder;
        this.words = words;
        for (int i = 0; i < words.length; i++) {
            words[i] = Words.getNormalized(words[i]);
        }
    }

    public List<Found> start() throws Exception {
        sources = new SourcesLoader(folder).load();
        return null;
    }

    public void load(File path) throws Exception {

    }
    
}
