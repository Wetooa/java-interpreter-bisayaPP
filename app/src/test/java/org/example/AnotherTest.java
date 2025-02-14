package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnotherTest {

  @Test
  void appHasAGreeting() {
    Another another = new Another();
    assertNotNull(another.getGreeting(), "app should have a greeting");
  }

}
