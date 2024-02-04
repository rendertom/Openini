package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.config.AppConfig;
import com.rendertom.openini.utils.FileEx;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.StringEx;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class OpenFile extends AnAction {
  protected final String APP_NAME;
  protected final String EDITOR_COMMAND;

  OpenFile(@NotNull AppConfig config) {
    this.APP_NAME = config.getAppName();
    this.EDITOR_COMMAND = config.getEditorCommand();
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    try {
      VirtualFile[] files = FileEx.getVirtualFiles(event);
      if (files != null) {
        List<String> paths = FileEx.getPaths(files);
        Process.executeIfExists(StringEx.quoteIfHasSpaces(EDITOR_COMMAND), StringEx.quoteEachIfHasSpaces(paths));
      }
    } catch (IOException | InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public void update(@NotNull AnActionEvent event) {
    String name = getActionName(event);
    if (name != null) {
      event.getPresentation().setText(name);
    } else {
      event.getPresentation().setEnabled(false);
    }
  }

  ///

  private @Nullable String getActionName(@NotNull AnActionEvent event) {
    VirtualFile[] files = FileEx.getVirtualFiles(event);
    if (files == null || files.length == 0) return null;

    long numFiles = Arrays.stream(files).filter(item -> !item.isDirectory()).count();
    long numFolders = Arrays.stream(files).filter(VirtualFile::isDirectory).count();

    String description;
    String fileType = numFiles == 1 ? "File" : (numFiles + " Files");
    String folderType = numFolders == 1 ? "Folder" : (numFolders + " Folders");

    if (numFiles == 0) {
      description = folderType;
    } else if (numFolders == 0) {
      description = fileType;
    } else {
      description = fileType + " and " + folderType;
    }

    return "Open " + description + " in " + APP_NAME;
  }
}
