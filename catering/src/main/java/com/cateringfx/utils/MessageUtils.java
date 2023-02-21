package com.cateringfx.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
public class MessageUtils {
    //Various functions to show alerts
    public static void showError(String header, String message){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void showMessage(String header, String message){

        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        msg.setTitle("Information");
        msg.setHeaderText(header);
        msg.setContentText(message);
        msg.showAndWait();
    }
    public static boolean showConfirmation(String header, String message){

        boolean confirmation = false;
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setHeaderText(header);
        dialog.setTitle("Confirmation");
        dialog.setContentText(message);
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
            confirmation = true;

        return  confirmation;
    }
}
