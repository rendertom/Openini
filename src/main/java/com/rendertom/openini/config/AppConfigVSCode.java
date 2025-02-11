package com.rendertom.openini.config;

public class AppConfigVSCode extends AppConfig {
  static final String APP_NAME = "VSCode";
  static final String ARGUMENT = "--goto";
  static final String EDITOR_COMMAND = "code";
  static final String ICON = "icons/vscode.svg";

  public AppConfigVSCode() {
    super(APP_NAME, ARGUMENT, EDITOR_COMMAND, ICON);
  }
}
