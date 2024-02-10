package com.rendertom.openini.utils;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditorManagerTest {

  @Mock
  private Project mockProject;

  @Mock
  private VirtualFile mockFile;

  @Mock
  private FileEditorManager mockFileEditorManager;

  @Mock
  private Editor mockEditor;

  @InjectMocks
  private EditorManager editorManager;

  @BeforeEach
  void setUp() {
    when(FileEditorManager.getInstance(mockProject)).thenReturn(mockFileEditorManager);
  }

  @Test
  void testGetPathWhenFileIsNotOpen() {
    when(mockFileEditorManager.getOpenFiles()).thenReturn(new VirtualFile[]{});

    IPath path = editorManager.getPath(mockFile);

    verify(mockFileEditorManager, never()).openTextEditor(any(OpenFileDescriptor.class), eq(false));
    assertTrue(path instanceof Path);
  }

  @Test
  void testGetPathWhenFileIsOpen() {
    when(mockFileEditorManager.getOpenFiles()).thenReturn(new VirtualFile[]{mockFile});
    when(mockFileEditorManager.openTextEditor(any(OpenFileDescriptor.class), eq(false))).thenReturn(mockEditor);

    IPath path = editorManager.getPath(mockFile);

    verify(mockFileEditorManager).openTextEditor(any(OpenFileDescriptor.class), eq(false));
    assertNotNull(path);
  }
}
