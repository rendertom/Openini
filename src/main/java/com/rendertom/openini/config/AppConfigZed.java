package com.rendertom.openini.config;

import com.rendertom.openini.utils.OSProvider;
import org.jetbrains.annotations.NotNull;

public class AppConfigZed extends AppConfig {
  static final String APP_NAME = "Zed";
  static final String ARGUMENT = "";
  static final String EDITOR_COMMAND = "zed";
  static final String ICON = "icons/zed.svg";

  public AppConfigZed() {
    super(APP_NAME, ARGUMENT, EDITOR_COMMAND, ICON);
  }

  @Override
  public @NotNull String getEditorCommand() {
    if (OSProvider.isLinux() || OSProvider.isMac()) {
      return EDITOR_COMMAND;
    } else {
      throw new UnsupportedOperationException("Unsupported OS");
    }
  }
}
