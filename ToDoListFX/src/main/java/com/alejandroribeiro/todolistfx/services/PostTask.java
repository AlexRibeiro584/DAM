package com.alejandroribeiro.todolistfx.services;

import com.alejandroribeiro.todolistfx.model.*;
import com.alejandroribeiro.todolistfx.utils.*;
import com.google.gson.Gson;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class PostTask extends Service<ToDoTaskResponse> {
    private final ToDoTask task;

    public PostTask(ToDoTask task) {
        this.task = task;
    }
    @Override
    protected Task<ToDoTaskResponse> createTask() {
        return new javafx.concurrent.Task<>() {
            @Override
            protected ToDoTaskResponse call(){
                Gson gson = new Gson();
                String res = ServiceUtils.getResponse(
                        NodeServer.getServer() +"/tasks", gson.toJson(task), "POST"
                );

                return gson.fromJson(res, ToDoTaskResponse.class);
            }
        };
    }
}
