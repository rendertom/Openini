package com.rendertom.openini.config;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppConfigVSCodeTest {

  private final AppConfigVSCode appConfig = new AppConfigVSCode();

  @Test
  void getAppName_ReturnsCorrectName() {
    @NotNull String expectedAppName = "VSCode";
    Assertions.assertEquals(expectedAppName, appConfig.getAppName(), "getAppName should return the correct app name.");
  }

  @Test
  void getEditorCommand_ReturnsCorrectCommand() {
    @NotNull String expectedEditorCommand = "code";
    Assertions.assertEquals(expectedEditorCommand, appConfig.getEditorCommand(), "getEditorCommand should return the correct editor command.");
  }

  @Test
  void getURL_ReturnsCorrectURL() {
    @NotNull String expectedURL = "https://code.visualstudio.com/";
    Assertions.assertEquals(expectedURL, appConfig.getURL(), "getURL should return the correct URL.");
  }
}

