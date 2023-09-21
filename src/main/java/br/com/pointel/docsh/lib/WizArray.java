package br.com.pointel.docsh.lib;

import java.util.Objects;

public class WizArray {

    public static <T> boolean contains(T element, T[] onArray) {
        if (onArray == null) {
            return false;
        }
        for (var inside : onArray) {
            if (Objects.equals(inside, element)) {
                return true;
            }
        }
        return false;
    }

}
