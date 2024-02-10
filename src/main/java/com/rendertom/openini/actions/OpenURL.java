package com.rendertom.openini.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rendertom.openini.config.AppConfig;
import org.jetbrains.annotations.NotNull;

public abstract class OpenURL extends AnAction {
  protected final AppConfig config;

  OpenURL(@NotNull AppConfig config) {
    this.config = config;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    BrowserUtil.open(config.getURL());
  }
}
