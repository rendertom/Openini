package com.rendertom.openini.utils;

import com.intellij.openapi.vfs.VirtualFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

class ActionNameManagerTest {
  private final String appName = "AppName";

  @Mock
  private VirtualFile mockFile;

  @Mock
  private VirtualFile mockFolder;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSingleFile() {
    when(mockFile.isDirectory()).thenReturn(false);

    VirtualFile[] files = new VirtualFile[]{mockFile};
    ActionNameManager manager = new ActionNameManager(appName, files);

    Assertions.assertEquals("Open File in " + appName, manager.getName());
  }

  @Test
  void testMultipleFiles() {
    when(mockFile.isDirectory()).thenReturn(false);

    VirtualFile[] files = new VirtualFile[]{mockFile, mockFile};
    ActionNameManager manager = new ActionNameManager(appName, files);

    Assertions.assertEquals("Open 2 Files in " + appName, manager.getName());
  }

  @Test
  void testSingleFolder() {
    when(mockFolder.isDirectory()).thenReturn(true);

    VirtualFile[] files = new VirtualFile[]{mockFolder};
    ActionNameManager manager = new ActionNameManager(appName, files);

    Assertions.assertEquals("Open Folder in " + appName, manager.getName());
  }

  @Test
  void testMultipleFolders() {
    when(mockFile.isDirectory()).thenReturn(false);
    when(mockFolder.isDirectory()).thenReturn(true);

    VirtualFile[] files = new VirtualFile[]{mockFolder, mockFolder};
    ActionNameManager manager = new ActionNameManager(appName, files);

    Assertions.assertEquals("Open 2 Folders in " + appName, manager.getName());
  }

  @Test
  void testFilesAndFolders() {
    when(mockFile.isDirectory()).thenReturn(false);
    when(mockFolder.isDirectory()).thenReturn(true);

    VirtualFile[] files = new VirtualFile[]{mockFile, mockFolder};
    ActionNameManager manager = new ActionNameManager(appName, files);

    Assertions.assertEquals("Open File and Folder in " + appName, manager.getName());
  }
}

