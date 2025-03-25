package org.lexer;

import java.util.ArrayList;
import java.util.List;

import org.exception.LexerException;
import org.lexer.Token.TokenType;

public class Lexer {

  private String inputCleaning(String input) {
    return new StringBuilder(input).append("\n").toString();
  }

  public List<Token> tokenize(String input) {
    input = inputCleaning(input);

    List<Token> tokens = new ArrayList<>();
    TokenizerState state = new TokenizerState();
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

      if (state.isResetState()) {
        String tokenValue = value.toString().trim();

        switch (state.getType()) {
          case START:
            System.out.println("Encountered a start state: " + state.getType());
            break;

          case SKIPPABLE_END:
            System.out.println("Encountered a skippable character: " + tokenValue);
            break;

          case COMMENT_END:
            System.out.println("Encountered a comment: " + tokenValue);
            break;

          case ARITHMETIC_OPERATOR_END:
            tokens.add(new Token(TokenType.ARITHMETIC_OPERATOR, tokenValue));
            break;

          case ASSIGNMENT_OPERATOR_END:
            tokens.add(new Token(TokenType.ASSIGNMENT_OPERATOR, tokenValue));
            break;

          case OPEN_PARENTHESIS_END:
            tokens.add(new Token(TokenType.OPEN_PARENTHESIS, tokenValue));
            break;

          case CLOSE_PARENTHESIS_END:
            tokens.add(new Token(TokenType.CLOSE_PARENTHESIS, tokenValue));
            break;

          case SINGLE_QUOTE_END:
            tokens.add(new Token(TokenType.STRING, tokenValue));
            break;

          case DOUBLE_QUOTE_END:
            String content = tokenValue.substring(1, tokenValue.length() - 1);
            if (content.equals("OO") || content.equals("DILI")) {
              tokens.add(new Token(TokenType.BOOLEAN_VALUE, content));
            } else {
              tokens.add(new Token(TokenType.STRING, tokenValue));
            }
            break;

          case COMMA_END:
            tokens.add(new Token(TokenType.COMMA, tokenValue));
            break;

          case COLON_END:
            tokens.add(new Token(TokenType.COLON, tokenValue));
            break;

          case AMPERSAND_END:
            tokens.add(new Token(TokenType.LOGICAL_OPERATOR, tokenValue));
            break;

          case CARRIAGE_RETURN_END:
            tokens.add(new Token(TokenType.CARRIAGE_RETURN, tokenValue));
            break;

          case DIGIT_END:
            tokens.add(new Token(TokenType.NUMBER, tokenValue));
            break;

          case ALPHABETIC_END:
            if (Keywords.KEYWORDS.containsKey(tokenValue)) {
              tokens.add(new Token(Keywords.KEYWORDS.get(tokenValue), tokenValue));
            } else {
              tokens.add(new Token(TokenType.IDENTIFIER, tokenValue));
            }
            break;

          case NEWLINE_END:
            tokens.add(new Token(TokenType.NEWLINE, tokenValue));
            break;

          default:
            System.err.println("Error: Unhandled Tokenizer End State: " + state.getType());
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
