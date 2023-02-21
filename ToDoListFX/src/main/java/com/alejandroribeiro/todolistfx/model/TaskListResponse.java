/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.model;

import java.util.List;

/**
 * Class to return a list of tasks
 */
public class TaskListResponse extends BaseResponse {

    private List<ToDoTask> result;

    public List<ToDoTask> getResult() {
        return result;
    }
}
