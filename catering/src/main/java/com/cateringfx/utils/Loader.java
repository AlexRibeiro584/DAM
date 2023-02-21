package com.cateringfx.utils;

import com.cateringfx.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Loader {
    public static Stage Load(String viewPath, Stage stage) throws IOException {
            Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(viewPath)));
            Scene viewScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(viewScene);
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(stage);
            return newStage;
    }
}
