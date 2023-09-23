package br.com.pointel.docsh.lib;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

/**
 *
 * @author emuvi
 */
public class WizProps {

    private static final Properties PROPERTIES = new Properties();

    static {
        tryLoad();
    }

    public static void tryLoad() {
        try {
            load();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void load() throws Exception {
        load(WizBase.APP_NAME);
    }

    public static void load(String name) throws Exception {
        File file = new File(name + ".ini");
        if (file.exists()) {
            try (FileReader input = new FileReader(file)) {
                PROPERTIES.load(input);
            }
        }
    }

    public static Boolean get(String key, Boolean defaultValue) {
        return Boolean.valueOf(get(key, defaultValue.toString()));
    }

    public static void set(String key, Boolean value) {
        set(key, value.toString());
        trySave();
    }

    public static Integer get(String key, Integer defaultValue) {
        return Integer.valueOf(get(key, defaultValue.toString()));
    }

    public static void set(String key, Integer value) {
        set(key, value.toString());
        trySave();
    }

    public static Double get(String key, Double defaultValue) {
        return Double.valueOf(get(key, defaultValue.toString()));
    }

    public static void set(String key, Double value) {
        set(key, value.toString());
        trySave();
    }

    public static String get(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static void set(String key, String value) {
        PROPERTIES.setProperty(key, value);
        trySave();
    }

    public static void trySave() {
        try {
            save();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void save() throws Exception {
        save(WizBase.APP_NAME);
    }

    public static void save(String name) throws Exception {
        File file = new File(name + ".ini");
        try (FileWriter output = new FileWriter(file)) {
            PROPERTIES.store(output, name + " properties");
        }
    }

}
