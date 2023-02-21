/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.model;

/**
 * Class that returns a ToDoTask object
 */

public class ToDoTaskResponse extends BaseResponse {
    ToDoTask result;

    public ToDoTask getResult() {
        return result;
    }
}
