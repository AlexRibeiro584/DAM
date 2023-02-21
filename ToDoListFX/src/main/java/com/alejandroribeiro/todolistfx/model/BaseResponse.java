/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.model;

/**
 * Parent class for the returned objects through services.
 */

public abstract class BaseResponse {
    String error;
    Boolean ok;

    public String getError() {
        return error;
    }

    public Boolean isOk() {
        return ok;
    }
}
