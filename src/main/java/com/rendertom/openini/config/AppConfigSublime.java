package com.rendertom.openini.config;

import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class AppConfigSublime extends AppConfig {
    final String appName = "Sublime Text";
    final String URL = "https://www.sublimetext.com/";
    Map<String, String> editorCommand = new HashMap<>() {{
        put("linux", "subl");
        put("mac", "/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl");
        put("windows", "C:\\Program Files\\Sublime Text\\subl.exe");
    }};

    @Override
    public @NotNull String getAppName() {
        return appName;
    }

    @Override
    public @NotNull String getEditorCommand() {
        if (SystemInfo.isLinux) return editorCommand.get("linux");
        if (SystemInfo.isMac) return editorCommand.get("mac");
        if (SystemInfo.isWindows) return editorCommand.get("windows");

        throw new RuntimeException("Unsupported OS");
    }

    @Override
    public @NotNull String getURL() {
        return URL;
    }
}
