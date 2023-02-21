package com.alejandroribeiro.todolistfx.services;

import com.alejandroribeiro.todolistfx.model.ToDoTask;
import com.alejandroribeiro.todolistfx.model.ToDoTaskResponse;
import com.alejandroribeiro.todolistfx.utils.NodeServer;
import com.alejandroribeiro.todolistfx.utils.ServiceUtils;
import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PutTask extends Service<ToDoTaskResponse> {
    private final ToDoTask task;

    public PutTask(ToDoTask task) {
        this.task = task;
    }
    @Override
    protected Task<ToDoTaskResponse> createTask() {
        return new javafx.concurrent.Task<>() {
            @Override
            protected ToDoTaskResponse call(){
                Gson gson = new Gson();
                String res = ServiceUtils.getResponse(
                        NodeServer.getServer() +"/tasks/"+task.getId(), gson.toJson(task), "PUT"
                );

                return gson.fromJson(res, ToDoTaskResponse.class);
            }
        };
    }
}
