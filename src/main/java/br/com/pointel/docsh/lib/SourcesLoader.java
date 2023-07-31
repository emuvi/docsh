package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourcesLoader {

    private final static Map<File, Source> CACHED = new HashMap<>(); 
    
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
            var cached = CACHED.get(path);
            if (cached != null) {
                sources.add(cached);
            } else {
                var text = new Reader(path).load();
                if (text != null && !text.isBlank()) {
                    var source = new Source(path, text);
                    sources.add(source);
                    CACHED.put(path, source);
                }
            }
        }
    }

}
