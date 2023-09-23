package br.com.pointel.docsh.lib;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author emuvi
 */
public class WizChars {
    
    public static String makeParameterName(String ofTitle) {
        return ofTitle.replace(" ", "_").toUpperCase();
    }
    
    public static String mountGrid(List<Pair<String, String>> grid) {
        var result = new StringBuilder();
        var max = 0;
        for (var line: grid) {
            max = Math.max(max, line.getLeft().length());
        }
        for (var line: grid) {
            result.append(StringUtils.rightPad(line.getLeft(), max, '.'));
            result.append("...: ");
            result.append(line.getRight());
            result.append("\n");
        }
        return result.toString();
    }
    
}
