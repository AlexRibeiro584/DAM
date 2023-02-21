package cardserver;
//Author: Alejandro Ribeiro Carretero

import cardmodel.Deck;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerMain {
    public static Deck deck; //Thread-safe deck.

    //Volatile variables for thread-safe management.
    private static volatile float maxScore = 0;
    static volatile Boolean gameOver = false;
    static volatile Boolean gameStarted = false;
    private static volatile int gameIndex = 0;
    public static volatile List<Boolean> finishedGames = new LinkedList<>(); //We store how many games are finished
    public static void main(String[] args) {
        int port;
        int maxPlayers;
        int connections = 0;
        try {
            //Check if there are arguments being passed
            port = Integer.parseInt(args[0]);
            maxPlayers = Integer.parseInt(args[1]);
        }
        catch (Exception e) {
            //Otherwise, set the default values
            port = 5000;
            maxPlayers = 3;
        }
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(maxPlayers);
        //Define an executor with a thread pool equal to the maximum amount of players
        deck = new Deck(); //Instantiate a new Deck
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            /*We try to initialize the server with the specified port.
             * If the port isn't valid, it will not start and the application will end.*/
            System.out.println("Server running...");
            System.out.println("Waiting for "+maxPlayers+ " players to begin...");
            try {
                while (connections < maxPlayers) {
                    //Accept connections and create a new Thread with each one.
                    Socket socket = serverSocket.accept();
                    connections++;
                    finishedGames.add(false); //Add a game to the list, so we can access its size later.
                    ServerThread serverThread = new ServerThread(socket);
                    executor.execute(serverThread); /*We run the serverThread as a runnable
                    in order to reuse it later. This is meant for the optional part which,
                    if you are reading this comment, I haven't done yet.*/
                    System.out.println("Player connected.");
                    if (connections == maxPlayers) {
                        gameStarted = true; /*Set the thread-safe global variable to
                         true so the threads can start their games*/
                        System.out.println("Game started!");
                    }
                    else {
                        System.out.println("Waiting for " + (maxPlayers - connections) + " players to begin...");
                    }
                }
                while (!gameOver) {
                    gameOver = !finishedGames.contains(false);
                    //If any of the booleans in this list is false, at least 1 game is still running
                }
            }
            catch (IOException e){
                System.out.println("Connection lost.");
                //If someone disconnects abruptly
            }
            finally {
                executor.shutdownNow();
            }
        }
        catch (IOException e) {
            //Unavailable port
            System.out.println("Server failed to initialize. " +
                    "Please check that the selected port is available.");
        }
        finally {
            //Game over
            System.out.println("Game finished.");
            Thread.currentThread().interrupt(); //Shut the server down
        }
    }

    public static float getMaxScore() {
        return maxScore;
    }

    public static void setMaxScore(float maxScore) {
        ServerMain.maxScore = maxScore;
    }

    public static int getGameIndex() {
        return gameIndex;
    }

    public static void setGameIndex(int gameIndex) {
        ServerMain.gameIndex = gameIndex;
    }
}