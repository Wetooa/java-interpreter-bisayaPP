package org;

import java.lang.String;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class InterpreterTest {
  static final int NUMBER_OF_TEST_CASES = 1;

  private InputStream getFileFromResourceAsStream(String fileName) {

    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    assert inputStream != null : String.format("File '%s' not found!", fileName);

    return inputStream;
  }

  // @Test
  public ArrayList<Testcase> readTestCases() {
    ArrayList<Testcase> testcaseContent = new ArrayList<>();

    for (int i = 1; i <= NUMBER_OF_TEST_CASES; i++) {

      String testcaseName = String.format("%d.txt", i);

      InputStream inputIs = getFileFromResourceAsStream("input/" + testcaseName);
      InputStream outputIs = getFileFromResourceAsStream("output/" + testcaseName);

      StringBuilder inputContent = new StringBuilder();
      StringBuilder outputContent = new StringBuilder();

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputIs, StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          inputContent.append(line).append("\n");
        }
      } catch (Exception e) {
        assert false : String.format("Error reading file '%s'", testcaseName);
      }

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(outputIs, StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
          outputContent.append(line).append("\n");
        }
      } catch (Exception e) {
        assert false : String.format("Error reading file '%s'", testcaseName);
      }

      Testcase testcase = new Testcase(i, inputContent.toString(), outputContent.toString());
      testcaseContent.add(testcase);
    }

    return testcaseContent;
  }

  // @Test
  public void runTestCases() {
    ArrayList<Testcase> testcases = readTestCases();
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
