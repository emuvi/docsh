package br.com.pointel.docsh.lib;

public class WordMap {

    public final String word;
    public final Integer start;
    public final Integer end;

    public WordMap(String word, Integer start, Integer end) {
        this.word = word;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "( " + start + " , " + end + " )";
    }

}
