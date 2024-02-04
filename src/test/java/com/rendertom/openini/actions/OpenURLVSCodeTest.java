package com.rendertom.openini.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenURLVSCodeTest {

  @Test
  void constructorTest() {
    OpenURLVSCode actual = new OpenURLVSCode();
    assertNotNull(actual, "OpenURLVSCode should be instantiated");
  }
}

