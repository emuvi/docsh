package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SourcesLoader {
    
    private final File root;
    private final List<Source> sources;

    public SourcesLoader(File root) {
        this.root = root;
        this.sources = new ArrayList<>();
    }

    public List<Source> start() throws Exception {
        load(root);
        return sources;
    }

    private void load(File path) throws Exception {
        if (path.isDirectory()) {
            for (File inside : path.listFiles()) {
                load(inside);
            }
        } else {
            var text = new Reader(path).load();
            if (text != null && !text.isBlank()) {
                sources.add(new Source(path, text));
            }
        }
    }

}
