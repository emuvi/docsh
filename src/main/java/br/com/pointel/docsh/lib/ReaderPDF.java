package br.com.pointel.docsh.lib;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReaderPDF {

    private final File file;

    public ReaderPDF(File file) {
        this.file = file;
    }

    public String load() throws Exception {
        try (var doc = PDDocument.load(file)) {
            var stripper = new PDFTextStripper();
            stripper.setStartPage(1);
            stripper.setEndPage(doc.getNumberOfPages());
            return stripper.getText(doc);
        }
    }
}
