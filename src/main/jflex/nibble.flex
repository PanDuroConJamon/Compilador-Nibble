
//Archivo de especificación
package org.nibble.compiladornibblejavafx.jflex;

%%
%public
%class Lexer
%type Token
%line
%column

num = [0-9]|1[0-5]
hex = [a-fA-F]
h = [hH]
asig = <-?|>
letter = [a-zA-Z_-]
space = " "

%eofval{
    return new Token(TokenConstant.EOF, null, yyline, "");
%eofval}

%%

// PALABRAS RESERVADAS
// AND
[Aa][Nn][Dd] {return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, "");}

// OR
[Oo][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// XOR
[Xx][Oo][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// NOT
[Nn][Oo][Tt] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// SUMA
[Ss][Uu][Mm][Aa] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// SUMAC
[Ss][Uu][Mm][Aa][Cc] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// RESTA
[Rr][Ee][Ss][Tt][Aa] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// RESTAC
[Rr][Ee][Ss][Tt][Aa][Cc] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// IDES
[Ii][Dd][Ee][Ss] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// DDES
[Dd][Dd][Ee][Ss] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// CARGAR
[Cc][Aa][Rr][Gg][Aa][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// MOSTRAR
[Mm][Oo][Ss][Tt][Rr][Aa][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, "");}

// BORRAR
[Bb][Oo][Rr][Rr][Aa][Rr] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// TODO
[Tt][Oo][Dd][Oo] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// ALTO
[Aa][Ll][Tt][Oo] { return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, ""); }

// SALTO
[Ss][Aa][Ll][Tt][Oo] {return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline +1, "");  }

// IDES
[Ii][Dd][Ee][Ss] {return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, "");}

// DDES
[Dd][Dd][Ee][Ss] {return new Token(TokenConstant.RESERVED_WORD, yytext(), yyline + 1, "");}



// REGISTROS
[Rr][a-dA-D] {return new Token(TokenConstant.REGISTER, yytext(), yyline +1, "");}


// NÚMEROS
// Enteros (1)
{num} { return new Token(TokenConstant.NUMBER, yytext(), yyline + 1, ""); }
// Hexadecimal
{hex}{h}? { return new Token(TokenConstant.HEXADECIMAL, yytext(), yyline + 1, "");}

// OPERADORES
{asig} { return new Token(TokenConstant.OPERATOR, yytext(), yyline + 1, "");}
[:] { return new Token(TokenConstant.OPERATOR, yytext(), yyline + 1, "");}


// IDENTIFICADORES
{letter}({letter}|[0-9])* { return new Token(TokenConstant.IDENTIFIER, yytext(), yyline + 1, ""); }


//Tabulador, enter y espacios
[\n] { return new Token(TokenConstant.ENTER, "\\n", yyline + 1, "");}
{space} { return new Token(TokenConstant.SPACE, yytext(), yyline + 1, "");}
{space}{4}|[\t] { return new Token(TokenConstant.TABULATOR, yytext()+"\\t", yyline + 1, "");}


// ERRORRES
// Identificadores que inician con números
{num}+{letter}+ { return new Token(TokenConstant.ERROR, yytext(), yyline + 1, "La cadena \""+yytext()+"\" es ilegal"); }
// Operadores invalidos
{asig}({letter}|{num})* { return new Token(TokenConstant.ERROR, yytext(), yyline + 1, "La cadena \""+yytext()+"\" es ilegal"); }
// Números que exceden 15
1[6-9]|[1-9][0-9]* { return new Token(TokenConstant.ERROR, yytext(), yyline + 1, "El número "+yytext()+" excede el tamaño" +
       " permitido"); }

[^] { return new Token(TokenConstant.ERROR, yytext(), yyline + 1, "La cadena \""+yytext()+"\" es ilegal"); }




//{digit}+{letter}+ { throw new IllegalArgumentException("La cadena \""+yytext()+"\" es ilegal "); }