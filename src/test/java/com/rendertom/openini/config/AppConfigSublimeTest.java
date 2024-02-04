package com.rendertom.openini.config;

import com.rendertom.openini.utils.OSProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class AppConfigSublimeTest {

  private final AppConfigSublime config = new AppConfigSublime();

  @Test
  void testGetAppName() {
    Assertions.assertEquals("Sublime Text", config.getAppName());
  }

  @Test
  void testGetEditorCommandLinux() {
    try (MockedStatic<OSProvider> mocked = Mockito.mockStatic(OSProvider.class)) {
      mocked.when(OSProvider::isLinux).thenReturn(true);

      Assertions.assertEquals("subl", config.getEditorCommand());
    }
  }

  @Test
  void testGetEditorCommandMac() {
    try (MockedStatic<OSProvider> mocked = Mockito.mockStatic(OSProvider.class)) {
      mocked.when(OSProvider::isMac).thenReturn(true);

      Assertions.assertEquals("/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl", config.getEditorCommand());
    }
  }

  @Test
  void testGetEditorCommandWindows() {
    try (MockedStatic<OSProvider> mocked = Mockito.mockStatic(OSProvider.class)) {
      mocked.when(OSProvider::isWindows).thenReturn(true);

      Assertions.assertEquals("C:\\Program Files\\Sublime Text\\subl.exe", config.getEditorCommand());
    }
  }

  @Test
  void testUnsupportedOS() {
    try (MockedStatic<OSProvider> mocked = Mockito.mockStatic(OSProvider.class)) {
      mocked.when(OSProvider::isLinux).thenReturn(false);
      mocked.when(OSProvider::isMac).thenReturn(false);
      mocked.when(OSProvider::isWindows).thenReturn(false);

      Exception exception = Assertions.assertThrows(RuntimeException.class, config::getEditorCommand);
      Assertions.assertTrue(exception.getMessage().contains("Unsupported OS"));
    }
  }

  @Test
  void testGetURL() {
    Assertions.assertEquals("https://www.sublimetext.com/", config.getURL());
  }
}

