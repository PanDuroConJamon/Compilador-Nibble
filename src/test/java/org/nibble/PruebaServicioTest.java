package org.nibble;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class PruebaServicioTest {
    @Test
    public void getPrueba() {
        PruebaServicio pruebaServicio = new PruebaServicio();
        assertTrue(pruebaServicio.getPrueba());
    }

    @Test
    public void matchId() throws IOException {
        String testString = "ide1";
        Reader stringReader = new StringReader(testString);
        Lexer lexer = new Lexer(stringReader);
        Token token = lexer.yylex();
        assertEquals(TokenConstant.IDENTIFIER, token.getTokenType());
    }

    @Test
    public void matchNum() throws IOException {
        String testString = "1233";
        Reader stringReader = new StringReader(testString);
        Lexer lexer = new Lexer(stringReader);
        Token token = lexer.yylex();
        assertEquals(TokenConstant.NUMBER, token.getTokenType());
    }

    @Test
    public void noMatch() throws IOException {
        String testString = "@";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Reader stringReader = new StringReader(testString);
            Lexer lexer = new Lexer(stringReader);
            Token token = lexer.yylex();
        });
    }

    @Test void AND() throws IOException {
        String testString = "and";

        Reader stringReader = new StringReader(testString.toUpperCase());
        Lexer lexer = new Lexer(stringReader);
        Token token = lexer.yylex();
        assertEquals(TokenConstant.RESERVED_WORD, token.getTokenType());
    }

}