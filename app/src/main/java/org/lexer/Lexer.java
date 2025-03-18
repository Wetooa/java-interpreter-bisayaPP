package org.lexer;

import java.util.ArrayList;
import java.util.List;

import org.exception.LexerException;
import org.lexer.Token.TokenType;
import org.lexer.TokenizerState.StateType;

public class Lexer {

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

      System.out.println(c + " " + state);

      if (state.isEndState()) {
        String tokenValue = value.toString().trim();

        switch (state.getType()) {
          case COMMENT_END:
            System.out.println("Encountered a comment: " + tokenValue);
            break;
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
            if (Keywords.KEYWORDS.containsKey(tokenValue)) {
              tokens.add(new Token(Keywords.KEYWORDS.get(tokenValue), tokenValue));
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
