package com.rendertom.openini.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class FileExTest {

  private VirtualFile mockFile1;
  private VirtualFile mockFile2;
  private AnActionEvent mockEvent;

  @BeforeEach
  public void setUp() {
    mockFile1 = Mockito.mock(VirtualFile.class);
    mockFile2 = Mockito.mock(VirtualFile.class);
    mockEvent = Mockito.mock(AnActionEvent.class);
  }

  @Test
  void testPrivateConstructor() throws NoSuchMethodException {
    Constructor<FileEx> constructor = FileEx.class.getDeclaredConstructor();
    Assertions.assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()), "Constructor is not private");

    constructor.setAccessible(true); // Make the constructor accessible

    InvocationTargetException exception = Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance, "Constructor invocation did not throw an IllegalStateException");

    Assertions.assertTrue(exception.getCause() instanceof IllegalStateException, "Thrown exception is not an IllegalStateException");
    Assertions.assertEquals("Utility class", exception.getCause().getMessage(), "Exception message does not match");
  }

  @Test
  void testExists_withNonNullExistingFile_returnsTrue() {
    Mockito.when(mockFile1.exists()).thenReturn(true);
    Assertions.assertTrue(FileEx.exists(mockFile1));
  }

  @Test
  void testExists_withNonNullNonExistingFile_returnsFalse() {
    Mockito.when(mockFile1.exists()).thenReturn(false);
    Assertions.assertFalse(FileEx.exists(mockFile1));
  }

  @Test
  void testExists_withNull_returnsFalse() {
    Assertions.assertFalse(FileEx.exists(null));
  }

  @Test
  void testGetPaths_withValidFiles_returnsPaths() {
    String path1 = "/path/to/file1";
    String path2 = "/path/to/file2";
    Mockito.when(mockFile1.exists()).thenReturn(true);
    Mockito.when(mockFile1.getPath()).thenReturn(path1);
    Mockito.when(mockFile2.exists()).thenReturn(true);
    Mockito.when(mockFile2.getPath()).thenReturn(path2);

    VirtualFile[] files = {mockFile1, mockFile2};
    Assertions.assertEquals(List.of(path1, path2), FileEx.getPaths(files));
  }

  @Test
  void testGetPaths_withNullFiles_returnsEmptyList() {
    Assertions.assertTrue(FileEx.getPaths(null).isEmpty());
  }

  @Test
  void testGetProjectFileDirectory_WithValidDirectory_ReturnsVirtualFile() {
    Mockito.when(mockEvent.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(mockFile1);
    Assertions.assertEquals(mockFile1, FileEx.getProjectFileDirectory(mockEvent));
  }

  @Test
  void testGetProjectFileDirectory_WithNoDirectory_ReturnsNull() {
    Mockito.when(mockEvent.getData(PlatformDataKeys.PROJECT_FILE_DIRECTORY)).thenReturn(null);
    Assertions.assertNull(FileEx.getProjectFileDirectory(mockEvent));
  }

  @Test
  void testGetVirtualFiles_ReturnsNonNullVirtualFilesArray() {
    VirtualFile[] expectedFiles = new VirtualFile[]{mockFile1, mockFile2};
    Mockito.when(mockEvent.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY)).thenReturn(expectedFiles);
    Assertions.assertArrayEquals(expectedFiles, FileEx.getVirtualFiles(mockEvent));
  }

  @Test
  void testGetVirtualFiles_ReturnsNullWhenNoFilesPresent() {
    Mockito.when(mockEvent.getData(PlatformDataKeys.VIRTUAL_FILE_ARRAY)).thenReturn(null);
    Assertions.assertNull(FileEx.getVirtualFiles(mockEvent));
  }
}
