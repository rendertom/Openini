package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.config.AppConfig;
import com.rendertom.openini.utils.FileEx;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.StringEx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OpenProjectTest {

  @Mock
  private AppConfig mockConfig;
  @Mock
  private AnActionEvent mockEvent;
  @Mock
  private VirtualFile mockFile;
  @Mock
  private Presentation mockPresentation;

  private OpenProject openProject;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(mockConfig.getEditorCommand()).thenReturn("editorCommand");
    when(mockEvent.getPresentation()).thenReturn(mockPresentation);
    when(mockFile.getPath()).thenReturn("filePath");

    openProject = new OpenProject(mockConfig) {
      // Implement abstract methods if necessary for testing
    };
  }

  @Test
  void getActionUpdateThread_ReturnsBGT() {
    ActionUpdateThread result = openProject.getActionUpdateThread();
    Assertions.assertEquals(ActionUpdateThread.BGT, result, "getActionUpdateThread should return ActionUpdateThread.BGT");
  }

  @Test
  void actionPerformed_FileExists() throws IOException, InterruptedException {
    // Mock the static methods
    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class);
         MockedStatic<Process> mockedProcess = mockStatic(Process.class)) {

      mockedFileEx.when(() -> FileEx.getProjectFileDirectory(mockEvent)).thenReturn(mockFile);
      mockedFileEx.when(() -> FileEx.exists(mockFile)).thenReturn(true);
      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenAnswer(invocation -> "\"" + invocation.getArgument(0) + "\"");

      openProject.actionPerformed(mockEvent);

      // Verify the behavior
      mockedProcess.verify(() -> Process.executeIfExists(anyString(), anyString()), atLeast(1));
      mockedStringEx.verify(() -> StringEx.quoteIfHasSpaces("editorCommand"), atLeast(1));
      mockedStringEx.verify(() -> StringEx.quoteIfHasSpaces("filePath"), atLeast(1));
    }
  }

  @Test
  void actionPerformed_WithException() throws IOException, InterruptedException {
    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class);
         MockedStatic<Process> mockedProcess = mockStatic(Process.class)) {

      mockedFileEx.when(() -> FileEx.getProjectFileDirectory(mockEvent)).thenReturn(mockFile);
      mockedFileEx.when(() -> FileEx.exists(mockFile)).thenReturn(true);
      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenAnswer(invocation -> "\"" + invocation.getArgument(0) + "\"");

      mockedProcess.when(() -> Process.executeIfExists(anyString(), anyString())).thenThrow(IOException.class);

      openProject.actionPerformed(mockEvent);

      assertTrue(Thread.currentThread().isInterrupted(), "Thread should be interrupted after IOException");
    }
  }

  @Test
  void update_FileExists() {
    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class)) {
      mockedFileEx.when(() -> FileEx.getProjectFileDirectory(mockEvent)).thenReturn(mockFile);
      mockedFileEx.when(() -> FileEx.exists(mockFile)).thenReturn(true);

      openProject.update(mockEvent);

      verify(mockPresentation).setEnabled(true);
    }
  }

  @Test
  void update_FileDoesNotExist() {
    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class)) {
      mockedFileEx.when(() -> FileEx.getProjectFileDirectory(mockEvent)).thenReturn(null);

      openProject.update(mockEvent);

      verify(mockPresentation).setEnabled(false);
    }
  }
}

