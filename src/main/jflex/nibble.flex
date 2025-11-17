
//Archivo de especificación
package org.nibble;
import java.io.*;

%%
%public
%class DemoLexer

digit = [0-9]
letter = [a-zA-Z]
whitespace = [ \t\r]
%type Token
%eofval{
    //return new Token(TokenConstant.EOF, null);
    //Lógica para EndOfFile
%eofval}

%%


//Expresión regular para identificadores
{letter}({letter}|{digit})*{
          //Lógica para identificador
      }

//Expresión para digitos
{digit}+{
          //Lógica para números
      }

