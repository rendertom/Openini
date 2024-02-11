package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.config.IAppConfig;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.StringEx;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OpenProject extends AnAction {
  private final IAppConfig config;

  public OpenProject(@NotNull IAppConfig config) {
    this.config = config;
  }

  public OpenProject(@NotNull IAppConfig config, String text) {
    super(text, text, IconLoader.getIcon(config.getIcon(), OpenProject.class));
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
