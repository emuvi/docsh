package br.com.pointel.docsh;

import org.apache.commons.lang3.ArrayUtils;

import br.com.pointel.docsh.cli.Cli;
import br.com.pointel.docsh.gui.Gui;

public class Docsh {

    public static void main(String[] args) throws Exception {
        var cli = args.length > 0 && !ArrayUtils.contains(args, "--gui");
        var gui = args.length == 0 || ArrayUtils.contains(args, "--gui");
        if (cli) {
            Cli.start(args);
        } else if (gui) {
            Gui.start(args);
        }
    }

}
