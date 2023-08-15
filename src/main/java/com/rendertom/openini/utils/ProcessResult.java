package com.rendertom.openini.utils;

public class ProcessResult {
  private final String COMMAND;
  private final int EXIT_CODE;
  private final String MESSAGE;

  ProcessResult(String command, int exitCode, String message) {
    this.COMMAND = command;
    this.EXIT_CODE = exitCode;
    this.MESSAGE = message;
  }

  public String getCommand() {
    return COMMAND;
  }

  public int getExitCode() {
    return EXIT_CODE;
  }

  public String getMessage() {
    return MESSAGE;
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
      "<strong>Message: </strong>" + getMessage();
  }

  public String toString() {
    return "Command: " + getCommand() + "\n" +
      "Exit code: " + getExitCode() + "\n" +
      "Message: " + getMessage();
  }
}
