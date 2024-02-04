package com.rendertom.openini.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenFileSublimeTest {

  @Test
  void constructorTest() {
    OpenFileSublime actual = new OpenFileSublime();
    assertNotNull(actual, "OpenFileSublime should be instantiated");
  }
}
