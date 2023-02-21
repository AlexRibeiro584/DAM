module com.menu.menudesigner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cateringfx to javafx.fxml;
    exports com.cateringfx;
}