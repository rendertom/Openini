package com.rendertom.openini.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenProjectVSCodeTest {

  @Test
  void constructorTest() {
    OpenProjectVSCode actual = new OpenProjectVSCode();
    assertNotNull(actual, "OpenProjectVSCode should be instantiated");
  }
}
