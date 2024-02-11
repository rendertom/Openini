package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.config.IAppConfig;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OpenFile extends AnAction {
  private final IAppConfig config;

  public OpenFile(@NotNull IAppConfig config) {
    this.config = config;
  }

  public OpenFile(@NotNull IAppConfig config, String text) {
    super(text, text, IconLoader.getIcon(config.getIcon(), OpenFile.class));
    this.config = config;
  }

  @Override
  public @NotNull ActionUpdateThread getActionUpdateThread() {
    return ActionUpdateThread.BGT;
  }

  @Override
  public void actionPerformed(@NotNull AnActionEvent event) {
    Project project = event.getRequiredData(CommonDataKeys.PROJECT);
    VirtualFile[] files = event.getRequiredData(CommonDataKeys.VIRTUAL_FILE_ARRAY);

    IEditorManager editorManager = new EditorManager(project);
    List<IPath> paths = Arrays.stream(files).map(editorManager::getPath).toList();
    List<String> arguments = paths.stream().map(IPath::getPathAndCaretPosition).collect(Collectors.toCollection(ArrayList::new));

    boolean hasCaret = paths.stream().anyMatch(IPath::hasCaretPosition);
    if (hasCaret) {
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
    Presentation presentation = event.getPresentation();
    Project project = event.getData(CommonDataKeys.PROJECT);
    VirtualFile[] files = event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);

    if (project == null || files == null || files.length == 0) {
      presentation.setEnabled(false);
      return;
    }

    ActionNameManager nameManager = new ActionNameManager(config.getAppName(), files);
    presentation.setText(nameManager.getName());
  }
}
