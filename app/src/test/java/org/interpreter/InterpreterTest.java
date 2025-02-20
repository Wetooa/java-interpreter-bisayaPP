package org.interpreter;

import java.lang.String;
import java.util.ArrayList;

import org.utils.Testcase;
import org.utils.TestcaseReader;

public class InterpreterTest {

  // @Test
  public void runTestCases() {
    TestcaseReader reader = new TestcaseReader();
    ArrayList<Testcase> testcases = reader.readTestCases();
    Interpreter interpreter = new Interpreter();

    for (Testcase testcase : testcases) {
      String input = testcase.getInput();
      String output = interpreter.run(input);
      String expectedOutput = testcase.getOutput();

      System.out.println("Input: " + input);
      System.out.println("Output: " + output);
      System.out.println("Expected Output: " + expectedOutput);

      assert output.equals(expectedOutput) : String.format("Testcase failed: %s", input);
    }
  }

}
