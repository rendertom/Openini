package com.rendertom.openini.config;

import com.rendertom.openini.utils.OSProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppConfigSublimeTest {

  private final AppConfigSublime config = new AppConfigSublime();
  private MockedStatic<OSProvider> mockOSProvider;

  @BeforeEach
  void setUp() {
    mockOSProvider = Mockito.mockStatic(OSProvider.class);
  }

  @AfterEach
  void tearDown() {
    if (mockOSProvider != null) {
      mockOSProvider.close();
    }
  }

  @Test
  void constructorAndGetterTests() {
    assertEquals(AppConfigSublime.APP_NAME, config.getAppName());
    assertEquals(AppConfigSublime.ARGUMENT, config.getArgument());
    assertEquals(AppConfigSublime.ICON, config.getIcon());
    assertEquals(AppConfigSublime.URL, config.getURL());
  }

  @Test
  void getEditorCommandLinux() {
    mockOSProvider.when(OSProvider::isLinux).thenReturn(true);
    assertEquals("subl", config.getEditorCommand());
  }

  @Test
  void getEditorCommandMac() {
    mockOSProvider.when(OSProvider::isMac).thenReturn(true);
    assertEquals("/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl", config.getEditorCommand());
  }

  @Test
  void getEditorCommandWindows() {
    mockOSProvider.when(OSProvider::isWindows).thenReturn(true);
    assertEquals("C:\\Program Files\\Sublime Text\\subl.exe", config.getEditorCommand());
  }

  @Test
  void getEditorCommandUnsupportedOS() {
    mockOSProvider.when(OSProvider::isLinux).thenReturn(false);
    mockOSProvider.when(OSProvider::isMac).thenReturn(false);
    mockOSProvider.when(OSProvider::isWindows).thenReturn(false);

    Exception exception = Assertions.assertThrows(UnsupportedOperationException.class, config::getEditorCommand);
    assertEquals("Unsupported OS", exception.getMessage());
  }

}

