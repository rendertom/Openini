package com.rendertom.openini.config;

import org.jetbrains.annotations.NotNull;

public interface IAppConfig {
  @NotNull String getAppName();

  @NotNull String getArgument();

  @NotNull String getEditorCommand();
  
  @NotNull String getIcon();

  @NotNull String getURL();
}
