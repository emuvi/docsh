package br.com.pointel.docsh.lib;

public class Score {
    
    public final WordMap wordMap;
    public final double similarity;

    public Score(WordMap wordMap, double similarity) {
        this.wordMap = wordMap;
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "( " + wordMap + " , " + String.format("%.2f", similarity) + " )";
    }
    
}
