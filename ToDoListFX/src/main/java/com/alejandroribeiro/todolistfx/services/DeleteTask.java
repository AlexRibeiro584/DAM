/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.services;

import com.alejandroribeiro.todolistfx.model.TaskListResponse;
import com.alejandroribeiro.todolistfx.model.ToDoTaskResponse;
import com.alejandroribeiro.todolistfx.utils.NodeServer;
import com.alejandroribeiro.todolistfx.utils.ServiceUtils;
import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Class to handle the deleting service
 */

public class DeleteTask extends Service<ToDoTaskResponse> {

    private final String id;

    /**
     * Constructor with parameters
     * @param id
     */
    public DeleteTask(String id) {
        this.id = id;
    }

    @Override
    protected Task<ToDoTaskResponse> createTask() {
        return new Task<ToDoTaskResponse>() {
            @Override
            protected ToDoTaskResponse call() throws Exception {
                Gson gson = new Gson();
                String res = ServiceUtils.getResponse(NodeServer.getServer()+"/tasks/"+id, null, "DELETE");
                return gson.fromJson(res, ToDoTaskResponse.class);
            }
        };
    }
}