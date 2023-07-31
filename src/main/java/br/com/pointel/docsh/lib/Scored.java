package br.com.pointel.docsh.lib;

import java.util.List;

public class Scored {
    
    public final String word;
    public final List<Integer> points;

    public Scored(String word, List<Integer> points) {
        this.word = word;
        this.points = points;
    }

    @Override
    public String toString() {
        return word + " : " + points.size();
    }

}
