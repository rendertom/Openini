package com.rendertom.openini.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenFileVSCodeTest {

  @Test
  void constructorTest() {
    OpenFileVSCode actual = new OpenFileVSCode();
    assertNotNull(actual, "OpenFileVSCode should be instantiated");
  }
}