package com.rendertom.openini;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Process {
    public static void executeIfExists(String command, List<String> arguments) throws IOException, InterruptedException {
        if (isInstalled(command)) {
            int exitCode = run(command, arguments);
            Messages.showMessageDialog(String.valueOf(exitCode), "Message Title", Messages.getInformationIcon());
            System.out.println("Process exited with code: " + exitCode);
        } else {
            System.out.println(command + " is not installed");
        }
    }

    public static boolean isInstalled(@NotNull String command) throws IOException, InterruptedException {
        return runCommand(command + " --version") == 0;
    }

    ///

    private static @NotNull ArrayList<String> getArguments() {
        return new ArrayList<>(SystemInfo.isWindows ? List.of("cmd.exe", "/C") : List.of("/bin/bash", "--login", "-c"));
    }

    private static int run(String command, List<String> arguments) throws IOException, InterruptedException {
        return runCommand(command + " " + String.join(" ", arguments));
    }

    private static int runCommand(@NotNull String command) throws IOException, InterruptedException {
        ArrayList<String> arguments = getArguments();
        arguments.add(command);

        ProcessBuilder builder = new ProcessBuilder();
        builder.command(arguments);
        builder.redirectErrorStream(true);
        System.out.println("Command: " + builder.command());

        java.lang.Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        return process.waitFor();
    }
}
