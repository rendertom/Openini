package com.rendertom.openini;

import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Process {
    public static void executeIfExists(String command, String argument) throws IOException, InterruptedException {
        executeIfExists(command, List.of(argument));
    }

    public static void executeIfExists(String command, List<String> arguments) throws IOException, InterruptedException {
        ProcessResult installResult = runCommand(command + " --version");
        if (installResult.isFailure()) {
            Notifier.error("Editor is not installed", installResult.toHTML());
            return;
        }

        ProcessResult result = run(command, arguments);
        if (result.isFailure()) {
            Notifier.error("Something went wrong", result.toString());
        }
    }

    ///

    private static @NotNull ArrayList<String> getArguments() {
        return new ArrayList<>(SystemInfo.isWindows ? List.of("cmd.exe", "/C") : List.of("/bin/bash", "--login", "-c"));
    }

    private static ProcessResult run(String command, List<String> arguments) throws IOException, InterruptedException {
        return runCommand(command + " " + String.join(" ", arguments));
    }

    private static ProcessResult runCommand(@NotNull String command) throws IOException, InterruptedException {
        ArrayList<String> arguments = getArguments();
        arguments.add(command);

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(arguments);
        builder.redirectErrorStream(true);
        System.out.println("Command: " + builder.command());

        java.lang.Process process = builder.start();
        int exitCode = process.waitFor();
        System.out.println("Exit code: " + exitCode);

        String message;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            message = reader.lines().collect(Collectors.joining("\n"));
            System.out.println("Message: " + message);
        }

        return new ProcessResult(builder.command().toString(), exitCode, message);
    }
}
