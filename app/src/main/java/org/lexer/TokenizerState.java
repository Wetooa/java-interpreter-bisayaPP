package org.lexer;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.exception.InvalidTokenizerStateException;
import org.exception.LexerException;
import org.exception.UnknownCharacterException;

public class TokenizerState {

  public static enum StateType {
    START,

    SKIPPABLE,
    SKIPPABLE_END,

    COMMENT,
    COMMENT_CONTENT,
    COMMENT_END,

    LESS_THAN_ARITHMETIC_OPERATOR,
    GREATER_THAN_ARITHMETIC_OPERATOR,
    ARITHMETIC_OPERATOR_END,

    ASSIGNMENT_OPERATOR_BEGIN,
    ASSIGNMENT_OPERATOR,
    ASSIGNMENT_OPERATOR_END,

    OPEN_PARENTHESIS,
    OPEN_PARENTHESIS_END,
    CLOSE_PARENTHESIS,
    CLOSE_PARENTHESIS_END,

    ESCAPED_CHAR,
    ESCAPED_CHAR_LAST_BRACKET,
    ESCAPED_CHAR_END,

    SINGLE_QUOTE,
    SINGLE_QUOTE_LAST_QUOTE,
    SINGLE_QUOTE_END,

    DOUBLE_QUOTE,
    DOUBLE_QUOTE_LAST_QUOTE,
    DOUBLE_QUOTE_END,

    BOOLEAN,
    BOOLEAN_END,

    COMMA,
    COMMA_END,

    CARRIAGE_RETURN,
    CARRIAGE_RETURN_END,

    AMPERSAND,
    AMPERSAND_END,

    COLON,
    COLON_END,

    ALPHABETIC,
    ALPHABETIC_END,

    DIGIT_WHOLE,
    DIGIT_AFTER_E,
    DIGIT_DECIMAL,
    DIGIT_END,
  }

  private static final Set<StateType> END_STATES = EnumSet.of(StateType.SKIPPABLE_END, StateType.COMMENT_END,
      StateType.ARITHMETIC_OPERATOR_END, StateType.ASSIGNMENT_OPERATOR_END, StateType.DIGIT_END,
      StateType.ALPHABETIC_END, StateType.AMPERSAND_END, StateType.COMMA_END, StateType.SINGLE_QUOTE_END,
      StateType.DOUBLE_QUOTE_END,
      StateType.CARRIAGE_RETURN_END,
      StateType.COLON_END,
      StateType.OPEN_PARENTHESIS_END,
      StateType.CLOSE_PARENTHESIS_END,
      StateType.ESCAPED_CHAR_END);

  private static final List<Character> SKIPPABLE_SYMBOLS = List.of(' ', '\n', '\t');

  private static boolean isSkippable(char c) {
    return SKIPPABLE_SYMBOLS.contains(c);
  }

  public boolean isEndState() {
    return END_STATES.contains(this.type);
  }

  public boolean isResetState() {
    return this.isEndState() || this.type == StateType.START;
  }

  private final StateType type;

  public StateType getType() {
    return type;
  }

  public TokenizerState() {
    this.type = StateType.START;
  }

  public TokenizerState(StateType type) {
    this.type = type;
  }

  public static TokenizerState transition(TokenizerState state, char c) throws LexerException {

    switch (state.type) {

      case START:
        if (Character.isDigit(c)) {
          return new TokenizerState(StateType.DIGIT_WHOLE);
        } else if (Character.isAlphabetic(c) || c == '_') {
          return new TokenizerState(StateType.ALPHABETIC);
        } else if (c == '-') {
          return new TokenizerState(StateType.COMMENT);
        } else if (c == '+' || c == '*' || c == '/' || c == '%' || c == '=') {
          return new TokenizerState(StateType.ASSIGNMENT_OPERATOR_BEGIN);
        } else if (c == '<') {
          return new TokenizerState(StateType.LESS_THAN_ARITHMETIC_OPERATOR);
        } else if (c == '>') {
          return new TokenizerState(StateType.GREATER_THAN_ARITHMETIC_OPERATOR);
        } else if (c == '(') {
          return new TokenizerState(StateType.OPEN_PARENTHESIS);
        } else if (c == ')') {
          return new TokenizerState(StateType.CLOSE_PARENTHESIS);
        } else if (c == '[') {
          return new TokenizerState(StateType.ESCAPED_CHAR);
        } else if (isSkippable(c)) {
          return new TokenizerState(StateType.SKIPPABLE);
        } else if (c == ',') {
          return new TokenizerState(StateType.COMMA);
        } else if (c == '&') {
          return new TokenizerState(StateType.AMPERSAND);
        } else if (c == '$') {
          return new TokenizerState(StateType.CARRIAGE_RETURN);
        } else if (c == '\'') {
          return new TokenizerState(StateType.SINGLE_QUOTE);
        } else if (c == '\"') {
          return new TokenizerState(StateType.DOUBLE_QUOTE);
        } else if (c == ':') {
          return new TokenizerState(StateType.COLON);
        } else {
          throw new UnknownCharacterException();
        }

      case SKIPPABLE:
        return new TokenizerState(StateType.SKIPPABLE_END);

      case COMMENT:
        if (c == '-') {
          return new TokenizerState(StateType.COMMENT_CONTENT);
        } else if (c == '=') {
          return new TokenizerState(StateType.ASSIGNMENT_OPERATOR);
        } else {
          return new TokenizerState(StateType.ARITHMETIC_OPERATOR_END);
        }

      case COMMENT_CONTENT:
        if (c == '\n') {
          return new TokenizerState(StateType.COMMENT_END);
        } else {
          return new TokenizerState(StateType.COMMENT_CONTENT);
        }

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

      case ASSIGNMENT_OPERATOR_BEGIN:
        if (c == '=') {
          return new TokenizerState(StateType.ASSIGNMENT_OPERATOR);
        }
        return new TokenizerState(StateType.ARITHMETIC_OPERATOR_END);

      case ASSIGNMENT_OPERATOR:
        return new TokenizerState(StateType.ASSIGNMENT_OPERATOR_END);

      case OPEN_PARENTHESIS:
        return new TokenizerState(StateType.OPEN_PARENTHESIS_END);

      case CLOSE_PARENTHESIS:
        return new TokenizerState(StateType.CLOSE_PARENTHESIS_END);

      case ESCAPED_CHAR:
        if (c == ']') {
          return new TokenizerState(StateType.ESCAPED_CHAR_LAST_BRACKET);
        }
        return new TokenizerState(StateType.ESCAPED_CHAR);

      case ESCAPED_CHAR_LAST_BRACKET:
        return new TokenizerState(StateType.ESCAPED_CHAR_END);

      case COMMA:
        return new TokenizerState(StateType.COMMA_END);

      case CARRIAGE_RETURN:
        return new TokenizerState(StateType.CARRIAGE_RETURN_END);

      case AMPERSAND:
        return new TokenizerState(StateType.AMPERSAND_END);

      case COLON:
        return new TokenizerState(StateType.COLON_END);

      case SINGLE_QUOTE:
        if (c == '\'') {
          return new TokenizerState(StateType.SINGLE_QUOTE_LAST_QUOTE);
        } else {
          return new TokenizerState(StateType.SINGLE_QUOTE);
        }

      case SINGLE_QUOTE_LAST_QUOTE:
        return new TokenizerState(StateType.SINGLE_QUOTE_END);

      case DOUBLE_QUOTE:
        if (c == '\"') {
          return new TokenizerState(StateType.DOUBLE_QUOTE_LAST_QUOTE);
        } else {
          return new TokenizerState(StateType.DOUBLE_QUOTE);
        }

      case DOUBLE_QUOTE_LAST_QUOTE:
        return new TokenizerState(StateType.DOUBLE_QUOTE_END);

      case ALPHABETIC:
        if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_') {
          return new TokenizerState(StateType.ALPHABETIC);
        } else {
          return new TokenizerState(StateType.ALPHABETIC_END);
        }

      case DIGIT_WHOLE:
        if (Character.isDigit(c)) {
          return new TokenizerState(StateType.DIGIT_WHOLE);
        } else if (isSkippable(c)) {
          return new TokenizerState(StateType.DIGIT_END);
        } else {
          throw new UnknownCharacterException();
        }

      default:
        if (state.isEndState()) {
          return new TokenizerState(StateType.START);
        }

        throw new InvalidTokenizerStateException();
    }
  }

  @Override
  public String toString() {
    return "TokenizerState [type=" + type + "]";
  }

}
