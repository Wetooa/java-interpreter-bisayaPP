package org.lexer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.exception.LexerException;
import org.lexer.Token.TokenType;
import org.lexer.TokenizerState.StateType;

public class Lexer {

  public static final Map<String, TokenType> KEYWORDS = new HashMap<>() {
    {
      put("SUGOD", TokenType.START_BLOCK);
      put("KATAPUSAN", TokenType.END_BLOCK);

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

  private void addToken(List<Token> tokens, TokenType type, StringBuilder value) {
    String tokenValue = value.toString().trim();

    tokens.add(new Token(type, tokenValue));
    value.setLength(0);
  }

  public List<Token> tokenize(String input) {
    input = new StringBuilder(input).append("\n").toString();

    ArrayList<Token> tokens = new ArrayList<>();
    StringBuilder value = new StringBuilder();
    TokenizerState state = new TokenizerState(StateType.START);

    for (int i = 0; i < input.length();) {
      char c = input.charAt(i);
      value.append(c);

      System.out.println(c);
      System.out.println(state);

      // FIX: Handle better later
      try {
        state = TokenizerState.transition(state, c);
      } catch (LexerException e) {
        e.printStackTrace();
      }

      switch (state.getType()) {
        case ARITHMETIC_OPERATOR_END:
          addToken(tokens, TokenType.ARITHMETIC_OPERATOR, value);
          break;

        case OPEN_PARENTHESIS_END:
          addToken(tokens, TokenType.OPEN_PARENTHESIS, value);

        case CLOSE_PARENTHESIS_END:
          addToken(tokens, TokenType.CLOSE_PARENTHESIS, value);

        case DIGIT_END:
          addToken(tokens, TokenType.NUMBER, value);
          break;

        case ALPHABETIC_END:
          if (KEYWORDS.containsKey(value.toString().trim())) {
            addToken(tokens, KEYWORDS.get(value.toString().trim()), value);
          } else {
            addToken(tokens, TokenType.IDENTIFIER, value);
          }
          break;

        default:
          ++i;
          break;
      }
    }

    return tokens;
  }

}
