package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformCoreDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.rendertom.openini.config.IAppConfig;
import com.rendertom.openini.utils.Process;
import com.rendertom.openini.utils.StringEx;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OpenProjectTest {
  private OpenProject openProject;

  @Mock
  private AnActionEvent mockAnActionEvent;

  @Mock
  private IAppConfig mockAppConfig;

  @Mock
  private Presentation mockPresentation;

  @Mock
  private VirtualFile mockVirtualFile;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    openProject = new OpenProject(mockAppConfig);
  }

  @Test
  void testConstructor() {
    String icon = "/icons/icon.png";
    when(mockAppConfig.getIcon()).thenReturn(icon);

    try (MockedStatic<IconLoader> mockedIconLoader = mockStatic(IconLoader.class)) {
      new OpenProject(mockAppConfig, "");
      mockedIconLoader.verify(() -> IconLoader.getIcon(icon, OpenProject.class), times(1));
    }
  }

  @Test
  void getActionUpdateThread_ReturnsBGT() {
    ActionUpdateThread result = openProject.getActionUpdateThread();
    assertEquals(ActionUpdateThread.BGT, result, "getActionUpdateThread should return ActionUpdateThread.BGT");
  }

  @Test
  void actionPerformed_ExecuteOnce() {
    when(mockAnActionEvent.getRequiredData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(mockVirtualFile);
    when(mockVirtualFile.exists()).thenReturn(true);
    when(mockVirtualFile.getPath()).thenReturn("main.js");
    when(mockAppConfig.getEditorCommand()).thenReturn("editorCommand");

    try (MockedStatic<Process> mockedProcess = mockStatic(Process.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class)) {

      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenReturn(anyString());

      openProject.actionPerformed(mockAnActionEvent);

      mockedStringEx.verify(() -> StringEx.quoteIfHasSpaces(anyString()), times(2));
      mockedProcess.verify(() -> Process.executeIfExists(anyString(), anyString()), times(1));
    }
  }

  @Test
  void actionPerformed_DoNotExecute() {
    when(mockAnActionEvent.getRequiredData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(mockVirtualFile);
    when(mockVirtualFile.exists()).thenReturn(false);

    try (MockedStatic<Process> mockedProcess = mockStatic(Process.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class)) {

      openProject.actionPerformed(mockAnActionEvent);

      mockedStringEx.verify(() -> StringEx.quoteIfHasSpaces(anyString()), times(0));
      mockedProcess.verify(() -> Process.executeIfExists(anyString(), anyString()), times(0));
    }
  }

  @Test
  void actionPerformed_WithException() {
    when(mockAnActionEvent.getRequiredData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(mockVirtualFile);
    when(mockVirtualFile.exists()).thenReturn(true);
    when(mockVirtualFile.getPath()).thenReturn("main.js");
    when(mockAppConfig.getEditorCommand()).thenReturn("editorCommand");

    try (MockedStatic<Process> mockedProcess = mockStatic(Process.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class)) {

      mockedProcess.when(() -> Process.executeIfExists(anyString(), anyString())).thenThrow(IOException.class);
      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenReturn(anyString());

      openProject.actionPerformed(mockAnActionEvent);

      assertTrue(Thread.currentThread().isInterrupted(), "Thread should be interrupted after IOException");
    }
  }

  @Test
  void shouldEnablePresentation() {
    when(mockAnActionEvent.getData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(mockVirtualFile);
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);
    when(mockVirtualFile.exists()).thenReturn(true);

    openProject.update(mockAnActionEvent);

    verify(mockPresentation).setEnabled(true);
  }

  @Test
  void shouldDisablePresentationWhenFileDoesNotExist() {
    when(mockAnActionEvent.getData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(mockVirtualFile);
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);
    when(mockVirtualFile.exists()).thenReturn(false);

    openProject.update(mockAnActionEvent);

    verify(mockPresentation).setEnabled(false);
  }

  @Test
  void shouldDisablePresentationWhenFileIsNull() {
    when(mockAnActionEvent.getData(PlatformCoreDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(null);
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);

    openProject.update(mockAnActionEvent);

    verify(mockPresentation).setEnabled(false);
  }
}
