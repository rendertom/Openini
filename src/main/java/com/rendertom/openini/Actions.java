package com.rendertom.openini;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.rendertom.openini.Utils.File;
import com.rendertom.openini.Utils.VSCode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Actions {
    private static void setEnabled(@NotNull Presentation presentation, @NotNull Boolean enabled) {
        presentation.setEnabled(enabled);
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
            setEnabled(event.getPresentation(), File.exists(File.getVirtualFiles(event)));
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
            setEnabled(event.getPresentation(), File.exists(File.getProjectFileDirectory(event)));
        }
    }
}
