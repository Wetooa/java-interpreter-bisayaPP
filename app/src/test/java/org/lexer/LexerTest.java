package org.lexer;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.lexer.Token.TokenType;

public class LexerTest {

  @Test
  public void testTokenize() {

    Lexer lexer = new Lexer();
    List<Token> tokens = lexer.tokenize("123 MUGNA <= <> > 23\n what_ever NUMERO OO O");

    System.out.println(tokens);

    assert tokens.get(0).getType() == TokenType.NUMBER : "Invalid token type!";
    assert tokens.get(1).getType() == TokenType.VARIABLE_DECLARATION : "Invalid token type!";
    assert tokens.get(2).getType() == TokenType.ARITHMETIC_OPERATOR : "Invalid token type!";
    assert tokens.get(3).getType() == TokenType.ARITHMETIC_OPERATOR : "Invalid token type!";
    assert tokens.get(4).getType() == TokenType.ARITHMETIC_OPERATOR : "Invalid token type!";
    assert tokens.get(5).getType() == TokenType.NUMBER : "Invalid token type!";
    assert tokens.get(6).getType() == TokenType.IDENTIFIER : "Invalid token type!";
    assert tokens.get(7).getType() == TokenType.DATATYPE : "Invalid token type!";
    assert tokens.get(8).getType() == TokenType.BOOLEAN_VALUE : "Invalid token type!";
    assert tokens.get(9).getType() == TokenType.LOGICAL_OPERATOR : "Invalid token type!";

  }

}
