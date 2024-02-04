package com.rendertom.openini.config;

import org.jetbrains.annotations.NotNull;

public class AppConfigVSCode implements AppConfig {
  static final String APP_NAME = "VSCode";
  static final String EDITOR_COMMAND = "code";
  static final String URL = "https://code.visualstudio.com/";

  @Override
  public @NotNull String getAppName() {
    return APP_NAME;
  }

  @Override
  public @NotNull String getEditorCommand() {
    return EDITOR_COMMAND;
  }

  @Override
  public @NotNull String getURL() {
    return URL;
  }
}
