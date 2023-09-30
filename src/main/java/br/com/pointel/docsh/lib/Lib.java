package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.List;

public class Lib {

    public static List<Found> search(File path, String words, double tolerance) throws Exception {
        return new Search(path, words.split(Source.SPACER_REGEX), tolerance).start();
    }

}
