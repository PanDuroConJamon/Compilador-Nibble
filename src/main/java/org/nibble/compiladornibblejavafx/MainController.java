package org.nibble.compiladornibblejavafx;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.nibble.compiladornibblejavafx.jflex.Lexer;
import org.nibble.compiladornibblejavafx.jflex.Token;
import org.nibble.compiladornibblejavafx.jflex.TokenConstant;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {

    // Archivo actualmente abierto/guardado
    private File archivoActual;
    private List<String> errores = new ArrayList<>();

    @FXML
    private AnchorPane anchorPaneMain;
    @FXML
    private AnchorPane anchorPaneMessageOutput;
    @FXML
    private AnchorPane anchorPaneLineCount;

    @FXML
    private TextArea textAreaCode;
    @FXML
    private TextArea textAreaLineCount;
    @FXML
    private TextArea textAreaMessageOutput;

    @FXML
    private MenuItem menuItemNuevoArchivo;
    @FXML
    private MenuItem menuItemAbrirArchivo;
    @FXML
    private MenuItem menuItemGuardarArchivo;
    @FXML
    private MenuItem menuItemGuardarComo;
    @FXML
    private MenuItem menuItemCerrarArchivo;

    @FXML
    private MenuItem menuItemEjecutarLexico;
    @FXML
    private MenuItem menuItemEjecutarSintactico;
    @FXML
    private MenuItem menuItemTablaDeSimbolos1;




    @FXML
    public void initialize() {
        textAreaLineCount.setEditable(false);
        textAreaLineCount.setText("1");
        textAreaCode.textProperty().addListener((obs, oldText, newText) -> actualizarLineas());
        actualizarTitulo(); // al iniciar, título base
    }

    private void actualizarLineas() {
        String text = textAreaCode.getText();
        if (text.isEmpty()) {
            textAreaLineCount.setText("1");
            return;
        }
        int lineCount = text.split("\n", -1).length;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= lineCount; i++) {
            sb.append(i).append("\n");
        }
        textAreaLineCount.setText(sb.toString());
    }

    private void actualizarTitulo() {
        if (textAreaCode == null || textAreaCode.getScene() == null) {
            return;
        }
        Window window = textAreaCode.getScene().getWindow();
        if (window instanceof Stage stage) {
            String baseTitle = "Nibble";
            if (archivoActual != null) {
                stage.setTitle(baseTitle + " - " + archivoActual.getName());
            } else {
                stage.setTitle(baseTitle);
            }
        }
    }

    private void guardarEnArchivo(File file) {
        try {
            String content = textAreaCode.getText();
            Files.writeString(file.toPath(), content, StandardCharsets.UTF_8);
            //textAreaMessageOutput.setText("Archivo guardado: " + file.getName());
            actualizarTitulo();
        } catch (Exception e) {
            e.printStackTrace();
            textAreaMessageOutput.setText("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private void ejecutarLexico(){
        System.out.println("Ejecutar Lexico");
        if(archivoActual != null){
            onGuardarArchivoClick();
            try {
                BufferedReader bfr = Files.newBufferedReader(archivoActual.toPath());
                Lexer lexer = new Lexer(bfr);
                Token token = lexer.yylex();
                int i = 0;
                boolean error = false;
                while (token.getTokenType() != TokenConstant.EOF) {
                    System.out.println(token);
                    if(token.getTokenType() == TokenConstant.ERROR){
                        error = true;
                        System.out.println("ERROOOOORR");
                        errores.add("Error en linea " + token.getLine() + ": " + token.getMsg());
                    }
                    token = lexer.yylex();
                }
                if(error){
                    textAreaMessageOutput.setText("Compilación con errores \n" + String.join("\n", errores));
                }else{
                    textAreaMessageOutput.setText("Compilación exitosa");
                }


                System.out.println("Analisis léxico finalizado");
            } catch (Exception e) {
                e.printStackTrace();
                textAreaMessageOutput.setText("Error al ejecutar el lexico: " + e.getMessage());
            }
        }else{
            onGuardarArchivoClick();
            textAreaMessageOutput.setText("");
        }
    }

    private void limpieza(){
        textAreaCode.setText("");
        textAreaLineCount.setText("");
        textAreaMessageOutput.setText("");
        archivoActual = null; // no hay archivo asociado
        actualizarLineas();
        actualizarTitulo();
    }




    @FXML
    protected void onNuevoArchivoClick(){
        System.out.println("Nuevo Archivo");
        limpieza();
    }

    @FXML
    protected void onAbrirArchivoClick(){
        limpieza();
        System.out.println("Abrir Archivo");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Abrir archivo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos Nibble (*.nbl)", "*.nbl"),
                new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        Window window = textAreaCode.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            try {
                String content = Files.readString(selectedFile.toPath());
                textAreaCode.setText(content);
                archivoActual = selectedFile;
                //textAreaMessageOutput.setText("Archivo abierto: " + selectedFile.getName());
                actualizarTitulo();
            } catch (Exception e) {
                e.printStackTrace();
                textAreaMessageOutput.setText("Error al leer el archivo: " + e.getMessage());
            }
        }
    }

    @FXML
    protected void onGuardarArchivoClick(){
        System.out.println("Guardar Archivo");
        if (archivoActual != null) {
            guardarEnArchivo(archivoActual);
        } else {
            onGuardarComoClick();
        }
    }

    @FXML
    protected void onGuardarComoClick(){
        System.out.println("Guardar Como");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo como");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos Nibble (*.nbl)", "*.nbl"),
                new FileChooser.ExtensionFilter("Archivos de texto (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );


        if (archivoActual != null) {
            fileChooser.setInitialFileName(archivoActual.getName());
        } else {
            fileChooser.setInitialFileName("nuevo_archivo.nbl");
        }

        Window window = textAreaCode.getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            archivoActual = file;
            guardarEnArchivo(file);
        }
    }



    @FXML
    protected void onCerrarArchivoClick(){
        System.out.println("Cerrar Archivo");
        limpieza();
    }

    @FXML
    protected void onEjecutarLexicoClick(){
        System.out.println("Ejecutar Lexico");
        ejecutarLexico();

    }

    @FXML
    protected void onEjecutarSintacticoClick(){
        System.out.println("Ejecutar Sintáctico");
    }

    @FXML
    protected void onTablaDeSimbolos1Click(){
        System.out.println("Tabla De Simbolos 1");
    }

}
