package cardserver;
//Author: Alejandro Ribeiro Carretero
import cardmodel.Card;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

//The ServerThread class will keep the game thread running with each separate client connection
public class ServerThread implements Runnable {
    Socket service;

    public ServerThread(Socket s)
    {
        service = s;
    }

    @Override
    public void run()
    {
        int gameIndex = ServerMain.getGameIndex(); //We need to check our position in the list
        ServerMain.setGameIndex(ServerMain.getGameIndex()+1); //Increase the global index for other threads to access
        ObjectOutputStream socketOut = null; //IO streams
        ObjectInputStream socketIn = null;
        float score = 0; //Initialize the score
        try
        {
            socketOut = new ObjectOutputStream(service.getOutputStream());
            socketIn = new ObjectInputStream(service.getInputStream());
            boolean finished = false; //Keep the game going until conditions are met
            boolean cardSent = false; //When this is false, we draw a card and send it to the client
            while (!ServerMain.gameStarted){
                socketOut.writeBoolean(ServerMain.gameStarted); //We tell the client to wait
                socketOut.flush();
            }
            socketOut.writeBoolean(ServerMain.gameStarted); //We update the client telling them to start the game
            socketOut.flush();
            Card card;
            // … Communication with client
            do{
                synchronized (this) {
                    if (ServerMain.gameOver) {
                        socketOut.close();
                        socketIn.close();
                        service.close();
                        break;
                    }
                }
                if (socketIn.readInt() == 0) {
                    cardSent = false;
                }
                if (!cardSent) { //Draw a card, send it to the client, and read the score
                    card = ServerMain.deck.NextCard();
                    socketOut.writeObject(card);
                    socketOut.flush();
                    score = socketIn.readFloat();
                    cardSent = true;
                }
                if (socketIn.readBoolean() == true || score >= 7.5) {
                    //We receive a boolean that tells us if the game is over
                    //Automatically finishes the game if the score is equal or greater than 7.5
                    finished = true;
                }
            } while (!finished);
            //We must open a synchronized block in order to get the proper max score
            synchronized (this) {
                if (!(score > 7.5) && score > ServerMain.getMaxScore()) {
                    //Check if our score is the best score through all games and then update it
                    ServerMain.setMaxScore(score);
                }
                ServerMain.finishedGames.set(gameIndex, true); //Mark our game as finished
            }
            while (!ServerMain.gameOver){
                //Tell the client the game is still in progress, so it keeps the connection open
                socketOut.writeBoolean(false);
                socketOut.flush();
            }
            //Tell the client the game is over
            socketOut.writeBoolean(true);
            socketOut.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ServerMain.gameOver = true;
            socketOut = null;
            socketIn = null;
            service = null;
        }
        finally {
            synchronized (this) {
                try {
                    //Check if the client is among the winners with the highest score
                    boolean winner;
                    winner = ServerMain.getMaxScore() == score;
                    if (service != null && socketOut != null) {
                        socketOut.writeBoolean(winner);
                        socketOut.flush();
                    }
                }
                catch (IOException ignored){
                }
            }
            //Finally, close the connection
            try {
                if(socketOut != null) {
                    socketOut.close();
                }
            } catch (IOException ignored) {}
            try {
                if (socketIn != null)
                    socketIn.close();
            } catch (IOException ignored) {}
            try {
                if (service != null)
                    service.close();
            } catch (IOException ignored) {}
        }
    }
}
