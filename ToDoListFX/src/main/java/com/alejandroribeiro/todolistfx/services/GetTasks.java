/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.services;


import com.alejandroribeiro.todolistfx.model.*;
import com.alejandroribeiro.todolistfx.utils.NodeServer;
import com.alejandroribeiro.todolistfx.utils.ServiceUtils;
import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Class to get tasks that match the given criteria
 */

public class GetTasks extends Service<TaskListResponse> {

    private String filter;

    /**
     * Constructor with parameters
     * @param type
     * @param priority
     * @param done
     * @param difficulty
     */
    public GetTasks(String type, String priority, String done, String difficulty){
        filter = "";

        if (type != null && !type.equals("Show all")) {
            filter = "/type/" + type.toLowerCase();
        }
        else if (priority != null && !priority.equals("Show all")) {
            filter = "/priority/" + priority;
        }
        else if (done != null && !done.equals("Show all")) {
            filter = "/done/" + (done.equals("Done"));
        }
        else if (difficulty != null && !difficulty.equals("Show all")) {
            filter = "/difficulty/" + difficulty;
        }
    }
    @Override
    protected Task<TaskListResponse> createTask() {
        return new Task<TaskListResponse>() {
            @Override
            protected TaskListResponse call() throws Exception {
                String res = ServiceUtils.getResponse(NodeServer.getServer()+"/tasks" + filter
                        , null, "GET");
                Gson gson = new Gson();
                return gson.fromJson(res, TaskListResponse.class);
            }
        };
    }
}
