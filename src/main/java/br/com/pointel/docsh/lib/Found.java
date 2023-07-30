package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Found {

    public final File file;
    public final String source;
    public final List<String> contents;
    public final Map<String, List<Integer>> points;

    public Found(File file, String source, List<String> contents, Map<String,List<Integer>> points) {
        this.file = file;
        this.source = source;
        this.contents = contents;
        this.points = points;
    }

}
