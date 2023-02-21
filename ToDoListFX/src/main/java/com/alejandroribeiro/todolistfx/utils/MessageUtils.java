/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.utils;


import com.alejandroribeiro.todolistfx.Application;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 * Utility class to handle dialogs
 */
public class MessageUtils {
    /**
     * Method to show error messages.
     * @param header The alert's header.
     * @param message The message shown in the alert.
     */
    public static void showError(String header, String message)
    {
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });
        dialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.setX(event.getScreenX() - xOffset[0]);
                dialog.setY(event.getScreenY() - yOffset[0]);
            }
        });
        dialogPane.getStylesheets().add(Application.class.getResource("alertStyles.css").toExternalForm());
        ((Button)dialogPane.lookupButton(ButtonType.OK)).setText("OK");
        dialog.setHeight(400);
        dialog.setWidth(800);
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    /**
     * Method to show information messages
     * @param header The alert's header.
     * @param message The message shown in the alert.
     */
    public static void showMessage(String header, String message)
    {
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });
        dialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.setX(event.getScreenX() - xOffset[0]);
                dialog.setY(event.getScreenY() - yOffset[0]);
            }
        });
        dialogPane.getStylesheets().add(Application.class.getResource("alertStyles.css").toExternalForm());
        ((Button)dialogPane.lookupButton(ButtonType.OK)).setText("OK");
        dialog.setHeight(400);
        dialog.setWidth(800);
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        dialog.showAndWait();
    }
    /**
     * Method to show a confirmation alert with the given parameters.
     * @param header The alert's header.
     * @param message The message shown in the alert.
     */
    public static Optional<ButtonType> showConfirmationAlert(String header, String message) {
        final double[] xOffset = {0};
        final double[] yOffset = {0};
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        DialogPane dialogPane = dialog.getDialogPane();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialogPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset[0] = event.getSceneX();
                yOffset[0] = event.getSceneY();
            }
        });
        dialogPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.setX(event.getScreenX() - xOffset[0]);
                dialog.setY(event.getScreenY() - yOffset[0]);
            }
        });
        dialogPane.getStylesheets().add(Application.class.getResource("alertStyles.css").toExternalForm());
        ((Button)dialogPane.lookupButton(ButtonType.OK)).setText("YES");
        ((Button)dialogPane.lookupButton(ButtonType.CANCEL)).setText("NO");
        dialog.setHeight(400);
        dialog.setWidth(800);
        dialog.setHeaderText(header);
        dialog.setContentText(message);

        return dialog.showAndWait();
    }
}
