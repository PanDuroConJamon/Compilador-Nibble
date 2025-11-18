module org.nibble.compiladornibblejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires info.picocli;


    opens org.nibble.compiladornibblejavafx to javafx.fxml;
    exports org.nibble.compiladornibblejavafx;
    exports org.nibble.compiladornibblejavafx.jflex;
    opens org.nibble.compiladornibblejavafx.jflex to javafx.fxml;
}