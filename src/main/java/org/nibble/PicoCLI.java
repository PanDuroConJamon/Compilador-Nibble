package org.nibble;


import picocli.CommandLine;

import java.io.StringReader;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "lexer", mixinStandardHelpOptions = true, version = "NibbleLexer 1.0",
description = "Analizador léxico simplificado para pruebas")
public class PicoCLI implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (true) {
            System.out.print("Ingrese una cadena: ");
            input = scanner.nextLine();
            if (input.equals("halt")) {
                break;
            }
            Lexer lexer = new Lexer(new StringReader(input));
            Token token = lexer.yylex();
            System.out.println(token);
        }
        return 0;
    }


    public static void main(String[] args) {
        //System.out.println("NibbleLexer 1.0 Iniciando...");
        int exitCode = new CommandLine(new PicoCLI()).execute(args);
        //System.out.println(exitCode == 0 ? "NibbleLexer 1.0 Terminado." : "NibbleLexer 1.0 Terminado con errores.");
    }


}
