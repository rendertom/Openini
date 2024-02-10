package com.rendertom.openini.utils;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ActionNameManager implements IActionNameManager {
  private final String appName;
  private final VirtualFile[] files;

  public ActionNameManager(@NotNull String appName, @NotNull VirtualFile[] files) {
    this.appName = appName;
    this.files = files;
  }

  @Override
  public @NotNull String getName() {
    long numFiles = Arrays.stream(files).filter(item -> !item.isDirectory()).count();
    long numFolders = Arrays.stream(files).filter(VirtualFile::isDirectory).count();

    String description;
    String fileType = getType(numFiles, "File", "Files");
    String folderType = getType(numFolders, "Folder", "Folders");

    if (numFiles == 0) {
      description = folderType;
    } else if (numFolders == 0) {
      description = fileType;
    } else {
      description = fileType + " and " + folderType;
    }

    return "Open " + description + " in " + appName;
  }

  ///

  private @NotNull String getType(@NotNull Long count, @NotNull String singular, @NotNull String plural) {
    return count == 1 ? singular : (count + " " + plural);
  }
}
