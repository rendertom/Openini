package com.rendertom.openini.utils;

import com.intellij.openapi.util.SystemInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OSProviderTest {

  @Test
  void testPrivateConstructor() throws NoSuchMethodException {
    Constructor<OSProvider> constructor = OSProvider.class.getDeclaredConstructor();
    Assertions.assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()), "Constructor is not private");

    constructor.setAccessible(true); // Make the constructor accessible

    InvocationTargetException exception = Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance, "Constructor invocation did not throw an IllegalStateException");

    Assertions.assertTrue(exception.getCause() instanceof IllegalStateException, "Thrown exception is not an IllegalStateException");
    Assertions.assertEquals("Utility class", exception.getCause().getMessage(), "Exception message does not match");
  }

  @Test
  void testIsLinux() {
    assertEquals(SystemInfo.isLinux, OSProvider.isLinux());
  }

  @Test
  void testIsMac() {
    assertEquals(SystemInfo.isMac, OSProvider.isMac());
  }

  @Test
  void testIsWindows() {
    assertEquals(SystemInfo.isWindows, OSProvider.isWindows());
  }
}

