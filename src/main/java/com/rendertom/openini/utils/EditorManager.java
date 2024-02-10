package com.rendertom.openini.utils;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EditorManager implements IEditorManager {
  private final Project project;
  private FileEditorManager fileEditorManager;

  public EditorManager(@NotNull Project project) {
    this.project = project;
  }

  public @NotNull IPath getPath(@NotNull VirtualFile file) {
    if (!contains(file)) return new Path(file, null);
    return new Path(file, openTextEditor(file));
  }

  ///

  private boolean contains(@NotNull VirtualFile file) {
    return List.of(getFileEditorManager().getOpenFiles()).contains(file);
  }

  private FileEditorManager getFileEditorManager() {
    if (fileEditorManager == null) {
      fileEditorManager = FileEditorManager.getInstance(project);
    }
    return fileEditorManager;
  }

  private @Nullable Editor openTextEditor(@NotNull VirtualFile file) {
    OpenFileDescriptor descriptor = new OpenFileDescriptor(project, file);
    return getFileEditorManager().openTextEditor(descriptor, false);
  }
}
