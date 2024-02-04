package com.rendertom.openini.config;

import org.jetbrains.annotations.NotNull;

public interface AppConfig {
  @NotNull String getAppName();

  @NotNull String getEditorCommand();

  @NotNull String getURL();
}
