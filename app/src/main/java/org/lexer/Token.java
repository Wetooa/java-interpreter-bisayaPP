package org.lexer;

class Token {

  public static enum TokenType {
    START_BLOCK,
    END_BLOCK,
    CONDITIONAL_BLOCK,

    VARIABLE_DECLARATION,

    INPUT_STATEMENTS,
    OUTPUT_STATEMENTS,

    FOR_LOOP,

    IDENTIFIER,

    EQUALS,
    OPEN_PARENTHESIS,
    CLOSE_PARENTHESIS,

    NUMBER,

    LOGICAL_OPERATOR,
    ARITHMETIC_OPERATOR,
    ASSIGNMENT_OPERATOR,

    BOOLEAN_VALUE,

    SYMBOL,

    STRING,

    DATATYPE
  }

  private final TokenType type;
  private final String value;

  public Token(TokenType type, String value) {
    this.type = type;
    this.value = value;
  }

  public TokenType getType() {
    return type;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Token [type=" + type + ", value=" + value + "]";
  }

}
