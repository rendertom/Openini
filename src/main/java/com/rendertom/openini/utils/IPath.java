package com.rendertom.openini.utils;

public interface IPath {
  String getPath();

  String getPathAndCaretPosition();

  boolean hasCaretPosition();

  boolean isDirectory();
}
