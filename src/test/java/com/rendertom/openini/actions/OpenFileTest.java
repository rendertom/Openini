package com.rendertom.openini.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
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

class OpenFileTest {
  private OpenFile openFile;

  @Mock
  private AnActionEvent mockAnActionEvent;

  @Mock
  private IAppConfig mockAppConfig;

  @Mock
  private FileEditorManager mockFileEditorManager;

  @Mock
  private Presentation mockPresentation;

  @Mock
  private Project mockProject;

  @Mock
  private VirtualFile mockVirtualFile;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    openFile = new OpenFile(mockAppConfig);
  }

  @Test
  void testConstructor() {
    String icon = "/icons/icon.png";
    when(mockAppConfig.getIcon()).thenReturn(icon);

    try (MockedStatic<IconLoader> mockedIconLoader = mockStatic(IconLoader.class)) {
      new OpenFile(mockAppConfig, "");
      mockedIconLoader.verify(() -> IconLoader.getIcon(icon, OpenFile.class), times(1));
    }
  }

  @Test
  void getActionUpdateThread_ReturnsBGT() {
    ActionUpdateThread result = openFile.getActionUpdateThread();
    assertEquals(ActionUpdateThread.BGT, result, "getActionUpdateThread should return ActionUpdateThread.BGT");
  }

  @Test
  void actionPerformedTest() {
    when(mockAnActionEvent.getRequiredData(CommonDataKeys.PROJECT)).thenReturn(mockProject);
    when(mockAnActionEvent.getRequiredData(CommonDataKeys.VIRTUAL_FILE_ARRAY)).thenReturn(new VirtualFile[]{mockVirtualFile});
    when(mockAppConfig.getEditorCommand()).thenReturn("editorCommand");
    when(mockFileEditorManager.getOpenFiles()).thenReturn(new VirtualFile[]{mockVirtualFile});

    // Mock static methods
    try (MockedStatic<FileEditorManager> mockedFEM = mockStatic(FileEditorManager.class);
         MockedStatic<Process> mockedProcess = mockStatic(Process.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class)) {

      mockedFEM.when(() -> FileEditorManager.getInstance(mockProject)).thenReturn(mockFileEditorManager);
      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenReturn(anyString());

      // Execute the method under test
      openFile.actionPerformed(mockAnActionEvent);

      // Verify the static methods were called
      mockedProcess.verify(() -> Process.executeIfExists(anyString(), anyList()), atLeastOnce());
      mockedStringEx.verify(() -> StringEx.quoteIfHasSpaces(anyString()), atLeastOnce());
    }
  }

  @Test
  void actionPerformedTest_WithException() {
    when(mockAnActionEvent.getRequiredData(CommonDataKeys.PROJECT)).thenReturn(mockProject);
    when(mockAnActionEvent.getRequiredData(CommonDataKeys.VIRTUAL_FILE_ARRAY)).thenReturn(new VirtualFile[]{mockVirtualFile});
    when(mockAppConfig.getEditorCommand()).thenReturn("editorCommand");
    when(mockFileEditorManager.getOpenFiles()).thenReturn(new VirtualFile[]{mockVirtualFile});

    // Mock static methods
    try (MockedStatic<FileEditorManager> mockedFEM = mockStatic(FileEditorManager.class);
         MockedStatic<Process> mockedProcess = mockStatic(Process.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class)) {

      mockedFEM.when(() -> FileEditorManager.getInstance(mockProject)).thenReturn(mockFileEditorManager);
      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenReturn("");
      mockedProcess.when(() -> Process.executeIfExists(anyString(), anyList())).thenThrow(IOException.class);

      openFile.actionPerformed(mockAnActionEvent);

      assertTrue(Thread.currentThread().isInterrupted(), "Thread should be interrupted after IOException");
    }
  }

  @Test
  void shouldDisablePresentationWhenProjectIsNull() {
    when(mockAnActionEvent.getData(CommonDataKeys.PROJECT)).thenReturn(null);
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);

    openFile.update(mockAnActionEvent);

    verify(mockPresentation).setEnabled(false);
  }

  @Test
  void shouldDisablePresentationWhenFilesAreNull() {
    when(mockAnActionEvent.getData(CommonDataKeys.PROJECT)).thenReturn(mockProject);
    when(mockAnActionEvent.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)).thenReturn(null);
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);

    openFile.update(mockAnActionEvent);

    verify(mockPresentation).setEnabled(false);
  }

  @Test
  void shouldDisablePresentationWhenNoFiles() {
    when(mockAnActionEvent.getData(CommonDataKeys.PROJECT)).thenReturn(mockProject);
    when(mockAnActionEvent.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)).thenReturn(new VirtualFile[]{});
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);

    openFile.update(mockAnActionEvent);

    verify(mockPresentation).setEnabled(false);
  }

  @Test
  void shouldSetNameOnPresentationWhenFilesPresent() {
    String appName = "appName";

    when(mockAnActionEvent.getData(CommonDataKeys.PROJECT)).thenReturn(mockProject);
    when(mockAnActionEvent.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)).thenReturn(new VirtualFile[]{mockVirtualFile});
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);
    when(mockAppConfig.getAppName()).thenReturn(appName);

    openFile.update(mockAnActionEvent);

    verify(mockPresentation).setText("Open File in " + appName);
  }
}

