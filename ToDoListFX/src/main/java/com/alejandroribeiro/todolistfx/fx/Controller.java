/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.fx;

import com.alejandroribeiro.todolistfx.Application;
import com.alejandroribeiro.todolistfx.model.*;
import com.alejandroribeiro.todolistfx.services.*;
import com.alejandroribeiro.todolistfx.utils.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the Application.
 */
public class Controller implements Initializable {

    @FXML
    public Button btnDelete;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnAdd;
    @FXML
    private ComboBox<String> cmbTypeSearch;

    @FXML
    private ComboBox<String> cmbPrioritySearch;

    @FXML
    private ComboBox<String> cmbDoneSearch;

    @FXML
    private ComboBox<String> cmbDifficultySearch;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private ComboBox<Integer> cmbPriority;

    @FXML
    private ComboBox<String> cmbDone;

    @FXML
    private ComboBox<Integer> cmbDifficulty;

    private ObservableList<ToDoTask> taskObservableList;

    @FXML
    private TextField txtDescription;

    @FXML
    private ListView<ToDoTask> lvTask;

    private GetTasks getTasks;
    private ToDoTask updatedTask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadComboBoxes();
        LoadList();
        lvTask.getSelectionModel().selectedItemProperty().addListener(l -> {
            LoadUpdatedTask();
        });
    }

    /**
     * Method to load the selected existing task.
     */

    private void LoadUpdatedTask() {
        updatedTask = lvTask.getSelectionModel().getSelectedItem();
        if (updatedTask != null) {
            txtDescription.setText(updatedTask.getDescription());
            cmbType.getSelectionModel().select(updatedTask.getType());
            cmbPriority.getSelectionModel().select(updatedTask.getPriority());
            cmbDone.getSelectionModel().select(updatedTask.isDone() ? "Done" : "Not done");
            cmbDifficulty.getSelectionModel().select(updatedTask.getDifficulty());
        }
    }

    /**
     * Initialization method for the ComboBox data.
     */
    private void LoadComboBoxes() {
        cmbType.getItems().clear();
        for (String s4 : Arrays.asList("home", "work", "family", "sport", "undefined")) {
            cmbType.getItems().add(s4);
        }
        cmbType.getSelectionModel().selectFirst();
        cmbTypeSearch.getItems().clear();
        for (String s3 : Arrays.asList("Show all", "home", "work", "family", "sport", "undefined")) {
            cmbTypeSearch.getItems().add(s3);
        }
        cmbTypeSearch.getSelectionModel().selectFirst();
        cmbPriority.getItems().clear();
        for (int j = 1; j < 6; j++) {
            cmbPriority.getItems().add(j);
        }
        cmbPriority.getSelectionModel().selectFirst();
        cmbPrioritySearch.getItems().clear();
        for (String s2 : Arrays.asList("Show all", "1", "2", "3", "4", "5")) {
            cmbPrioritySearch.getItems().add(s2);
        }
        cmbPrioritySearch.getSelectionModel().selectFirst();
        cmbDone.getItems().clear();
        cmbDone.getItems().add("Done");
        cmbDone.getItems().add("Not done");
        cmbDone.getSelectionModel().selectFirst();
        cmbDoneSearch.getItems().clear();
        for (String s1 : Arrays.asList("Show all", "Done", "Not done")) {
            cmbDoneSearch.getItems().add(s1);
        }
        cmbDoneSearch.getSelectionModel().selectFirst();
        cmbDifficulty.getItems().clear();
        for (int i = 0; i < 11; i++) {
            cmbDifficulty.getItems().add(i);
        }
        cmbDifficulty.getSelectionModel().selectFirst();
        cmbDifficultySearch.getItems().clear();
        for (String s : Arrays.asList("Show all", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")) {
            cmbDifficultySearch.getItems().add(s);
        }
        cmbDifficultySearch.getSelectionModel().selectFirst();
    }

    /**
     * Method to load the data into the ListView.
     */
    private void LoadList() {
        String type, priority, done, difficulty;

        type = cmbTypeSearch.getSelectionModel().getSelectedItem();
        priority = cmbPrioritySearch.getSelectionModel().getSelectedItem();
        done = cmbDoneSearch.getSelectionModel().getSelectedItem();
        difficulty = cmbDifficultySearch.getSelectionModel().getSelectedItem();

        getTasks = new GetTasks(type, priority, done, difficulty);
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (getTasks.getValue().isOk()) {
                taskObservableList = FXCollections.observableArrayList(getTasks.getValue().getResult());
                lvTask.setItems(taskObservableList);
            }
        });
        getTasks.setOnFailed(e ->
        {
            MessageUtils.showError("Error", "Error \n" + e.getSource().getException().toString());
        });
    }

    /**
     * Method to filter the tasks by type
     */
    @FXML
    public void cmbTypeSearch_onChange(ActionEvent actionEvent) {
        String filter = cmbTypeSearch.getSelectionModel().getSelectedItem();

        cmbPrioritySearch.getSelectionModel().selectFirst();
        cmbDoneSearch.getSelectionModel().selectFirst();
        cmbDifficultySearch.getSelectionModel().selectFirst();

        GetTasks getTasks = new GetTasks(filter, null, null, null);
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (getTasks.getValue().isOk()) {
                taskObservableList = FXCollections.observableList(getTasks.getValue().getResult());
                lvTask.setItems(taskObservableList);
                ClearForm();
            } else {
                MessageUtils.showError("Error", "No task matches the filter");
            }
        });

        getTasks.setOnFailed(e -> MessageUtils.showError("Error", "Connection failed"));
    }

    /**
     * Method to filter the tasks by priority
     */
    @FXML
    public void cmbPrioritySearch_onChange(ActionEvent actionEvent) {
        String filter = cmbPrioritySearch.getSelectionModel().getSelectedItem();

        cmbTypeSearch.getSelectionModel().selectFirst();
        cmbDoneSearch.getSelectionModel().selectFirst();
        cmbDifficultySearch.getSelectionModel().selectFirst();

        GetTasks getTasks = new GetTasks(null, filter, null, null);
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (getTasks.getValue().isOk()) {
                taskObservableList = FXCollections.observableList(getTasks.getValue().getResult());
                lvTask.setItems(taskObservableList);
                ClearForm();
            } else {
                MessageUtils.showError("Error", "No task matches the filter");
            }
        });

        getTasks.setOnFailed(e -> MessageUtils.showError("Error", "Connection failed"));

    }

    /**
     * Method to filter the tasks by done or not done
     */
    @FXML
    public void cmbDoneSearch_onChange(ActionEvent actionEvent) {
        String filter = cmbDoneSearch.getSelectionModel().getSelectedItem();

        cmbTypeSearch.getSelectionModel().selectFirst();
        cmbPrioritySearch.getSelectionModel().selectFirst();
        cmbDifficultySearch.getSelectionModel().selectFirst();

        GetTasks getTasks = new GetTasks(null, null, filter, null);
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (getTasks.getValue().isOk()) {
                taskObservableList = FXCollections.observableList(getTasks.getValue().getResult());
                lvTask.setItems(taskObservableList);
                ClearForm();
            } else {
                MessageUtils.showError("Error", "No task matches the filter");
            }
        });

        getTasks.setOnFailed(e -> MessageUtils.showError("Error", "Connection failed"));

    }

    /**
     * Method to filter the tasks by difficulty
     */
    @FXML
    public void cmbDifficultySearch_onChange(ActionEvent actionEvent) {
        String filter = cmbDifficultySearch.getSelectionModel().getSelectedItem();

        cmbTypeSearch.getSelectionModel().selectFirst();
        cmbPrioritySearch.getSelectionModel().selectFirst();
        cmbDoneSearch.getSelectionModel().selectFirst();

        GetTasks getTasks = new GetTasks(null, null, null, filter);
        getTasks.start();

        getTasks.setOnSucceeded(e -> {
            if (getTasks.getValue().isOk()) {
                taskObservableList = FXCollections.observableList(getTasks.getValue().getResult());
                lvTask.setItems(taskObservableList);
                ClearForm();
            } else {
                MessageUtils.showError("Error", "No task matches the filter");
            }
        });

        getTasks.setOnFailed(e -> MessageUtils.showError("Error", "Connection failed"));
    }

    /**
     * Method to clear all fields in the update form
     */
    private void ClearForm() {
        txtDescription.clear();
        cmbDifficulty.getSelectionModel().selectFirst();
        cmbDone.getSelectionModel().selectFirst();
        cmbPriority.getSelectionModel().selectFirst();
        cmbType.getSelectionModel().selectFirst();
    }

    /**
     * Method to delete Tasks through a DELETE service.
     */
    public void btnDelete_onClick(ActionEvent actionEvent) {
        ToDoTask selectedTask = lvTask.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            Optional<ButtonType> result = MessageUtils.showConfirmationAlert("Delete task",
                    "Delete the following task?:\nTask data:" + selectedTask.refinedToString());

            if (result.get() == ButtonType.OK) {
                DeleteTask deleteTask = new DeleteTask(selectedTask.getId());
                deleteTask.start();

                deleteTask.setOnSucceeded(e -> {
                    if (deleteTask.getValue().isOk()) {
                        MessageUtils.showMessage("Success", "Successfully deleted the following " +
                                "task:\nTask data:" +
                                deleteTask.getValue().getResult().refinedToString());
                    } else {
                        MessageUtils.showMessage("Error", "Error: " +
                                deleteTask.getValue().getError());
                    }
                    lvTask.getItems().clear();
                    LoadList();
                    ClearForm();
                });
                deleteTask.setOnFailed(e -> {
                    MessageUtils.showError("Error", "Connection failed");
                });
            }
        } else {
            MessageUtils.showError("Error", "Must select a task first");
        }
    }

    /**
     * Event for the custom Close button
     *
     * @param actionEvent
     */
    public void btnClose_onClick(ActionEvent actionEvent) {
        Optional<ButtonType> result = MessageUtils.showConfirmationAlert("Exit",
                "Are you sure you want to close the app?");

        if (result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Method used to add new Tasks through a POST service.
     *
     * @param actionEvent
     */
    public void btnAdd_onClick(ActionEvent actionEvent) {
        String description = txtDescription.getText();

        if (!description.isEmpty()) {
            String type = cmbType.getValue();
            int priority = cmbPriority.getValue();
            boolean done = cmbDone.getValue().equals("Done");
            int difficulty = cmbDifficulty.getValue();

            ToDoTask task = new ToDoTask(description, type, priority, done, difficulty);

            PostTask postTask = new PostTask(task);
            postTask.start();

            postTask.setOnSucceeded(e -> {
                lvTask.getItems().clear();
                LoadList();
                MessageUtils.showMessage("Success", "Added the following task:" +
                        "\nTask data:" + task.refinedToString());
                ClearForm();
            });
            postTask.setOnFailed(e -> {
                MessageUtils.showError("Error", "Error \n" + e.getSource().getException().toString());
            });
        } else {
            MessageUtils.showError("Error", "Description cannot be empty.");
        }
    }

    /**
     * Method used to update Tasks through a PUT service.
     * @param actionEvent
     */
    public void btnUpdate_onClick(ActionEvent actionEvent) {
        ToDoTask selectedTask = lvTask.getSelectionModel().getSelectedItem();

        if (selectedTask != null) {
            Optional<ButtonType> result = MessageUtils.showConfirmationAlert("Update task",
                    "Update the following task?:\nTask data:" + selectedTask.refinedToString());

            if (result.get() == ButtonType.OK) {
                String description = txtDescription.getText();

                if (!description.isEmpty()) {
                    String type = cmbType.getValue();
                    int priority = cmbPriority.getValue();
                    boolean done = cmbDone.getValue().equals("Done");
                    int difficulty = cmbDifficulty.getValue();

                    selectedTask.setType(type);
                    selectedTask.setPriority(priority);
                    selectedTask.setDescription(description);
                    selectedTask.setDifficulty(difficulty);
                    selectedTask.setDone(done);

                    PutTask putTask = new PutTask(selectedTask);
                    putTask.start();

                    putTask.setOnSucceeded(e -> {
                        lvTask.getItems().clear();
                        LoadList();
                        MessageUtils.showMessage("Success", "Updated the following task:" +
                                "\nTask data:" + selectedTask.refinedToString());
                        ClearForm();
                    });
                    putTask.setOnFailed(e -> {
                        MessageUtils.showError("Error", "Error \n" + e.getSource().getException().toString());
                    });
                } else {
                    MessageUtils.showError("Error", "Description cannot be empty.");
                }
            }
        }
        else {
            MessageUtils.showError("Error", "Must select a task first");
        }
    }
}