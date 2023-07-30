package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.List;

public class Lib {

    public static List<Found> search(File path, String words) throws Exception {
        return new Search(path, words.split("\\s+")).start();
    }

}
