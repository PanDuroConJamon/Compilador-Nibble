
//Archivo de especificación
package org.nibble.compiladornibblejavafx.jflex;

%%
%public
%class Lexer
%type Token
%line
%column

num = [0-9]+
asig = <-?|>
letter = [a-zA-Z_-]
space = " "

%eofval{
    return new Token(TokenConstant.EOF, null, yyline, "");
%eofval}

%%

// PALABRAS RESERVADAS
[Mm][Oo][Dd][Ee][Ll] {return new Token(TokenConstant.MODEL, yytext(), yyline + 1, "");}
[Dd][Aa][Tt][Aa] {return new Token(TokenConstant.DATA, yytext(), yyline + 1, "");}
[Vv][Aa][Rr][Ss] {return new Token(TokenConstant.VARS, yytext(), yyline + 1, "");}
[Oo][Bb][Jj] {return new Token(TokenConstant.OBJ, yytext(), yyline + 1, "");}
[Cc][Oo][Nn][Ss][Tt][Rr] {return new Token(TokenConstant.CONSTR, yytext(), yyline + 1, "");}

[Ss][Oo][Ll][Vv][Ee] {return new Token(TokenConstant.SOLVE, yytext(), yyline + 1, "");}
[Ll][Ii][Nn][Ee][Aa][Rr] {return new Token(TokenConstant.LINEAR, yytext(), yyline + 1, "");}
[Tt][Rr][Aa][Nn][Ss][Pp][Oo][Rr][Tt] {return new Token(TokenConstant.TRANSPORT, yytext(), yyline + 1, "");}
[Aa][Ss][Ss][Ii][Gg][Nn] {return new Token(TokenConstant.ASSIGN, yytext(), yyline + 1, "");}
[Nn][Ee][Tt] {return new Token(TokenConstant.NET, yytext(), yyline + 1, "");}
[Pp][Ee][Rr][Tt] {return new Token(TokenConstant.PERT, yytext(), yyline + 1, "");}
[Mm][Aa][Xx] {return new Token(TokenConstant.MAX, yytext(), yyline + 1, "");}
[Mm][Ii][Nn] {return new Token(TokenConstant.MIN, yytext(), yyline + 1, "");}
[Cc][Oo][Nn][Tt] {return new Token(TokenConstant.CONT, yytext(), yyline + 1, "");}
[Ii][Nn][Tt] {return new Token(TokenConstant.INT, yytext(), yyline + 1, "");}
[Bb][Ii][Nn] {return new Token(TokenConstant.BIN, yytext(), yyline + 1, "");}
[Oo][Rr][Ii][Gg] {return new Token(TokenConstant.ORIG, yytext(), yyline + 1, "");}
[Dd][Ee][Ss][Tt] {return new Token(TokenConstant.DEST, yytext(), yyline + 1, "");}
[Nn][Oo][Dd][Ee][Ss] {return new Token(TokenConstant.NODES, yytext(), yyline + 1, "");}
[Aa][Rr][Cc][Ss] {return new Token(TokenConstant.ARCS, yytext(), yyline + 1, "");}
[Cc][Oo][Ss][Tt] {return new Token(TokenConstant.COST, yytext(), yyline + 1, "");}
[Cc][Aa][Pp] {return new Token(TokenConstant.CAP, yytext(), yyline + 1, "");}
[Ss][Uu][Pp] {return new Token(TokenConstant.SUP, yytext(), yyline + 1, "");}
[Dd][Ee][Mm] {return new Token(TokenConstant.DEM, yytext(), yyline + 1, "");}
[Pp][Aa][Tt][Hh] {return new Token(TokenConstant.PATH, yytext(), yyline + 1, "");}
[Ff][Ll][Oo][Ww] {return new Token(TokenConstant.FLOW, yytext(), yyline + 1, "");}
[Ff][Rr][Oo][Mm] {return new Token(TokenConstant.FROM, yytext(), yyline + 1, "");}
[Tt][Oo] {return new Token(TokenConstant.TO, yytext(), yyline + 1, "");}
[Ss][Uu][Mm] {return new Token(TokenConstant.SUM, yytext(), yyline + 1, "");}
[Ss][Ii][Mm][Pp][Ll][Ee][Xx] {return new Token(TokenConstant.SIMPLEX, yytext(), yyline + 1, "");}
[Vv][Oo][Gg][Ee][Ll] {return new Token(TokenConstant.VOGEL, yytext(), yyline + 1, "");}
[Hh][Uu][Nn][Gg][Aa][Rr][Ii][Aa][Nn] {return new Token(TokenConstant.HUNGARIAN, yytext(), yyline + 1, "");}
[Dd][Ii][Jj][Kk][Ss][Tt][Rr][Aa] {return new Token(TokenConstant.DIJKSTRA, yytext(), yyline + 1, "");}
[Mm][Aa][Xx][Ff][Ll][Oo][Ww] {return new Token(TokenConstant.MAXFLOW, yytext(), yyline + 1, "");}
[Cc][Pp][Mm] {return new Token(TokenConstant.CPM, yytext(), yyline + 1, "");}


// OPERADORES RELACIONALES
[<=] { return new Token(TokenConstant.MENOR_IGUAL, yytext(), yyline + 1, "");}
[>=] { return new Token(TokenConstant.MAYOR_IGUAL, yytext(), yyline + 1, "");}
[=] { return new Token(TokenConstant.IGUAL, yytext(), yyline + 1, "");}
[<] { return new Token(TokenConstant.MENOR, yytext(), yyline + 1, "");}
[>] { return new Token(TokenConstant.MAYOR, yytext(), yyline + 1, "");}

// OPERADORES ARITMETICOS
[+] { return new Token(TokenConstant.SUMA, yytext(), yyline + 1, "");}
[-] { return new Token(TokenConstant.RESTA, yytext(), yyline + 1, "");}
[*] { return new Token(TokenConstant.MULTIPLICACION, yytext(), yyline + 1, "");}
[/] { return new Token(TokenConstant.DIVISION, yytext(), yyline + 1, "");}

// SEPARADORES
[,] { return new Token(TokenConstant.COMA, yytext(), yyline + 1, "");}
[:] { return new Token(TokenConstant.DOSPUNTOS, yytext(), yyline + 1, "");}
[->] { return new Token(TokenConstant.ASIGNADOR, yytext(), yyline + 1, "");}
[;] { return new Token(TokenConstant.PUNTOCOMA, yytext(), yyline + 1, "");}

// AGRUPACIÓN
[(] { return new Token(TokenConstant.PARIZQUIERDO, yytext(), yyline + 1, "");}
[)] { return new Token(TokenConstant.PARDERECHO, yytext(), yyline + 1, "");}
[{] { return new Token(TokenConstant.LLAVEIZQUIERDA, yytext(), yyline + 1, "");}
[}] { return new Token(TokenConstant.LLAVEDERECHA, yytext(), yyline + 1, "");}
[\[] { return new Token(TokenConstant.CORCHETEIZQUIERDO, yytext(), yyline + 1, "");}
[\]] { return new Token(TokenConstant.CORCHETEDERECHO, yytext(), yyline + 1, "");}



// NÚMEROS
// Enteros (1)
[+-]?{num} { return new Token(TokenConstant.NUMBER, yytext(), yyline + 1, ""); }
[+-]?{num}[.]{num} { return new Token(TokenConstant.NUMBER, yytext(), yyline + 1, ""); }


// OPERADORES
//{asig} { return new Token(TokenConstant.OPERATOR, yytext(), yyline + 1, "");}



// IDENTIFICADORES
{letter}({letter}|[0-9])* { return new Token(TokenConstant.IDENTIFIER, yytext(), yyline + 1, ""); }
{num}[*]{letter}({letter}|[0-9])* { return new Token(TokenConstant.IDENTIFIER, yytext(), yyline + 1, ""); }




//Tabulador, enter y espacios
[\n] { /*return new Token(TokenConstant.ENTER, "\\n", yyline + 1, "");*/}
{space} { /*return new Token(TokenConstant.SPACE, yytext(), yyline + 1, "");*/}
{space}{4}|[\t] {/* return new Token(TokenConstant.TABULATOR, yytext()+"\\t", yyline + 1, "");*/}


// ERRORRES
// Identificadores que inician con números
{num}+{letter}+ { return new Token(TokenConstant.ERROR, yytext(), yyline + 1, "La cadena \""+yytext()+"\" es ilegal"); }
[*]{letter}({letter}|[0-9])* { return new Token(TokenConstant.ERROR, yytext(), yyline + 1, "La cadena \""+yytext()+"\" es ilegal"); }

[^] { return new Token(TokenConstant.ERROR, yytext(), yyline + 1, "La cadena \""+yytext()+"\" es ilegal"); }
