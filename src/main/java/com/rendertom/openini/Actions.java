package com.rendertom.openini;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.Utils.File;
import com.rendertom.openini.Utils.VSCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;

public class Actions {
    private static @NotNull String getActionName(@NotNull AnActionEvent event) {
        VirtualFile[] files = File.getVirtualFiles(event);
        if (files == null || files.length == 0) return "";

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

        return "Open " + description + " in VSCode";
    }

    public static class Get extends AnAction{
        @Override
        public void actionPerformed(@NotNull AnActionEvent event) {
            VSCode.get();
        }
    }

    public static class OpenFile extends AnAction {
        @Override
        public @NotNull ActionUpdateThread getActionUpdateThread() {
            return ActionUpdateThread.BGT;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent event) {
            try {
                VSCode.open(File.getPaths(File.getVirtualFiles(event)));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void update(@NotNull AnActionEvent event) {
            String name = getActionName(event);
            if (!name.isEmpty()) {
                event.getPresentation().setText(name);
            } else {
                event.getPresentation().setEnabled(false);
            }
        }
    }

    public static class OpenProject extends AnAction {
        @Override
        public @NotNull ActionUpdateThread getActionUpdateThread() {
            return ActionUpdateThread.BGT;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent event) {
            try {
                VSCode.open(File.getPath(File.getProjectFileDirectory(event)));
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void update(@NotNull AnActionEvent event) {
            event.getPresentation().setEnabled(File.exists(File.getProjectFileDirectory(event)));
        }
    }
}
