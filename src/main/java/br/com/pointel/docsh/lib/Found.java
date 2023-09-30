package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.List;

public class Found {

    public final File file;
    public final String source;
    public final List<WordMap> contents;
    public final List<Scored> scores;
    public final double penalty;
    public final Double weighted;

    public Found(File file, String source, List<WordMap> contents, List<Scored> scores, double penalty) {
        this.file = file;
        this.source = source;
        this.contents = contents;
        this.scores = scores;
        this.penalty = penalty;
        this.weighted = ponderScores();
    }

    private Double ponderScores() {
        var result = 0.0;
        for (var score : scores) {
            for(var point : score.points) {
                result += (100.0 * point.similarity);
            }
        }
        result = result - (result * penalty);
        return result;
    }

    @Override
    public String toString() {
        return "{ " + file.getName() + " : " + String.format("%.2f", weighted)  + " }";
    }

}
