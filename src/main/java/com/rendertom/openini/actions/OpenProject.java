package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.utils.FileEx;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.StringEx;
import com.rendertom.openini.config.AppConfig;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class OpenProject extends AnAction {
    protected final String EDITOR_COMMAND;

    OpenProject(@NotNull AppConfig config) {
        this.EDITOR_COMMAND = config.getEditorCommand();
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        try {
            VirtualFile file = FileEx.getProjectFileDirectory(event);
            if (FileEx.exists(file)) {
                Process.executeIfExists(StringEx.quoteIfHasSpaces(EDITOR_COMMAND), StringEx.quoteIfHasSpaces(file.getPath()));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        VirtualFile file = FileEx.getProjectFileDirectory(event);
        boolean enabled = FileEx.exists(file);
        event.getPresentation().setEnabled(enabled);
    }
}
