package org.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TestcaseReader {
  static final int NUMBER_OF_TEST_CASES = 1;

  public InputStream getFileFromResourceAsStream(String fileName) {

    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(fileName);

    assert inputStream != null : String.format("File '%s' not found!", fileName);

    return inputStream;
  }

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
}
