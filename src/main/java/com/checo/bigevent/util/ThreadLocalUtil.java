package com.checo.bigevent.util;

public class ThreadLocalUtil {
    private static final ThreadLocal<Object> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Object value) {
        THREAD_LOCAL.set(value);
    }

    public static <T> T get() {
        return (T) THREAD_LOCAL.get();
    }

    /** 防止内存泄露，即时清除 */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}