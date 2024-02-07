package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.config.AppConfig;
import com.rendertom.openini.utils.FileEx;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.StringEx;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class OpenFile extends AnAction {
  protected final AppConfig config;

  OpenFile(@NotNull AppConfig config) {
    this.config = config;
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    VirtualFile[] files = FileEx.getVirtualFiles(event);

    Project project = event.getRequiredData(CommonDataKeys.PROJECT);
    List<String> paths = FileEx.getPathsWithCaretPosition(files, project);
    List<String> arguments = StringEx.quoteEachIfHasSpaces(paths);
    if (StringEx.containsLineAndColumnNumber(arguments)) {
      arguments.add(0, config.getArgument());
    }

    try {
      Process.executeIfExists(StringEx.quoteIfHasSpaces(config.getEditorCommand()), arguments);
    } catch (IOException | InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public void update(@NotNull AnActionEvent event) {
    VirtualFile[] files = FileEx.getVirtualFiles(event);
    if (files == null || files.length == 0) {
      event.getPresentation().setEnabled(false);
    } else {
      event.getPresentation().setText(getActionName(files));
    }
  }

  ///

  private @NotNull String getActionName(@NotNull VirtualFile[] files) {
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

    return "Open " + description + " in " + config.getAppName();
  }
}
