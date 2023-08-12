package com.rendertom.openini.Utils;

import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Process {
    private static int runCommand(@NotNull List<String> command) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.redirectErrorStream(true);
        System.out.println("Command: " + builder.command());

        java.lang.Process process = builder.start();
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Process exited with code: " + exitCode);

        return exitCode;
    }

    public static boolean isInstalled(@NotNull String binName) throws IOException, InterruptedException {
        return run(binName, List.of("--version")) == 0;
    }

    public static int run(String binName, List<String> arguments) throws IOException, InterruptedException {
        List<String> command = new ArrayList<>(
            SystemInfo.isWindows
                ? List.of("cmd.exe", "/C")
                : List.of("bash", "-l")
        );

        command.add(binName);
        command.addAll(arguments);

        return runCommand(command);
    }
}
