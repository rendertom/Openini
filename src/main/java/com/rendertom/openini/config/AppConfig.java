package com.rendertom.openini.config;

import org.jetbrains.annotations.NotNull;

public abstract class AppConfig {
    public abstract @NotNull String getAppName();

    public abstract @NotNull String getEditorCommand();

    public abstract @NotNull String getURL();
}
