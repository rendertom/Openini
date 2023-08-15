package com.rendertom.openini.config;

import org.jetbrains.annotations.NotNull;

public class AppConfigVSCode extends AppConfig {
    final String appName = "VSCode";
    final String editorCommand = "code";
    final String URL = "https://code.visualstudio.com/";

    @Override
    public @NotNull String getAppName() {
        return appName;
    }

    @Override
    public @NotNull String getEditorCommand() {
        return editorCommand;
    }

    @Override
    public @NotNull String getURL() {
        return URL;
    }
}
