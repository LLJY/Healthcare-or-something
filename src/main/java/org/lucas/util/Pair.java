package org.lucas.util;

/**
 * Utility class to represent a pair of objects
 * @param <T> First class
 * @param <S> Second class
 */
public class Pair<T, S> {
    public final T first;
    public final S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }
}
