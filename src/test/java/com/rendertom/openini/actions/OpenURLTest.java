package com.rendertom.openini.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rendertom.openini.config.AppConfigVSCode;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class OpenURLTest {

  @Test
  void testActionPerformed() {
    String testUrl = "https://example.com";
    AppConfigVSCode mockConfig = Mockito.mock(AppConfigVSCode.class);
    Mockito.when(mockConfig.getURL()).thenReturn(testUrl);

    OpenURL openURL = new OpenURL(mockConfig) {
      // Since OpenURL is abstract, provide an anonymous implementation if necessary
    };

    try (MockedStatic<BrowserUtil> mockedBrowserUtil = Mockito.mockStatic(BrowserUtil.class)) {
      openURL.actionPerformed(Mockito.mock(AnActionEvent.class));
      mockedBrowserUtil.verify(() -> BrowserUtil.open(testUrl), Mockito.times(1));
    }
  }
}
