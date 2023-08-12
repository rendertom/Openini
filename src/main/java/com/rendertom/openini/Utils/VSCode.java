package com.rendertom.openini.Utils;

import com.intellij.ide.BrowserUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class VSCode {
    private static final String EDITOR_COMMAND = "code";
    private final static String URL = "https://code.visualstudio.com/";

    public static void open(@NotNull String path) throws IOException, InterruptedException {
        open(List.of(path));
    }

    public static void open(@NotNull List<String> paths) throws IOException, InterruptedException {
        if (Process.isInstalled(EDITOR_COMMAND)) {
            Process.run(EDITOR_COMMAND, quoteEach(paths));
        } else {
            System.out.println(EDITOR_COMMAND + " is not installed");
        }
    }

    public static void get() {
        BrowserUtil.open(URL);
    }

    private static List<String> quoteEach(List<String> strings) {
        return strings.stream().map(string -> "\"" + string + "\"").toList();
    }
}
