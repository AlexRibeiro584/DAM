module com.alejandroribeiro.cardclient.cardclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires CardModel;

    opens com.alejandroribeiro.cardclient.cardclient to javafx.fxml;
    exports com.alejandroribeiro.cardclient.cardclient;
}