package com.rendertom.openini.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenURLSublimeTest {

  @Test
  void constructorTest() {
    OpenURLSublime actual = new OpenURLSublime();
    assertNotNull(actual, "OpenURLSublime should be instantiated");
  }
}

