package com.alejandroribeiro.cardclient.cardclient;
//Author: Alejandro Ribeiro Carretero

import cardmodel.Card;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;
    @FXML
    private Button connectButton;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private ListView<String> cardsListGame = new ListView<>();
    @FXML
    private Label lblScore;
    @FXML
    private Label yourCardLbl;
    @FXML
    private Label lblMoreCards;

    private Socket service;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private float score = 0;
    private final ExecutorService executorService = Executors.newWorkStealingPool();
    private Boolean receiveCard = true;
    private Boolean finished = false;
    private Boolean fold = false;

    @FXML
    private void moreCards() {
        //This tells the server to draw another card and send it to us
        receiveCard = true;
    }

    @FXML
    private void foldAndFinish() {
        //We tell the server we are finished with our game
        fold = true;
    }

    @FXML
    private void connectToServer() {
        Task<Void> connect = new Task<>() {
            @Override
            protected Void call() throws Exception {
                addressField.setDisable(true); //Disable the connection fields
                portField.setDisable(true);
                connectButton.setDisable(true);
                //Establish the connection
                try {
                    service = new Socket(addressField.getText(), Integer.parseInt(portField.getText()));
                    oos = new ObjectOutputStream(service.getOutputStream());
                    ois = new ObjectInputStream(service.getInputStream());
                    if(service == null || oos == null){
                        return null;
                    }
                    Platform.runLater(() -> {
                        lblScore.setVisible(true);
                        yourCardLbl.setText("Waiting for other players...");
                    });
                    boolean beginGame = ois.readBoolean(); //We wait until the game begins.
                    while (!beginGame && !service.isClosed()) {
                        if(service == null || oos == null){
                            return null;
                        }
                        beginGame = ois.readBoolean();
                    }
                    while (!finished && !service.isClosed()) {
                        if(service == null || oos == null){
                            return null;
                        }
                        //Play the game until we lose or fold
                        if (fold) {
                            finished = true;
                        }
                        if (receiveCard && !finished) {
                            oos.writeInt(receiveCard.compareTo(true));
                            //We send a binary
                            // Int value to interpret as a Boolean in order to not send multiple Booleans
                            oos.flush();
                            Card card = (Card) ois.readObject();
                            score += card.GetNumericValue();
                            String currentCard = card.toString();
                            //We receive the card from the server, add its score, and store its name.
                            Platform.runLater(() -> {
                                //Update the GUI with the current game data
                                yesButton.setVisible(true);
                                noButton.setVisible(true);
                                lblMoreCards.setVisible(true);
                                cardsListGame.getItems().add(currentCard);
                                yourCardLbl.setText("YOUR CARD IS: " + currentCard);
                                lblScore.setText("YOUR SCORE IS: " + score);
                            });
                            receiveCard = false; //Set receiveCard to false
                            // again so the server doesn't loop through the entire deck
                        }
                        if (score >= 7.5 && !finished) {
                            finished = true;
                        }
                        oos.writeFloat(score); //Send the score over to the server
                        oos.flush();
                        oos.writeBoolean(finished); //Tell the server whether we are finished or not
                        oos.flush();
                    }
                    if (service.isClosed()) return null;
                    Platform.runLater(() -> {
                        //Hide and disable the yes or no buttons and the more cards label
                        lblMoreCards.setVisible(false);
                        yesButton.visibleProperty().setValue(false);
                        yesButton.setDisable(true);
                        noButton.visibleProperty().setValue(false);
                        noButton.setDisable(true);
                    });
                    boolean gameOver = ois.readBoolean();
                    if (score < 7.5) {
                        Platform.runLater(() -> lblScore.setText("Waiting for the other results."));
                    } else if (score == 7.5) {
                        Platform.runLater(() -> {
                            lblScore.setText("YOU WON WITH " + score + " POINTS");
                            lblScore.setStyle("-fx-text-fill: #332940; " +
                                    "-fx-background-color:#00BFBF ;-fx-border-color: #bb86fc; " +
                                    "-fx-border-radius: 9; -fx-background-radius: 8;" +
                                    "-fx-font-weight: bold;");
                        });
                    } else {
                        Platform.runLater(() -> {
                            lblScore.setText("YOU LOST WITH " + score + " POINTS");
                            lblScore.setStyle("-fx-text-fill: #332940; " +
                                    "-fx-background-color: #ff4040 ;-fx-border-color: #bb86fc; " +
                                    "-fx-border-radius: 9; -fx-background-radius: 8;" +
                                    " -fx-font-weight: bold;");
                        });
                    }
                    while (!gameOver) {
                        if(service == null || oos == null){
                            return null;
                        }
                        gameOver = ois.readBoolean(); //Wait for the server to tell us the game is over
                    }
                    boolean result = ois.readBoolean();
                    //We check if our score is equal to the maxScore
                    String winOrLose;
                    if (result) {
                        winOrLose = "YOU WON WITH " + score + " POINTS";
                        Platform.runLater(()->lblScore.setStyle("-fx-text-fill: #332940; " +
                                "-fx-background-color: #00BFBF ;-fx-border-color: #bb86fc; " +
                                "-fx-border-radius: 9; -fx-background-radius: 8; " +
                                "-fx-font-weight: bold;"));
                    } else {
                        winOrLose = "YOU LOST WITH " + score + " POINTS";
                        Platform.runLater(()->lblScore.setStyle("-fx-text-fill: #332940; " +
                                "-fx-background-color: #ff4040 ;-fx-border-color: #bb86fc; " +
                                "-fx-border-radius: 9; -fx-background-radius: 8;" +
                                "-fx-font-weight: bold;"));
                    }
                    Platform.runLater(() -> lblScore.setText(winOrLose));
                    return null;
                }
                catch (IOException e){
                    Platform.runLater(()->{
                    addressField.setDisable(false);
                    yesButton.setDisable(true);
                    noButton.setDisable(true);
                    lblMoreCards.setVisible(false);//Enable the connection fields
                    portField.setDisable(false);
                    connectButton.setDisable(false);
                    lblScore.setVisible(false);
                    yourCardLbl.setText("Connection error");});
                    return null;
                }
                finally {
                    try {
                        if(oos != null) {
                            oos.close();
                        }
                    } catch (IOException ignored) {}
                    try {
                        if (ois != null)
                            ois.close();
                    } catch (IOException ignored) {}
                    try {
                        if (service != null)
                            service.close();
                    } catch (IOException ignored) {}
                    Thread.sleep(3000);
                    Platform.exit();
                }
            }
        };
        executorService.execute(connect);//Run the task
    }
}