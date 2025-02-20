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

  private String inputCleaning(String input) {
    return new StringBuilder(input).append("\n").toString();
  }

  public List<Token> tokenize(String input) {
    input = inputCleaning(input);

    List<Token> tokens = new ArrayList<>();
    TokenizerState state = new TokenizerState(StateType.START);
    StringBuilder value = new StringBuilder();

    int i = 0;

    while (i < input.length()) {
      char c = input.charAt(i);

      try {
        state = TokenizerState.transition(state, c);
      } catch (LexerException e) {
        // TODO: Auto-generated catch block
        e.printStackTrace();
      }

      if (state.isEndState()) {
        String tokenValue = value.toString().trim();

        switch (state.getType()) {
          case ARITHMETIC_OPERATOR_END:
            tokens.add(new Token(TokenType.ARITHMETIC_OPERATOR, tokenValue));
            break;
          case OPEN_PARENTHESIS_END:
            tokens.add(new Token(TokenType.OPEN_PARENTHESIS, tokenValue));
            break;
          case CLOSE_PARENTHESIS_END:
            tokens.add(new Token(TokenType.CLOSE_PARENTHESIS, tokenValue));
            break;
          case DIGIT_END:
            tokens.add(new Token(TokenType.NUMBER, tokenValue));
            break;
          case SYMBOL_END:
            tokens.add(new Token(TokenType.SYMBOL, tokenValue));
            break;
          case ALPHABETIC_END:
            if (KEYWORDS.containsKey(tokenValue)) {
              tokens.add(new Token(KEYWORDS.get(tokenValue), tokenValue));
            } else {
              tokens.add(new Token(TokenType.IDENTIFIER, tokenValue));
            }
            break;
          default:
            System.out.println("Unhandled Tokenizer End State: " + state.getType());
        }

        value = new StringBuilder();
      } else {
        value.append(c);
        i++;
      }
    }

    return tokens;
  }

}
