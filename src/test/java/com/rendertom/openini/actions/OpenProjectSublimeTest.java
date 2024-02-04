package com.rendertom.openini.actions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenProjectSublimeTest {
  
  @Test
  void constructorTest() {
    OpenProjectSublime actual = new OpenProjectSublime();
    assertNotNull(actual, "OpenProjectSublime should be instantiated");
  }
}
