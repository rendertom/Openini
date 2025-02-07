package com.rendertom.openini.config;

import com.rendertom.openini.utils.OSProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppConfigZedTest {

  private final AppConfigZed config = new AppConfigZed();
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
    assertEquals(AppConfigZed.APP_NAME, config.getAppName());
    assertEquals(AppConfigZed.ARGUMENT, config.getArgument());
    assertEquals(AppConfigZed.ICON, config.getIcon());
  }

  @Test
  void getEditorCommandLinux() {
    mockOSProvider.when(OSProvider::isLinux).thenReturn(true);
    assertEquals("zed", config.getEditorCommand());
  }

  @Test
  void getEditorCommandMac() {
    mockOSProvider.when(OSProvider::isMac).thenReturn(true);
    assertEquals("zed", config.getEditorCommand());
  }

  @Test
  void getEditorCommandWindows() {
    mockOSProvider.when(OSProvider::isWindows).thenReturn(true);
    assertThrows(UnsupportedOperationException.class, config::getEditorCommand);
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

