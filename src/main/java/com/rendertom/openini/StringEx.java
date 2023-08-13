package com.rendertom.openini;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StringEx {
    public static @NotNull String quote(String string) {
        return string == null || string.isEmpty()
            ? ""
            : "\"" + string + "\"";
    }

    public static @NotNull List<String> quoteEach(@NotNull List<String> strings) {
        return strings.stream().map(StringEx::quote).toList();
    }

    public static @NotNull List<String> quoteEachIfHasSpaces(@NotNull List<String> strings) {
        return strings.stream().map(StringEx::quoteIfHasSpaces).toList();
    }

    public static String quoteIfHasSpaces(String string) {
        return string.contains(" ")
            ? quote(string)
            : string;
    }
}
