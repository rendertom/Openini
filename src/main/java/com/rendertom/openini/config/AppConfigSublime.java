package com.rendertom.openini.config;

import com.rendertom.openini.utils.OSProvider;
import org.jetbrains.annotations.NotNull;

public class AppConfigSublime extends AppConfig {
  static final String APP_NAME = "Sublime Text";
  static final String ARGUMENT = "";
  static final String EDITOR_COMMAND = "";
  static final String ICON = "icons/sublime.svg";
  static final String URL = "https://www.sublimetext.com/";

  public AppConfigSublime() {
    super(APP_NAME, ARGUMENT, EDITOR_COMMAND, ICON, URL);
  }

  @Override
  public @NotNull String getEditorCommand() {
    if (OSProvider.isLinux()) return "subl";
    if (OSProvider.isMac()) return "/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl";
    if (OSProvider.isWindows()) return "C:\\Program Files\\Sublime Text\\subl.exe";

    throw new UnsupportedOperationException("Unsupported OS");
  }

}
