package org.nibble;

public class Token {
    private TokenConstants tokenType;
    private String tokenLexeme;

    public Token(TokenConstants tokenType, String tokenLexeme) {
        this.tokenType = tokenType;
        this.tokenLexeme = tokenLexeme;
    }

    public String getTokenLexeme() {
        return tokenLexeme;
    }

    public void setTokenLexeme(String tokenLexeme) {
        this.tokenLexeme = tokenLexeme;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", tokenLexeme='" + tokenLexeme + '\'' +
                '}';
    }
}
