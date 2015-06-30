package com.recipes.util;

/**
 * Created by Michal Radtke on 2015-06-30.
 */
public class Parameters {
    public static void checkNotNull(Object o) {
        if (o == null) throw new IllegalArgumentException("Object can't be null!");
    }
}
