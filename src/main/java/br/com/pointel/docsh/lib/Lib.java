package br.com.pointel.docsh.lib;

import java.io.File;
import java.util.List;

public class Lib {

    public static List<Found> search(File path, String search) throws Exception {
        return new Search(path, search.split("\\s+")).start();
    }

}
