package com.rendertom.openini.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringEx {
  private StringEx() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean containsLineAndColumnNumber(List<String> strings) {
    Pattern pattern = Pattern.compile(":\\d+:\\d+");
    return strings.stream().anyMatch(s -> pattern.matcher(s).find());
  }

  public static @NotNull String quote(String string) {
    return string == null || string.isEmpty()
      ? ""
      : "\"" + string + "\"";
  }

  public static @NotNull List<String> quoteEachIfHasSpaces(@NotNull List<String> strings) {
    return strings.stream().map(StringEx::quoteIfHasSpaces).collect(Collectors.toCollection(ArrayList::new));
  }

  public static String quoteIfHasSpaces(String string) {
    return string.contains(" ")
      ? quote(string)
      : string;
  }
}
