package org.exception;

public class LexerException extends Exception {

  @Override
  public String getMessage() {
    return "Lexer exception: " + super.getMessage();
  }

}
