package com.rendertom.openini.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppConfigVSCodeTest {

  @Test
  void constructorAndGetterTests() {
    AppConfigVSCode config = new AppConfigVSCode();
    assertEquals(AppConfigVSCode.APP_NAME, config.getAppName());
    assertEquals(AppConfigVSCode.ARGUMENT, config.getArgument());
    assertEquals(AppConfigVSCode.EDITOR_COMMAND, config.getEditorCommand());
    assertEquals(AppConfigVSCode.ICON, config.getIcon());
    assertEquals(AppConfigVSCode.URL, config.getURL());
  }
}
