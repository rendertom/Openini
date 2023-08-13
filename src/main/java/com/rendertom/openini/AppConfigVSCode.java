package com.rendertom.openini;

public class AppConfigVSCode extends AppConfig {
    final String appName = "VSCode";
    final String editorCommand = "code";
    final String URL = "https://code.visualstudio.com/";

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public String getEditorCommand() {
        return editorCommand;
    }

    @Override
    public String getURL() {
        return URL;
    }
}
