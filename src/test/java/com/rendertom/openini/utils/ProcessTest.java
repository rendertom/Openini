package com.rendertom.openini.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;

class ProcessTest {
  
  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<Process> constructor = Process.class.getDeclaredConstructor();
    constructor.setAccessible(true); // Make private constructor accessible

    InvocationTargetException exception = Assertions.assertThrows(
      InvocationTargetException.class,
      constructor::newInstance
    );

    Assertions.assertTrue(exception.getCause() instanceof IllegalStateException);
    Assertions.assertEquals("Utility class", exception.getCause().getMessage());
  }

  @Test
  void testExecuteIfExists_CommandSucceeds() throws IOException, InterruptedException {
    try (MockedStatic<Process> mockedProcess = Mockito.mockStatic(Process.class, Mockito.CALLS_REAL_METHODS);
         MockedStatic<Notifier> mockedNotifier = Mockito.mockStatic(Notifier.class)) {

      ProcessResult result1 = new ProcessResult("", 0, "");
      mockedProcess.when(() -> Process.runCommand(anyString())).thenReturn(result1);

      ProcessResult result2 = new ProcessResult("", 0, "");
      mockedProcess.when(() -> Process.run(anyString(), anyList())).thenReturn(result2);

      Process.executeIfExists("echo", "Hello World");
      Process.executeIfExists("echo", List.of("Hello World"));

      mockedNotifier.verifyNoInteractions();
    }
  }

  @Test
  void testExecuteIfExists_CommandFails_NotInstalled() throws IOException, InterruptedException {
    try (MockedStatic<Process> mockedProcess = Mockito.mockStatic(Process.class, Mockito.CALLS_REAL_METHODS);
         MockedStatic<Notifier> mockedNotifier = Mockito.mockStatic(Notifier.class)) {

      ProcessResult result = new ProcessResult("false --version", 1, "Command Not Found");
      mockedProcess.when(() -> Process.runCommand(anyString())).thenReturn(result);

      Process.executeIfExists("false", "argument");

      mockedNotifier.verify(() -> Notifier.error("Editor is not installed", result.toHTML()), times(1));
    }
  }

  @Test
  void testExecuteIfExists_CommandFails_WentWrong() throws IOException, InterruptedException {
    try (MockedStatic<Process> mockedProcess = Mockito.mockStatic(Process.class, Mockito.CALLS_REAL_METHODS);
         MockedStatic<Notifier> mockedNotifier = Mockito.mockStatic(Notifier.class)) {

      ProcessResult result1 = new ProcessResult("", 0, "");
      mockedProcess.when(() -> Process.runCommand(anyString())).thenReturn(result1);

      ProcessResult result2 = new ProcessResult("", 1, "");
      mockedProcess.when(() -> Process.run(anyString(), anyList())).thenReturn(result2);

      Process.executeIfExists("false", "argument");

      mockedNotifier.verify(() -> Notifier.error(eq("Something went wrong"), anyString()), atLeastOnce());
    }
  }
}

