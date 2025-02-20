package org.exception;

public class UnknownCharacterException extends LexerException {

  @Override
  public String getMessage() {
    return "Unkown character in tokenizer! " + super.getMessage();
  }

}
