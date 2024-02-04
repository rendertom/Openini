package com.rendertom.openini.utils;

public class ProcessResult {
  private final String COMMAND;
  private final int EXIT_CODE;
  private final String OUTPUT;

  public ProcessResult(String command, int exitCode, String output) {
    this.COMMAND = command;
    this.EXIT_CODE = exitCode;
    this.OUTPUT = output;
  }

  public String getCommand() {
    return COMMAND;
  }

  public int getExitCode() {
    return EXIT_CODE;
  }

  public String getOutput() {
    return OUTPUT;
  }

  public boolean isFailure() {
    return !isSuccess();
  }

  public boolean isSuccess() {
    return EXIT_CODE == 0;
  }

  public String toHTML() {
    return "<strong>Command: </strong>" + getCommand() + "<br/>" +
      "<strong>Exit code: </strong>" + getExitCode() + "<br>" +
      "<strong>Output: </strong>" + getOutput();
  }

  public String toString() {
    return "Command: " + getCommand() + "\n" +
      "Exit code: " + getExitCode() + "\n" +
      "Output: " + getOutput();
  }
}
