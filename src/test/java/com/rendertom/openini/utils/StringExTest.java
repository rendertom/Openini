package com.rendertom.openini.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class StringExTest {

  @Test
  void testPrivateConstructor() throws NoSuchMethodException {
    Constructor<StringEx> constructor = StringEx.class.getDeclaredConstructor();
    Assertions.assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()), "Constructor is not private");

    constructor.setAccessible(true); // Make the constructor accessible

    InvocationTargetException exception = Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance, "Constructor invocation did not throw an IllegalStateException");

    Assertions.assertTrue(exception.getCause() instanceof IllegalStateException, "Thrown exception is not an IllegalStateException");
    Assertions.assertEquals("Utility class", exception.getCause().getMessage(), "Exception message does not match");
  }

  @Test
  void testQuoteNonEmptyStringWithoutSpaces() {
    String input = "Hello";
    String expected = "\"Hello\"";
    String result = StringEx.quote(input);
    Assertions.assertEquals(expected, result, "The string should be quoted.");
  }

  @Test
  void testQuoteStringWithSpaces() {
    String input = "Hello World";
    String expected = "\"Hello World\"";
    String result = StringEx.quote(input);
    Assertions.assertEquals(expected, result, "The string with spaces should be quoted.");
  }

  @Test
  void testQuoteEmptyString() {
    String input = "";
    String expected = "";
    String result = StringEx.quote(input);
    Assertions.assertEquals(expected, result, "The empty string should return an empty string.");
  }

  @Test
  void testQuoteNull() {
    String input = null;
    String expected = "";
    String result = StringEx.quote(input);
    Assertions.assertEquals(expected, result, "Null input should return an empty string.");
  }

  @Test
  void testQuoteEachIfHasSpacesWithMixedStrings() {
    List<String> inputs = Arrays.asList("Hello", "Hello World", "", "OpenINI");
    List<String> expected = Arrays.asList("Hello", "\"Hello World\"", "", "OpenINI");
    List<String> results = StringEx.quoteEachIfHasSpaces(inputs);
    Assertions.assertEquals(expected, results, "The list should contain both quoted and unquoted strings appropriately.");
  }

  @Test
  void testQuoteEachIfHasSpacesWithAllSpaces() {
    List<String> inputs = Arrays.asList("Hello World", "Open INI");
    List<String> expected = Arrays.asList("\"Hello World\"", "\"Open INI\"");
    List<String> results = StringEx.quoteEachIfHasSpaces(inputs);
    Assertions.assertEquals(expected, results, "All strings with spaces should be quoted.");
  }

  @Test
  void testQuoteEachIfHasSpacesWithNoSpaces() {
    List<String> inputs = Arrays.asList("Hello", "World");
    List<String> expected = Arrays.asList("Hello", "World");
    List<String> results = StringEx.quoteEachIfHasSpaces(inputs);
    Assertions.assertEquals(expected, results, "Strings without spaces should remain unquoted.");
  }

  @Test
  void testQuoteEachIfHasSpacesWithEmptyList() {
    List<String> inputs = Collections.emptyList();
    List<String> expected = Collections.emptyList();
    List<String> results = StringEx.quoteEachIfHasSpaces(inputs);
    Assertions.assertEquals(expected, results, "An empty list should return an empty list.");
  }
}

