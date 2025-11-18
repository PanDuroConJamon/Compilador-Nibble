package org.nibble.compiladornibblejavafx;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
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
    private List<String> identifiers  = new ArrayList<>();

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
    private MenuItem menuItemGuardarArchivo;
    @FXML
    private MenuItem menuItemAbrirArchivo;
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
    private MenuItem menuAcerca;



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

    private void ejecutarLexico(String seccción){
        System.out.println("Ejecutar Lexico");
        if(archivoActual != null){
            onGuardarArchivoClick();
            try {
                boolean error = false;
                errores.clear();
                identifiers.clear();

                BufferedReader bfr = Files.newBufferedReader(archivoActual.toPath());
                Lexer lexer = new Lexer(bfr);
                Token token = lexer.yylex();
                while (token.getTokenType() != TokenConstant.EOF) {
                    System.out.println(token);
                    if(token.getTokenType() == TokenConstant.ERROR){
                        error = true;
                        errores.add("Error en linea " + token.getLine() + ": " + token.getMsg());
                    }
                    if(token.getTokenType() == TokenConstant.IDENTIFIER){
                        identifiers.add(token.getTokenLexeme() + " " + token.getLine());
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
        archivoActual = null;
        onGuardarArchivoClick();
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
        ejecutarLexico("");

    }

    @FXML
    protected void onEjecutarSintacticoClick(){
        System.out.println("Ejecutar Sintáctico");
    }

    @FXML
    protected void onTablaDeSimbolos1Click(){
        System.out.println("Tabla De Simbolos 1");
        System.out.println("Ejecutar ts");
        if(archivoActual != null){
            onGuardarArchivoClick();
            try {
                boolean error = false;
                errores.clear();
                identifiers.clear();

                BufferedReader bfr = Files.newBufferedReader(archivoActual.toPath());
                Lexer lexer = new Lexer(bfr);
                Token token = lexer.yylex();
                while (token.getTokenType() != TokenConstant.EOF) {
                    System.out.println(token);
                    if(token.getTokenType() == TokenConstant.ERROR){
                        error = true;
                        errores.add("Error en linea " + token.getLine() + ": " + token.getMsg());
                    }
                    if(token.getTokenType() == TokenConstant.IDENTIFIER){
                        String lexema = token.getTokenLexeme();
                        int linea = token.getLine();

                        boolean yaExiste = identifiers.stream()
                                .anyMatch(s -> {
                                    String[] parts = s.split(" ");
                                    return parts.length >= 1 && parts[0].equals(lexema);
                                });

                        String tipo = yaExiste ? "Referencia" : "Declaracion";
                        identifiers.add(lexema + " " + linea + " " + tipo);
                    }
                    token = lexer.yylex();
                }
                if(error){
                    textAreaMessageOutput.setText("Compilación con errores \n" + String.join("\n", errores));
                }else{
                    textAreaMessageOutput.setText("Compilación exitosa");


                    String idsText;
                    if (identifiers.isEmpty()) {
                        idsText = " ";
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(String.format("%-20s %-8s %-12s%n", "Identificador", "Línea", "Tipo"));
                        sb.append("-------------------- -------- ------------\n");
                        for (String s : identifiers) {
                            // guardado como "lexema linea tipo"
                            String[] parts = s.split(" ");
                            String lexema = parts.length > 0 ? parts[0] : "";
                            String linea  = parts.length > 1 ? parts[1] : "";
                            String tipo   = parts.length > 2 ? parts[2] : "";
                            sb.append(String.format("%-20s %-8s %-12s%n", lexema, linea, tipo));
                        }
                        idsText = sb.toString();
                    }

                    Alert alertIds = new Alert(Alert.AlertType.NONE);
                    alertIds.setTitle("TS");
                    alertIds.setResizable(true); // <--- hace el diálogo redimensionable

                    TextArea area = new TextArea(idsText);
                    area.setEditable(false);
                    area.setWrapText(false);
                    area.setPrefColumnCount(60);
                    area.setPrefRowCount(20);
                    area.setStyle("-fx-font-family: 'Ubuntu Mono', 'Consolas', 'Monospaced';");

                    alertIds.getDialogPane().setContent(area);
                    alertIds.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alertIds.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
                    alertIds.getDialogPane().setPrefSize(800, 400); // tamaño inicial cómodo

                    ButtonType btnCerrar = new ButtonType("Cerrar", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alertIds.getButtonTypes().add(btnCerrar);

                    alertIds.showAndWait();
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


    @FXML private void onAcercaClick(){
        System.out.println("" +
                "--- NIBBLE COMPILER 1.0 ---\n\n" +
                "    NibbleLexerCore 1.0\n" +
                "   NibbleParserCore 1.0\n\n" +
                "--------------------------\n\n" +
                "Owner: Cesar Rosas\n" +
                "GitHub: @PanDuroConJamon\n");
    }
}
