package org.nibble.compiladornibblejavafx;


import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "lexer", mixinStandardHelpOptions = true, version = "NibbleLexer 1.0",
description = "Analizador léxico simplificado para pruebas")
public class PicoCLI implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--file"}, description = "Archivo a procesar", required = false)
    private File file;

    @Override
    public Integer call() throws Exception {

        if(file != null) {
            BufferedReader bfr = Files.newBufferedReader(file.toPath());
            Lexer lexer = new Lexer(bfr);
            Token token = lexer.yylex();
            while (token.getTokenType() != TokenConstant.EOF) {
                System.out.println(token);
                token = lexer.yylex();
            }
            return 0;
        }else{

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
    }


    public static void main(String[] args) {
        //System.out.println("NibbleLexer 1.0 Iniciando...");
        int exitCode = new CommandLine(new PicoCLI()).execute(args);
        //System.out.println(exitCode == 0 ? "NibbleLexer 1.0 Terminado." : "NibbleLexer 1.0 Terminado con errores.");
    }


}
