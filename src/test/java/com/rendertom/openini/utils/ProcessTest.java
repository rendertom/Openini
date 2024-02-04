package com.rendertom.openini.utils;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class ProcessTest {

  @Test
  void testExecuteIfExists_CommandSucceeds() throws IOException, InterruptedException {
    try (MockedStatic<Process> mockedProcess = Mockito.mockStatic(Process.class, Mockito.CALLS_REAL_METHODS);
         MockedStatic<Notifier> mockedNotifier = Mockito.mockStatic(Notifier.class)) {

      ProcessResult mockSuccessResult = new ProcessResult("echo --version", 0, "Echo Version Output");
      mockedProcess.when(() -> Process.runCommand(any(String.class))).thenReturn(mockSuccessResult);

      ProcessResult mockResult = new ProcessResult("echo Hello World", 0, "Hello World");
      mockedProcess.when(() -> Process.run(any(String.class), any(List.class))).thenReturn(mockResult);

      Process.executeIfExists("echo", "Hello World");
      Process.executeIfExists("echo", List.of("Hello World"));

      mockedNotifier.verifyNoInteractions();
    }
  }

  @Test
  void testExecuteIfExists_CommandFails() throws IOException, InterruptedException {
    try (MockedStatic<Process> mockedProcess = Mockito.mockStatic(Process.class, Mockito.CALLS_REAL_METHODS);
         MockedStatic<Notifier> mockedNotifier = Mockito.mockStatic(Notifier.class)) {

      ProcessResult mockFailureResult = new ProcessResult("false --version", 1, "Command Not Found");
      mockedProcess.when(() -> Process.runCommand(any(String.class))).thenReturn(mockFailureResult);

      Process.executeIfExists("false", List.of());

      mockedNotifier.verify(() -> Notifier.error("Editor is not installed", mockFailureResult.toHTML()), times(1));
    }
  }
}

