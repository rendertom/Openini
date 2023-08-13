package com.rendertom.openini;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FileEx {
    public static boolean exists(@Nullable VirtualFile file) {
        return file != null && file.exists();
    }

    public static @NotNull String getPath(@Nullable VirtualFile file) {
        return exists(file) ? file.getPath() : "";
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

    public static @Nullable VirtualFile getProjectFileDirectory(@NotNull AnActionEvent event) {
        return event.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY);
    }

    public static @Nullable VirtualFile[] getVirtualFiles(@NotNull AnActionEvent event) {
        return event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
    }
}
