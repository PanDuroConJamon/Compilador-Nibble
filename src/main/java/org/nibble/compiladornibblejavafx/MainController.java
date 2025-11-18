package org.nibble.compiladornibblejavafx;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class MainController {


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


    @FXML
    protected void onNuevoArchivoClick(){
        System.out.println("Nuevo Archivo");
    }
    @FXML
    protected void onAbrirArchivoClick(){
        System.out.println("Abrir Archivo");
    }
    @FXML
    protected void onGuardarArchivoClick(){
        System.out.println("Guardar Archivo");
    }
    @FXML
    protected void onCerrarArchivoClick(){
        System.out.println("Cerrar Archivo");
        textAreaCode.setText("");
        textAreaLineCount.setText("");
        textAreaMessageOutput.setText("");
    }

    @FXML
    protected void onEjecutarLexicoClick(){
        System.out.println("Ejecutar Lexico");
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
