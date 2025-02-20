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

      put("ALANG", TokenType.FOR_LOOP);
      put("SA", TokenType.FOR_LOOP);

      put("NUMERO", TokenType.DATATYPE);
      put("LETRA", TokenType.DATATYPE);
      put("TINUOD", TokenType.DATATYPE);
      put("TIPIK", TokenType.DATATYPE);
    }
  };

  public List<Token> tokenize(String input) {
    input = new StringBuilder(input).append("\n").toString();

    ArrayList<Token> tokens = new ArrayList<>();
    StringBuilder value = new StringBuilder();
    TokenizerState state = new TokenizerState(StateType.START);

    for (char c : input.toCharArray()) {
      value.append(c);

      // FIX: Handle better later
      try {
        state = TokenizerState.transition(state, c);
      } catch (LexerException e) {
        e.printStackTrace();
      }

      if (state.getType() == StateType.DIGIT_END) {
        tokens.add(new Token(TokenType.NUMBER, value.toString()));
        value = new StringBuilder();
      }
    }

    return tokens;
  }

}
