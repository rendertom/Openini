package com.rendertom.openini.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileEx {
  private FileEx() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean exists(@Nullable VirtualFile file) {
    return file != null && file.exists();
  }

  public static @NotNull List<String> getPaths(@Nullable VirtualFile[] files) {
    List<String> paths = new ArrayList<>();

    if (files == null) return paths;

    for (VirtualFile file : files) {
      if (exists(file)) {
        paths.add(file.getPath());
      }
    }

    return paths;
  }

  public static @NotNull List<String> getPathsWithCaretPosition(VirtualFile[] files, Project project) {
    List<VirtualFile> openFiles = List.of(FileEditorManager.getInstance(project).getOpenFiles());

    return Arrays.stream(files).map(file -> openFiles.contains(file)
      ? getPathWithCaretPosition(file, project)
      : file.getPath()
    ).toList();
  }

  public static @Nullable VirtualFile getProjectFileDirectory(@NotNull AnActionEvent event) {
    return event.getData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY);
  }

  public static @Nullable VirtualFile[] getVirtualFiles(@NotNull AnActionEvent event) {
    return event.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
  }

  ///

  private static String getPathWithCaretPosition(VirtualFile file, Project project) {
    String path = file.getPath();

    Editor editor = FileEditorManager.getInstance(project).openTextEditor(new OpenFileDescriptor(project, file), false);
    if (editor == null) return path;

    LogicalPosition position = editor.getCaretModel().getPrimaryCaret().getLogicalPosition();
    return String.format("%s:%d:%d", path, position.line + 1, position.column + 1);
  }

}
