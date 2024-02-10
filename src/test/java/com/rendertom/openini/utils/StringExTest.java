package com.rendertom.openini.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class StringExTest {

  @Test
  void testPrivateConstructor() throws Exception {
    Constructor<StringEx> constructor = StringEx.class.getDeclaredConstructor();
    constructor.setAccessible(true); // Make private constructor accessible

    InvocationTargetException exception = Assertions.assertThrows(
      InvocationTargetException.class,
      constructor::newInstance
    );

    Assertions.assertTrue(exception.getCause() instanceof IllegalStateException);
    Assertions.assertEquals("Utility class", exception.getCause().getMessage());
  }

  @Test
  void testQuoteWithNull() {
    String expected = "";
    String actual = StringEx.quote(null);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void testQuoteWithEmptyString() {
    String expected = "";
    String actual = StringEx.quote("");

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void testQuoteWithNonEmptyString() {
    String testString = "Hello";
    String expected = "\"" + testString + "\"";
    String actual = StringEx.quote(testString);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void testQuoteIfHasSpacesWithSpaces() {
    String testString = "Hello World";
    String expected = "\"" + testString + "\"";
    String actual = StringEx.quoteIfHasSpaces(testString);

    Assertions.assertEquals(expected, actual);
  }

  @Test
  void testQuoteIfHasSpacesWithoutSpaces() {
    String testString = "Hello";
    String actual = StringEx.quoteIfHasSpaces(testString);
    Assertions.assertEquals(testString, actual);
  }
}
