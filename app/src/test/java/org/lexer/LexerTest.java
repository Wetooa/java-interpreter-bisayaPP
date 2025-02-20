package org.lexer;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.lexer.Token.TokenType;

public class LexerTest {

  @Test
  public void testTokenize() {

    Lexer lexer = new Lexer();
    List<Token> tokens = lexer.tokenize("123 123 32");

    System.out.println(tokens);

    assert tokens.size() == 3 : "Invalid token size!";

    assert tokens.get(0).getType() == TokenType.NUMBER : "Invalid token type!";
    assert tokens.get(0).getType() == TokenType.NUMBER : "Invalid token type!";
    assert tokens.get(0).getType() == TokenType.NUMBER : "Invalid token type!";

  }

}
