package com.rendertom.openini;

import com.intellij.openapi.util.SystemInfo;

public class AppConfigSublime extends AppConfig {
    final String appName = "Sublime Text";
    final String editorCommandLINUX = "subl";
    final String editorCommandMAC = "\"/Applications/Sublime Text.app/Contents/SharedSupport/bin/subl\"";
    final String editorCommandWIN = "\"C:\\Program Files\\Sublime Text\\subl.exe\"";
    final String URL = "https://www.sublimetext.com/";

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public String getEditorCommand() {
        if (SystemInfo.isLinux) {
            return editorCommandLINUX;
        } else if (SystemInfo.isMac) {
            return editorCommandMAC;
        } else if (SystemInfo.isWindows) {
            return editorCommandWIN;
        } else {
            throw new RuntimeException("Unsupported OS");
        }
    }

    @Override
    public String getURL() {
        return URL;
    }
}
