package com.rendertom.openini.config;

import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AppConfigSublime implements AppConfig {
  static final String APP_NAME = "Sublime Text";
  static final Map<String, String> EDITOR_COMMAND = new HashMap<>();
  static final String URL = "https://www.sublimetext.com/";

  // Static initializer block to populate the editorCommand map
  static {
    EDITOR_COMMAND.put("linux", "subl");
    EDITOR_COMMAND.put("mac", "/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl");
    EDITOR_COMMAND.put("windows", "C:\\Program Files\\Sublime Text\\subl.exe");
  }

  @Override
  public @NotNull String getAppName() {
    return APP_NAME;
  }

  @Override
  public @NotNull String getEditorCommand() {
    if (SystemInfo.isLinux) return EDITOR_COMMAND.get("linux");
    if (SystemInfo.isMac) return EDITOR_COMMAND.get("mac");
    if (SystemInfo.isWindows) return EDITOR_COMMAND.get("windows");

    throw new RuntimeException("Unsupported OS");
  }

  @Override
  public @NotNull String getURL() {
    return URL;
  }
}
