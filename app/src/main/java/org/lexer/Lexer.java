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

  private TokenizerState state;
  private StringBuilder value;
  private List<Token> tokens;

  private void addToken(TokenType type) {
    String tokenValue = value.toString().trim();

    this.tokens.add(new Token(type, tokenValue));
    this.value.setLength(0);

    // FIX: Handle better later
    try {
      this.state = TokenizerState.transition(this.state, ' ');
    } catch (LexerException e) {
      e.printStackTrace();
    }
  }

  private String inputCleaning(String input) {
    return new StringBuilder(input).append("\n").toString();
  }

  public List<Token> tokenize(String input) {
    input = inputCleaning(input);

    this.tokens = new ArrayList<>();
    this.value = new StringBuilder();
    this.state = new TokenizerState(StateType.START);

    for (char c : input.toCharArray()) {
      this.value.append(c);

      // FIX: Handle better later
      try {
        this.state = TokenizerState.transition(this.state, c);
      } catch (LexerException e) {
        e.printStackTrace();
      }

      switch (state.getType()) {
        case ARITHMETIC_OPERATOR_END:
          addToken(TokenType.ARITHMETIC_OPERATOR);
          break;

        case OPEN_PARENTHESIS_END:
          addToken(TokenType.OPEN_PARENTHESIS);

        case CLOSE_PARENTHESIS_END:
          addToken(TokenType.CLOSE_PARENTHESIS);

        case DIGIT_END:
          addToken(TokenType.NUMBER);
          break;

        case ALPHABETIC_END:
          String identifierValue = value.toString().trim();

          if (KEYWORDS.containsKey(identifierValue)) {
            addToken(KEYWORDS.get(identifierValue));
          } else {
            addToken(TokenType.IDENTIFIER);
          }
          break;

        default:
          break;
      }
    }

    return tokens;
  }

}
