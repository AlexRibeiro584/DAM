package com.cateringfx;

import com.cateringfx.model.Aliment;
import com.cateringfx.model.Menu;
import com.cateringfx.model.MenuElement;
import com.cateringfx.utils.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

//class MainController which takes care of every interactive item in the main view
public class MainController implements Initializable {
    @FXML private CheckBox milkCheck;
    @FXML private CheckBox nutsCheck;
    @FXML private CheckBox eggCheck;
    @FXML private CheckBox glutenCheck;
    private List<MenuElement>menuList;
    private Menu menu;
    @FXML private TableView<MenuElement> leftTableView;
    @FXML private TableColumn<MenuElement, Aliment> leftTableName;
    @FXML private TableColumn<MenuElement, Aliment> tableKcal;
    @FXML private TableColumn<MenuElement, Aliment> tableCarbs;
    @FXML private TableColumn<MenuElement, Aliment> tableFats;

    @FXML private TableView<MenuElement> tableViewRight;
    @FXML private TableColumn<MenuElement, String> columnMenuName;
    @FXML private TableColumn<MenuElement, String> columnMenuDesc;
    @FXML private DatePicker datePicker;

    private List<MenuElement> alimentList;
    //initialization method to fill the tables
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        open();
    }
    //the open method is invoked any time the main view is opened
    private void open(){
        datePicker.setValue(LocalDate.of(LocalDate.now().getYear(),
                LocalDate.now().getMonth(),LocalDate.now().getDayOfMonth()));
        menu = new Menu(LocalDate.now());
        alimentList = FXCollections.observableArrayList(FileUtils.loadElements());
        leftTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableKcal.setCellValueFactory(new PropertyValueFactory<>("calories"));
        tableCarbs.setCellValueFactory(new PropertyValueFactory<>("carbohydrates"));
        tableFats.setCellValueFactory(new PropertyValueFactory<>("fat"));
        leftTableView.setItems(FXCollections.observableList(alimentList));

        columnMenuName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnMenuDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        menuList = new ArrayList<>();
    }
    @FXML
    private void setLimitsClick(ActionEvent actionEvent){
        //function to open the set limits stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
            Stage newAliment = Loader.Load("limits.fxml", stage);
            newAliment.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void newAlimentClick(ActionEvent actionEvent){
        //function to open the new aliment stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        try {
           Stage newAliment = Loader.Load("new-aliment.fxml", stage);
           newAliment.setOnCloseRequest(e -> open());
           newAliment.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //function to add aliments to the right table
    @FXML
    private void addAlimentToTheMenu(ActionEvent actionEvent) {
        if(leftTableView.getSelectionModel().getSelectedItem() !=null){
            menuList.add(leftTableView.getItems().get(leftTableView.getSelectionModel().getSelectedIndex()));
            tableViewRight.setItems(FXCollections.observableArrayList(menuList));

            menu.addNewElement(leftTableView.getSelectionModel().getSelectedItem());

        }else MessageUtils.showMessage("Information", "Please select an item.");
    }
    @FXML
    private void deleteAlimentFromTheMenu(ActionEvent actionEvent) {
        if(tableViewRight.getSelectionModel().getSelectedItem() != null){
            menuList.remove(tableViewRight.getItems().get(tableViewRight.getSelectionModel().getSelectedIndex()));
            tableViewRight.setItems(FXCollections.observableArrayList(menuList));

            menu.getElements().remove(tableViewRight.getSelectionModel().getSelectedItem());

        }else
            MessageUtils.showMessage("Information", "Please select an item.");
    }
    @FXML
    private void saveNewMenu(ActionEvent actionEvent) {

        if(menuList.size() > 0 && menu != null){
            boolean result = MessageUtils.showConfirmation("Confirmation", "Do you want to save this menu?");
            if(result)
                FileUtils.storeMenu(menu);

        }else  MessageUtils.showError("Error", "No item has been selected for the menu");
    }
    //the following functions are called when you select any of the checkboxes for allergens
    @FXML
    private void milkChecked(){
        if(milkCheck.isSelected()){
            eggCheck.setSelected(false);
            nutsCheck.setSelected(false);
            glutenCheck.setSelected(false);
            List<Aliment> milkSafe = new ArrayList<>();
            for(MenuElement a: alimentList){
                if(a instanceof Aliment){
                    if(!((Aliment) a).hasMilk()){
                        milkSafe.add((Aliment)a);
                    }
                }
            }
            leftTableView.setItems(FXCollections.observableArrayList(milkSafe));
        }
        else{
            milkCheck.setSelected(false);
            leftTableView.setItems(FXCollections.observableArrayList(alimentList));
        }
    }

    @FXML
    private void eggChecked(){
        if(eggCheck.isSelected()){
            milkCheck.setSelected(false);
            nutsCheck.setSelected(false);
            glutenCheck.setSelected(false);
            List<Aliment> eggsSafe = new ArrayList<>();
            for(MenuElement a: alimentList){
                if(a instanceof Aliment){
                    if(!((Aliment) a).hasEgg()){
                        eggsSafe.add((Aliment)a);
                    }
                }
            }
            leftTableView.setItems(FXCollections.observableArrayList(eggsSafe));
        }
        else{
            eggCheck.setSelected(false);
            leftTableView.setItems(FXCollections.observableArrayList(alimentList));
        }
    }

    @FXML
    private void nutsChecked(){
        if(nutsCheck.isSelected()){
            eggCheck.setSelected(false);
            milkCheck.setSelected(false);
            glutenCheck.setSelected(false);
            List<Aliment> nutsSafe = new ArrayList<>();
            for(MenuElement a: alimentList){
                if(a instanceof Aliment){
                    if(!((Aliment) a).hasNuts()){
                        nutsSafe.add((Aliment)a);
                    }
                }
            }
            leftTableView.setItems(FXCollections.observableArrayList(nutsSafe));
        }
        else{
            nutsCheck.setSelected(false);
            leftTableView.setItems(FXCollections.observableArrayList(alimentList));
        }
    }

    @FXML
    private void glutenChecked(){
        if(glutenCheck.isSelected()){
            eggCheck.setSelected(false);
            nutsCheck.setSelected(false);
            milkCheck.setSelected(false);
            List<Aliment> glutenSafe = new ArrayList<>();
            for(MenuElement a: alimentList){
                if(a instanceof Aliment){
                    if(!((Aliment) a).hasGluten()){
                        glutenSafe.add((Aliment)a);
                    }
                }
            }
            leftTableView.setItems(FXCollections.observableArrayList(glutenSafe));
        }
        else{
            glutenCheck.setSelected(false);
            leftTableView.setItems(FXCollections.observableArrayList(alimentList));
        }
    }
}