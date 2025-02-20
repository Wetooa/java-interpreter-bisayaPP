
package org.exception;

public class InvalidTokenizerStateException extends LexerException {

  @Override
  public String getMessage() {
    return "Invalid tokenizer state! " + super.getMessage();
  }

}
