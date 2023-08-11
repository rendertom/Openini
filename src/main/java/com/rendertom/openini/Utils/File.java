package com.rendertom.openini.Utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class File {
    @NotNull
    public static String getPath(@Nullable VirtualFile file) {
        return exists(file) ? file.getPath() : "";
    }

    @NotNull
    public static List<String> getPaths(@Nullable VirtualFile[] files) {
        List<String> paths = new ArrayList<>();

        if (files == null) return paths;

        for (VirtualFile file : files) {
            if (exists(file)) {
                paths.add(file.getPath());
            }
        }

        return paths;
    }

    public static boolean exists(@Nullable VirtualFile file) {
        return file != null && file.exists();
    }

    public static boolean exists(@Nullable VirtualFile[] files) {
        return files != null && Arrays.stream(files).allMatch(File::exists);
    }

    @Nullable
    public static VirtualFile getProjectFileDirectory(@NotNull AnActionEvent event) {
        return event.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY);
    }

    @Nullable
    public static VirtualFile[] getVirtualFiles(@NotNull AnActionEvent event) {
        return event.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY);
    }
}
