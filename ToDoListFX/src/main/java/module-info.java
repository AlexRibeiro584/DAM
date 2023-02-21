module com.alejandroribeiro.todolistfx.todolistfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    requires org.controlsfx.controls;

    opens com.alejandroribeiro.todolistfx to javafx.fxml;
    exports com.alejandroribeiro.todolistfx;
    exports com.alejandroribeiro.todolistfx.fx;
    opens com.alejandroribeiro.todolistfx.fx to javafx.fxml;
    opens com.alejandroribeiro.todolistfx.model;
    opens com.alejandroribeiro.todolistfx.services;
    opens com.alejandroribeiro.todolistfx.utils;
}