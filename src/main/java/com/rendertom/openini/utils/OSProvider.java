package com.rendertom.openini.utils;

import com.intellij.openapi.util.SystemInfo;

public class OSProvider {
  
  private OSProvider() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean isLinux() {
    return SystemInfo.isLinux;
  }

  public static boolean isMac() {
    return SystemInfo.isMac;
  }

  public static boolean isWindows() {
    return SystemInfo.isWindows;
  }
}
