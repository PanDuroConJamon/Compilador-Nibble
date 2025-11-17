
//Archivo de especificación
package org.nibble;

%%
%public
%class Lexer

num = [0-9]|1[0-5]
hex = [a-fA-F]
h = [hH]
asig = <-?|>
letter = [a-zA-Z]
whitespace = [\s]
tabulator = [\t]
enter = [\n\r]
%type Token
%eofval{
    return new Token(TokenConstant.EOF, null, "");
%eofval}

%%

// PALABRAS RESERVADAS
// AND
[Aa][Nn][Dd] {return new Token(TokenConstant.RESERVED_WORD, yytext(), "");}

// OR
[Oo][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// XOR
[Xx][Oo][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// NOT
[Nn][Oo][Tt] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// SUMA
[Ss][Uu][Mm][Aa] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// SUMAC
[Ss][Uu][Mm][Aa][Cc] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// RESTA
[Rr][Ee][Ss][Tt][Aa] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// RESTAC
[Rr][Ee][Ss][Tt][Aa][Cc] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// IDES
[Ii][Dd][Ee][Ss] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// DDES
[Dd][Dd][Ee][Ss] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// CARGAR
[Cc][Aa][Rr][Gg][Aa][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// BORRAR
[Bb][Oo][Rr][Rr][Aa][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// TODO
[Tt][Oo][Dd][Oo] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }

// ALTO
[Aa][Ll][Tt][Oo] { return new Token(TokenConstant.RESERVED_WORD, yytext(), ""); }



// NÚMEROS
// Enteros (1)
{num} { return new Token(TokenConstant.NUMBER, yytext(), ""); }
// Hexadecimal
{hex}{h}? { return new Token(TokenConstant.HEXADECIMAL, yytext(), "");}

// OPERADORES
{asig} { return new Token(TokenConstant.OPERATOR, yytext(), "");}
[:] { return new Token(TokenConstant.OPERATOR, yytext(), "");}


// IDENTIFICADORES
{letter}({letter}|[0-9])* { return new Token(TokenConstant.IDENTIFIER, yytext(), ""); }


//Tabulador, enter y espacios
{tabulator} { return new Token(TokenConstant.TABULATOR, yytext(), "");}
{enter} { return new Token(TokenConstant.ENTER, yytext(), "");}
{whitespace} { return new Token(TokenConstant.WHITESPACE, yytext(), "");}


// ERRORRES
// Identificadores que inician con números
{num}+{letter}+ { return new Token(TokenConstant.ERROR, yytext(), "La cadena \""+yytext()+"\" es ilegal"); }
// Operadores invalidos
{asig}({letter}|{num})* { return new Token(TokenConstant.ERROR, yytext(), "La cadena \""+yytext()+"\" es ilegal"); }
// Números que exceden 15
1[6-9]|[1-9][0-9]* { return new Token(TokenConstant.ERROR, yytext(), "El número "+yytext()+" excede el tamaño" +
       " permitido"); }

[^] { return new Token(TokenConstant.ERROR, yytext(), "La cadena \""+yytext()+"\" es ilegal"); }




//{digit}+{letter}+ { throw new IllegalArgumentException("La cadena \""+yytext()+"\" es ilegal "); }