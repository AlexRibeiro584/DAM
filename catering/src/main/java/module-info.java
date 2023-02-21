module com.cateringfx {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.cateringfx to javafx.fxml;
    opens com.cateringfx.model to javafx.base;
    opens com.cateringfx.utils to javafx.base;
    exports com.cateringfx;
}