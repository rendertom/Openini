package com.rendertom.openini.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StringEx {
  private StringEx() {
    throw new IllegalStateException("Utility class");
  }

  public static @NotNull String quote(@Nullable String string) {
    return string == null || string.isEmpty() ? "" : "\"" + string + "\"";
  }

  public static @NotNull String quoteIfHasSpaces(@NotNull String string) {
    return string.contains(" ")
      ? quote(string)
      : string;
  }
}
