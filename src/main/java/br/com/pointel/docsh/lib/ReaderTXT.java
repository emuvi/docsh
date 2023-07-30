package br.com.pointel.docsh.lib;

import java.io.File;
import java.nio.file.Files;

public class ReaderTXT {

    private final File file;

    public ReaderTXT(File file) {
        this.file = file;
    }

    public String load() throws Exception {
        return Files.readString(file.toPath());
    }

}
