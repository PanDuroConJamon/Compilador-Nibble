package org.nibble.compiladornibblejavafx.jflex;

public class Token {
    private TokenConstant tokenType;
    private String tokenLexeme;
    private String msg = "";
    private int line = 0;

    public Token(TokenConstant tokenType, String tokenLexeme, int line, String msg) {
        this.tokenType = tokenType;
        this.tokenLexeme = tokenLexeme;
        this.line = line;
        this.msg = msg;
    }

    public TokenConstant getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenConstant tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenLexeme() {
        return tokenLexeme;
    }

    public void setTokenLexeme(String tokenLexeme) {
        this.tokenLexeme = tokenLexeme;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", tokenLexeme='" + tokenLexeme + '\'' +
                ", msg='" + msg + '\'' +
                ", line=" + line +
                '}';
    }
}
