package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.config.AppConfig;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.StringEx;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class OpenProject extends AnAction {
  protected final AppConfig config;

  OpenProject(@NotNull AppConfig config) {
    this.config = config;
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    try {
      VirtualFile file = event.getRequiredData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY);
      if (file.exists()) {
        Process.executeIfExists(StringEx.quoteIfHasSpaces(config.getEditorCommand()), StringEx.quoteIfHasSpaces(file.getPath()));
      }
    } catch (IOException | InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public void update(@NotNull AnActionEvent event) {
    VirtualFile file = event.getData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY);
    boolean enabled = file != null && file.exists();
    event.getPresentation().setEnabled(enabled);
  }
}
