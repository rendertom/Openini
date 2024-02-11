package com.rendertom.openini.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rendertom.openini.config.IAppConfig;
import org.jetbrains.annotations.NotNull;

public class OpenURL extends AnAction {
  protected final IAppConfig config;

  public OpenURL(@NotNull IAppConfig config) {
    this.config = config;
  }

  public OpenURL(@NotNull IAppConfig config, String text) {
    super(text, text, AllIcons.General.Web);
    this.config = config;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    BrowserUtil.open(config.getURL());
  }
}
