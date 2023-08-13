package com.rendertom.openini;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

public abstract class OpenURL extends AnAction {
    protected final String URL;

    OpenURL(AppConfig config) {
        this.URL = config.getURL();
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        BrowserUtil.open(URL);
    }
}
