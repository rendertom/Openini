package com.rendertom.openini.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class VSCode {
    private static final String BIN_NAME = "code";

    public static void open(@NotNull String argument) throws IOException, InterruptedException {
        open(List.of(argument));
    }

    public static void open(@NotNull List<String> arguments) throws IOException, InterruptedException {
        if (Process.isInstalled(BIN_NAME)) {
            Process.run(BIN_NAME, arguments);
        } else {
            System.out.println(BIN_NAME + " is not installed");
        }
    }
}
