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
    public static class OpenFile extends AnAction {
        @Override
        public @NotNull ActionUpdateThread getActionUpdateThread() {
            return ActionUpdateThread.BGT;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent event) {
            try {
                VSCode.open(File.getVirtualFile(event).getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void update(@NotNull AnActionEvent event) {
            setEnabled(event.getPresentation(), File.exists(File.getVirtualFile(event)));
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
                VSCode.open(File.getProjectFileDirectory(event).getPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void update(@NotNull AnActionEvent event) {
            setEnabled(event.getPresentation(), File.exists(File.getProjectFileDirectory(event)));
        }
    }

    private static void setEnabled(Presentation presentation, Boolean enabled) {
        presentation.setEnabled(enabled);
    }
}
