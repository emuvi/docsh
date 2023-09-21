package br.com.pointel.docsh.lib;

import java.io.File;

public class Reader {

    private final File file;

    public Reader(File file) {
        this.file = file;
    }

    public String load() throws Exception {
        var check = file.getName().toLowerCase();
        if (check.endsWith(".txt")) {
            return new ReaderTXT(file).load();
        } else if (check.endsWith(".pdf")) {
            return new ReaderPDF(file).load();
        } else {
            var readerMS = new ReaderMS(file);
            if (readerMS.canExtract()) {
                return readerMS.extractText();
            } else {
                return null;
            }
        }
    }

}
