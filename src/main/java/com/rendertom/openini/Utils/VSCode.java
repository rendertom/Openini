package com.rendertom.openini.Utils;

import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VSCode {
    public static void open(@NotNull String path) throws IOException, InterruptedException {
        open(Collections.singletonList(path));
    }

    public static void open(@NotNull List<String> paths) throws IOException, InterruptedException {
        List<String> command = getCommand();
        command.addAll(paths);
        runCommand(command);
    }

    @NotNull
    private static List<String> getCommand() {
        if (SystemInfo.isMac) {
            return new ArrayList<>(List.of("bash", "-l", "code"));
        } else if (SystemInfo.isWindows) {
            return new ArrayList<>(List.of("cmd.exe", "/C", "code"));
        } else {
            throw new RuntimeException(SystemInfo.getOsNameAndVersion() + " is not supported");
        }
    }

    private static void runCommand(@NotNull List<String> command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.redirectErrorStream(true);
        System.out.println("Command: " + builder.command());

        Process process = builder.start();
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Process exited with code: " + exitCode);
    }
}
