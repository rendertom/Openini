package com.rendertom.openini;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

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
            List<String> paths = List.of(FileEx.getPath(file));
            Process.executeIfExists(EDITOR_COMMAND, Utils.quoteEach(paths));
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
