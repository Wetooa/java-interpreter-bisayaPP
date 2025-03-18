package org.lexer;

import java.util.HashMap;
import java.util.Map;

import org.lexer.Token.TokenType;

public class Keywords {
  public static final Map<String, TokenType> KEYWORDS = new HashMap<>() {
    {
      put("SUGOD", TokenType.START_BLOCK);
      put("KATAPUSAN", TokenType.END_BLOCK);
      put("KUNG", TokenType.CONDITIONAL_BLOCK);

      put("MUGNA", TokenType.VARIABLE_DECLARATION);

      put("DAWAT", TokenType.INPUT_STATEMENTS);
      put("IPAKITA", TokenType.OUTPUT_STATEMENTS);

      put("UG", TokenType.LOGICAL_OPERATOR);
      put("O", TokenType.LOGICAL_OPERATOR);
      put("DILI", TokenType.LOGICAL_OPERATOR);

      put("OO", TokenType.BOOLEAN_VALUE);

      put("ALANG SA", TokenType.FOR_LOOP);

      put("NUMERO", TokenType.DATATYPE);
      put("LETRA", TokenType.DATATYPE);
      put("TINUOD", TokenType.DATATYPE);
      put("TIPIK", TokenType.DATATYPE);
    }
  };
}
