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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OpenFileTest {

  @Mock
  private AppConfig mockAppConfig;
  @Mock
  private AnActionEvent mockAnActionEvent;
  @Mock
  private VirtualFile mockVirtualFile;
  @Mock
  private Presentation mockPresentation;

  private OpenFile openFile;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    when(mockAppConfig.getAppName()).thenReturn("TestApp");
    when(mockAppConfig.getEditorCommand()).thenReturn("test-editor");
    when(mockAnActionEvent.getPresentation()).thenReturn(mockPresentation);

    openFile = new OpenFile(mockAppConfig) {
      // Implementation not necessary for testing abstract methods
    };
  }

  @Test
  void getActionUpdateThread_ReturnsBGT() {
    ActionUpdateThread result = openFile.getActionUpdateThread();
    Assertions.assertEquals(ActionUpdateThread.BGT, result, "getActionUpdateThread should return ActionUpdateThread.BGT");
  }

  @Test
  void actionPerformed_WithFiles() throws IOException, InterruptedException {
    VirtualFile[] files = new VirtualFile[]{mockVirtualFile};

    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class);
         MockedStatic<Process> mockedProcess = mockStatic(Process.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class)) {

      mockedFileEx.when(() -> FileEx.getVirtualFiles(any(AnActionEvent.class))).thenReturn(files);
      mockedFileEx.when(() -> FileEx.getPaths(any(VirtualFile[].class))).thenReturn(List.of("path/to/file"));
      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenAnswer(invocation -> "\"" + invocation.getArgument(0) + "\"");
      mockedStringEx.when(() -> StringEx.quoteEachIfHasSpaces(anyList())).thenReturn(List.of("\"path/to/file\""));

      // No exception should be thrown
      openFile.actionPerformed(mockAnActionEvent);

      mockedProcess.verify(() -> Process.executeIfExists(anyString(), anyList()), times(1));
    }
  }

  @Test
  void actionPerformed_WithException() throws IOException, InterruptedException {
    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class);
         MockedStatic<Process> mockedProcess = mockStatic(Process.class);
         MockedStatic<StringEx> mockedStringEx = mockStatic(StringEx.class)) {

      mockedFileEx.when(() -> FileEx.getVirtualFiles(any(AnActionEvent.class))).thenReturn(new VirtualFile[]{mockVirtualFile});
      mockedFileEx.when(() -> FileEx.getPaths(any(VirtualFile[].class))).thenReturn(List.of("/path/to/file"));
      mockedStringEx.when(() -> StringEx.quoteIfHasSpaces(anyString())).thenReturn("\"editorCommand\"");
      mockedStringEx.when(() -> StringEx.quoteEachIfHasSpaces(anyList())).thenReturn(List.of("\"/path/to/file\""));

      // Configure Process.executeIfExists to throw an IOException
      mockedProcess.when(() -> Process.executeIfExists(anyString(), anyList())).thenThrow(IOException.class);

      openFile.actionPerformed(mockAnActionEvent);
      
      assertTrue(Thread.currentThread().isInterrupted(), "Thread should be interrupted after IOException");
    }
  }

  @Test
  void update_WithFiles() {
    VirtualFile[] files = {mockVirtualFile};

    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class)) {
      mockedFileEx.when(() -> FileEx.getVirtualFiles(mockAnActionEvent)).thenReturn(files);
      when(mockVirtualFile.isDirectory()).thenReturn(false);

      openFile.update(mockAnActionEvent);

      verify(mockPresentation).setText("Open File in TestApp");
      verify(mockPresentation, never()).setEnabled(false);
    }
  }

  @Test
  void update_WithFolder() {
    VirtualFile[] files = {mockVirtualFile};

    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class)) {
      mockedFileEx.when(() -> FileEx.getVirtualFiles(mockAnActionEvent)).thenReturn(files);
      when(mockVirtualFile.isDirectory()).thenReturn(true);

      openFile.update(mockAnActionEvent);

      verify(mockPresentation).setText("Open Folder in TestApp");
      verify(mockPresentation, never()).setEnabled(false);
    }
  }

  @Test
  void update_WithMultipleFilesAndFolders() {
    VirtualFile mockFolder = mock(VirtualFile.class);
    VirtualFile[] filesAndFolders = {mockVirtualFile, mockFolder};

    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class)) {
      mockedFileEx.when(() -> FileEx.getVirtualFiles(mockAnActionEvent)).thenReturn(filesAndFolders);
      when(mockVirtualFile.isDirectory()).thenReturn(false);
      when(mockFolder.isDirectory()).thenReturn(true);

      openFile.update(mockAnActionEvent);

      verify(mockPresentation).setText("Open File and Folder in TestApp");
    }
  }

  @Test
  void update_WithNoFiles() {
    try (MockedStatic<FileEx> mockedFileEx = mockStatic(FileEx.class)) {
      mockedFileEx.when(() -> FileEx.getVirtualFiles(mockAnActionEvent)).thenReturn(null);

      openFile.update(mockAnActionEvent);

      verify(mockPresentation).setEnabled(false);
    }
  }
}

