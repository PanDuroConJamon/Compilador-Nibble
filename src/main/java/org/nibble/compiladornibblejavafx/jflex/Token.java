package org.nibble.compiladornibblejavafx.jflex;

import org.nibble.TokenConstant;

public class Token {
    private TokenConstant tokenType;
    private String tokenLexeme;
    private String msg = "";

    public Token(TokenConstant tokenType, String tokenLexeme, String msg) {
        this.tokenType = tokenType;
        this.tokenLexeme = tokenLexeme;
        this.msg = msg;
    }

    public void setTokenLexeme(String tokenLexeme) {
        this.tokenLexeme = tokenLexeme;
    }
    public TokenConstant getTokenType() {
        return tokenType;
    }

    public String getTokenLexeme() {
        return tokenLexeme;
    }

    public void setTokenType(TokenConstant tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", tokenLexeme='" + tokenLexeme + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
