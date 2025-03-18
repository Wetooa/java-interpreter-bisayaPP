package org.lexer;

import java.util.List;

import org.exception.InvalidTokenizerStateException;
import org.exception.LexerException;
import org.exception.UnknownCharacterException;

public class TokenizerState {

  public static enum StateType {
    START,

    COMMENT,
    COMMENT_CONTENT,
    COMMENT_END,

    LESS_THAN_ARITHMETIC_OPERATOR,
    GREATER_THAN_ARITHMETIC_OPERATOR,

    ARITHMETIC_OPERATOR_END,

    OPEN_PARENTHESIS_END,
    CLOSE_PARENTHESIS_END,

    SYMBOL_END,

    ALPHABETIC,
    ALPHABETIC_END,

    DIGIT_WHOLE,
    DIGIT_AFTER_E,
    DIGIT_DECIMAL,
    DIGIT_END,
  }

  private static final List<StateType> END_STATES = List.of(StateType.COMMENT_END, StateType.ARITHMETIC_OPERATOR_END,
      StateType.DIGIT_END, StateType.ALPHABETIC_END, StateType.OPEN_PARENTHESIS_END, StateType.CLOSE_PARENTHESIS_END,
      StateType.SYMBOL_END);
  private static final List<Character> SKIPPABLE_SYMBOLS = List.of(' ', '\n', '\t');

  private static boolean isSkippable(char c) {
    return SKIPPABLE_SYMBOLS.contains(c);
  }

  public boolean isEndState() {
    return END_STATES.contains(this.type);
  }

  private final StateType type;

  public StateType getType() {
    return type;
  }

  public TokenizerState(StateType type) {
    this.type = type;
  }

  public static TokenizerState transition(TokenizerState state, char c) throws LexerException {

    switch (state.type) {

      case START:
        if (Character.isDigit(c)) {
          return new TokenizerState(StateType.DIGIT_WHOLE);
        } else if (Character.isAlphabetic(c)) {
          return new TokenizerState(StateType.ALPHABETIC);
        } else if (c == '-') {
          return new TokenizerState(StateType.COMMENT);
        } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
          return new TokenizerState(StateType.ARITHMETIC_OPERATOR_END);
        } else if (c == '<') {
          return new TokenizerState(StateType.LESS_THAN_ARITHMETIC_OPERATOR);
        } else if (c == '>') {
          return new TokenizerState(StateType.GREATER_THAN_ARITHMETIC_OPERATOR);
        } else if (c == '(') {
          return new TokenizerState(StateType.OPEN_PARENTHESIS_END);
        } else if (c == ')') {
          return new TokenizerState(StateType.CLOSE_PARENTHESIS_END);
        } else if (c == ' ') {
          return new TokenizerState(StateType.START);
        } else if (c == ',') {
          return new TokenizerState(StateType.SYMBOL_END);
        } else {
          throw new UnknownCharacterException();
        }

      case COMMENT:
        if (c == '-') {
          return new TokenizerState(StateType.COMMENT_CONTENT);
        } else {
          return new TokenizerState(StateType.ARITHMETIC_OPERATOR_END);
        }

      case COMMENT_CONTENT:
        if (c == '\n') {
          return new TokenizerState(StateType.COMMENT_END);
        } else {
          return new TokenizerState(StateType.COMMENT_CONTENT);
        }

      case COMMENT_END:
        return new TokenizerState(StateType.START);

      case LESS_THAN_ARITHMETIC_OPERATOR:
        if (c == '>' || c == '=' || isSkippable(c)) {
          return new TokenizerState(StateType.ARITHMETIC_OPERATOR_END);
        } else {
          throw new UnknownCharacterException();
        }

      case GREATER_THAN_ARITHMETIC_OPERATOR:
        if (c == '=' || isSkippable(c)) {
          return new TokenizerState(StateType.ARITHMETIC_OPERATOR_END);
        } else {
          throw new UnknownCharacterException();
        }

      case ARITHMETIC_OPERATOR_END:
        return new TokenizerState(StateType.START);

      case SYMBOL_END:
        return new TokenizerState(StateType.START);

      case DIGIT_WHOLE:

        if (Character.isDigit(c)) {
          return new TokenizerState(StateType.DIGIT_WHOLE);
        } else if (isSkippable(c)) {
          return new TokenizerState(StateType.DIGIT_END);
        } else {
          throw new UnknownCharacterException();
        }

      case DIGIT_END:
        return new TokenizerState(StateType.START);

      case ALPHABETIC:
        if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_') {
          return new TokenizerState(StateType.ALPHABETIC);
        } else if (isSkippable(c) || c == ',') {
          return new TokenizerState(StateType.ALPHABETIC_END);
        } else {
          throw new UnknownCharacterException();
        }

      case ALPHABETIC_END:
        return new TokenizerState(StateType.START);

      default:
        throw new InvalidTokenizerStateException();
    }
  }

  @Override
  public String toString() {
    return "TokenizerState [type=" + type + "]";
  }

}
