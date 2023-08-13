package com.rendertom.openini;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Utils {
    public static @NotNull List<String> quoteEach(@NotNull List<String> strings) {
        return strings.stream().map(string -> "\"" + string + "\"").toList();
    }
}
