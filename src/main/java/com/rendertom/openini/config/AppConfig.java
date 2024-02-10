package com.rendertom.openini.config;

import org.jetbrains.annotations.NotNull;

public abstract class AppConfig implements IAppConfig {
  private final String appName;
  private final String argument;
  private final String editorCommand;
  private final String url;

  protected AppConfig(@NotNull String appName, @NotNull String argument, @NotNull String editorCommand, @NotNull String url) {
    this.appName = appName;
    this.argument = argument;
    this.editorCommand = editorCommand;
    this.url = url;
  }

  public @NotNull String getAppName() {
    return appName;
  }

  public @NotNull String getArgument() {
    return argument;
  }

  public @NotNull String getEditorCommand() {
    return editorCommand;
  }

  public @NotNull String getURL() {
    return url;
  }
}
