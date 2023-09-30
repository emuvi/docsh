package br.com.pointel.docsh.lib;

public class WordMap {

    public final String origin;
    public final String word;
    public final Integer start;
    public final Integer end;

    public WordMap(String origin, String word, Integer start, Integer end) {
        this.origin = origin;
        this.word = word;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "( '" + origin + "' -> " + start + " : " + end + " )";
    }

}
