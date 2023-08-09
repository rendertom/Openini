package com.rendertom.openini.Utils;

import com.intellij.openapi.util.SystemInfo;

import java.io.IOException;

public class VSCode {
    public static void open(String path) throws IOException {
        runCommand(getCommand(path));
    }

    private static String[] getCommand(String path) {
        if (SystemInfo.isMac) {
            return new String[]{"code", path};
        } else if (SystemInfo.isWindows) {
            return new String[]{"cmd.exe", "/C", "code", path};
        } else {
            throw new RuntimeException(SystemInfo.getOsNameAndVersion() + " is not supported");
        }
    }

    private static void runCommand(String[] command) throws IOException {
        ProcessBuilder process = new ProcessBuilder();
        process.command(command);
        process.start();
    }
}
