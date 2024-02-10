package com.rendertom.openini.utils;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Path implements IPath {
  private final @Nullable Editor editor;
  private final @NotNull VirtualFile file;

  public Path(@NotNull VirtualFile file, @Nullable Editor editor) {
    this.editor = editor;
    this.file = file;
  }

  @Override
  public @NotNull String getPath() {
    return quote(file.getPath());
  }

  @Override
  public @NotNull String getPathAndCaretPosition() {
    if (!hasCaretPosition()) return getPath();

    LogicalPosition position = editor.getCaretModel().getPrimaryCaret().getLogicalPosition();
    String path = String.format("%s:%d:%d", file.getPath(), position.line + 1, position.column + 1);
    return quote(path);
  }

  @Override
  public boolean hasCaretPosition() {
    return editor != null;
  }

  @Override
  public boolean isDirectory() {
    return file.isDirectory();
  }

  ///

  private String quote(String string) {
    return "\"" + string + "\"";
  }
}
