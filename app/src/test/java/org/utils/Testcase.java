package org.utils;

public class Testcase {
  int number;
  String input, output;

  public Testcase(int number, String input, String output) {
    this.number = number;
    this.input = input;
    this.output = output;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

}
