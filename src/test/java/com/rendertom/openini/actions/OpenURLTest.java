package com.rendertom.openini.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rendertom.openini.config.IAppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OpenURLTest {
  private final String url = "https://example.com";

  @Mock
  private IAppConfig mockAppConfig;

  @Mock
  private AnActionEvent mockAnActionEvent;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(mockAppConfig.getURL()).thenReturn(url);
  }

  @Test
  void testOpenURLConstructor() {
    OpenURL openURL = new OpenURL(mockAppConfig, url);

    assertEquals(url, openURL.config.getURL(), "Config should be correctly set in OpenURL");
  }

  @Test
  void testActionPerformed() {
    OpenURL openURL = new OpenURL(mockAppConfig);

    try (MockedStatic<BrowserUtil> mockedBrowser = mockStatic(BrowserUtil.class)) {
      openURL.actionPerformed(mockAnActionEvent);
      mockedBrowser.verify(() -> BrowserUtil.open(url), times(1));
    }
  }
}
