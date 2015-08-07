package com.recipes.util;

/**
 * Fail early.
 */
public class Parameters {
	public static void checkNotNull(Object o) {
		if (o == null) throw new IllegalArgumentException("Object can't be null!");
	}
}
