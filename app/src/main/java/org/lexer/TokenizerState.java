package org.lexer;

import org.exception.InvalidTokenizerStateException;
import org.exception.LexerException;
import org.exception.UnknownCharacterException;

public class TokenizerState {
  public enum StateType {
    START,

    DIGIT_WHOLE,
    DIGIT_AFTER_E,
    DIGIT_DECIMAL,
    DIGIT_END,
  }

  private final StateType type;

  public StateType getType() {
    return type;
  }

  public TokenizerState(StateType type) {
    this.type = type;
  }

  private static boolean isSkippable(char c) {
    return c == ' ' || c == '\n' || c == '\t';
  }

  public static TokenizerState transition(TokenizerState state, char c) throws LexerException {
    // FIX: SHIT IMPLEMENTATION: JAVA PLEASE GIVE US RUST MATCH T_T

    if (state.type == StateType.START) {
      if (Character.isDigit(c)) {
        return new TokenizerState(StateType.DIGIT_WHOLE);
      } else if (isSkippable(c)) {
        return new TokenizerState(StateType.DIGIT_END);
      } else {
        throw new UnknownCharacterException();
      }
    } else if (state.type == StateType.DIGIT_END) {
      return new TokenizerState(StateType.START);
    }

    throw new InvalidTokenizerStateException();
  }
}
