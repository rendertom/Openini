package com.rendertom.openini.utils;

import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.vfs.VirtualFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PathTest {
  private final String filePath = "/path/to/file.xyz";

  @Mock
  private Caret mockCaret;

  @Mock
  private CaretModel mockCaretModel;

  @Mock
  private Editor mockEditor;

  @Mock
  private VirtualFile mockFile;


  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getPathWithoutCaretPosition() {
    when(mockFile.getPath()).thenReturn(filePath);

    String expected = "\"" + filePath + "\"";

    Path path = new Path(mockFile, null);
    String actual = path.getPath();

    assertEquals(expected, actual);
  }

  @Test
  void getPathWithCaretPosition() {
    int line = 10;
    int column = 20;

    when(mockFile.getPath()).thenReturn(filePath);
    when(mockEditor.getCaretModel()).thenReturn(mockCaretModel);
    when(mockCaretModel.getPrimaryCaret()).thenReturn(mockCaret);
    when(mockCaret.getLogicalPosition()).thenReturn(new LogicalPosition(line, column));

    String expected = String.format("\"%s:%d:%d\"", filePath, line + 1, column + 1);

    Path path = new Path(mockFile, mockEditor);
    String actual = path.getPathAndCaretPosition();

    assertEquals(expected, actual);
  }

  @Test
  void hasCaretPosition() {
    Path pathWithoutEditor = new Path(mockFile, null);
    Path pathWithEditor = new Path(mockFile, mockEditor);

    assertFalse(pathWithoutEditor.hasCaretPosition());
    assertTrue(pathWithEditor.hasCaretPosition());
  }

  @Test
  void isDirectory() {
    Path path = new Path(mockFile, null);

    when(mockFile.isDirectory()).thenReturn(false);
    assertFalse(path.isDirectory());

    when(mockFile.isDirectory()).thenReturn(true);
    assertTrue(path.isDirectory());
  }
}
