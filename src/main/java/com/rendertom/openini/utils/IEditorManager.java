package com.rendertom.openini.utils;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public interface IEditorManager {
  @NotNull IPath getPath(@NotNull VirtualFile file);
}
