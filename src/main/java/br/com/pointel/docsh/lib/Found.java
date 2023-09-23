package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.List;

public class Found {

    public final File file;
    public final String source;
    public final List<WordMap> contents;
    public final List<Scored> scores;

    public Found(File file, String source, List<WordMap> contents, List<Scored> scores) {
        this.file = file;
        this.source = source;
        this.contents = contents;
        this.scores = scores;
    }

    public Double ponderScores() {
        var result = 0.0;
        for (var score : scores) {
            result += score.points.size();
        }
        return result;
    }

    @Override
    public String toString() {
        return "{ " + file.getName() + " : " + ponderScores() + " }";
    }

}
