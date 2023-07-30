package br.com.pointel.docsh.lib;

import java.io.File;

public class Source {
    
    private final File file;
    private final String source;
    private final String[] words;

    public Source(File file, String source) {
        this.file = file;
        this.source = source;
        this.words = source.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = Words.getNormalized(words[i]);
        }
    }

}
