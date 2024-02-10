package com.rendertom.openini.utils;

public class ProcessResult {
  private final String command;
  private final int exitCode;
  private final String output;

  public ProcessResult(String command, int exitCode, String output) {
    this.command = command;
    this.exitCode = exitCode;
    this.output = output;
  }

  public String getCommand() {
    return command;
  }

  public int getExitCode() {
    return exitCode;
  }

  public String getOutput() {
    return output;
  }

  public boolean isFailure() {
    return !isSuccess();
  }

  public boolean isSuccess() {
    return exitCode == 0;
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
