package br.com.pointel.docsh.lib;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author emuvi
 */
public class WizFiles {

    public static String[] MSWORD_EXTENSIONS = new String[]{"doc", "docx"};
    
    public static boolean isMSWordFile(File file) {
        return isMSWordFile(file.getName());
    }
    
    public static boolean isMSWordFile(String fileName) {
        return WizArray.contains(FilenameUtils.getExtension(fileName).toLowerCase(), MSWORD_EXTENSIONS);
    }
    
    public static String[] MSEXCEL_EXTENSIONS = new String[]{"xls", "xlsx"};
    
    public static boolean isMSExcelFile(File file) {
        return isMSExcelFile(file.getName());
    }
    
    public static boolean isMSExcelFile(String fileName) {
        return WizArray.contains(FilenameUtils.getExtension(fileName).toLowerCase(), MSEXCEL_EXTENSIONS);
    }
    
    public static String[] MSPOWERPOINT_EXTENSIONS = new String[]{"ppt", "pptx"};
    
    public static boolean isMSPowerPointFile(File file) {
        return isMSPowerPointFile(file.getName());
    }
    
    public static boolean isMSPowerPointFile(String fileName) {
        return WizArray.contains(FilenameUtils.getExtension(fileName).toLowerCase(), MSPOWERPOINT_EXTENSIONS);
    }
    
}
