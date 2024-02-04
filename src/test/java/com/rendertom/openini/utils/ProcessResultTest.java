package com.rendertom.openini.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProcessResultTest {

  @Test
  void testSuccessState() {
    ProcessResult successResult = new ProcessResult("echo Hello", 0, "Hello");
    Assertions.assertTrue(successResult.isSuccess(), "The process should be successful with exit code 0.");
    Assertions.assertFalse(successResult.isFailure(), "The process should not be marked as failure with exit code 0.");
  }

  @Test
  void testFailureState() {
    ProcessResult failureResult = new ProcessResult("exit 1", 1, "");
    Assertions.assertFalse(failureResult.isSuccess(), "The process should not be successful with a non-zero exit code.");
    Assertions.assertTrue(failureResult.isFailure(), "The process should be marked as failure with a non-zero exit code.");
  }

  @Test
  void testCommandOutput() {
    String command = "echo Hello";
    String output = "Hello";
    ProcessResult result = new ProcessResult(command, 0, output);
    Assertions.assertEquals(command, result.getCommand(), "The command should match the input command.");
    Assertions.assertEquals(output, result.getOutput(), "The output should match the executed command's output.");
  }

  @Test
  void testToString() {
    ProcessResult result = new ProcessResult("echo Hello", 0, "Hello");
    String expectedString = "Command: echo Hello\nExit code: 0\nOutput: Hello";
    Assertions.assertEquals(expectedString, result.toString(), "The toString output should match the expected format.");
  }

  @Test
  void testToHTML() {
    ProcessResult result = new ProcessResult("echo Hello", 0, "Hello");
    String expectedHtml = "<strong>Command: </strong>echo Hello<br/>" +
      "<strong>Exit code: </strong>0<br>" +
      "<strong>Output: </strong>Hello";
    Assertions.assertEquals(expectedHtml, result.toHTML(), "The toHTML output should match the expected HTML format.");
  }
}

