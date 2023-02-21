/**
 * @author Alejandro Ribeiro Carretero
 * version: 1.0
 */
package com.alejandroribeiro.todolistfx.model;


import com.google.gson.annotations.SerializedName;

/**
 * Class that stores the ToDoTask Entity
 */
public class ToDoTask {

    @SerializedName("_id")
    private String id;
    private String description;
    private String type;
    private int priority;
    private boolean done;
    private int difficulty;

    public ToDoTask(String id, String description, String type, int priority, boolean done, int difficulty) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.done = done;
        this.difficulty = difficulty;
    }

    public ToDoTask(String description, String type, int priority, boolean done, int difficulty) {
        this.description = description;
        this.type = type;
        this.priority = priority;
        this.done = done;
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Method to represent the entity and all its data in a better String format
     * @return
     */
    public String refinedToString() {
        return  "\nId: '" + id + '\'' +
                "\nDescription: '" + description + '\'' +
                "\nType: '" + type + '\'' +
                "\nPriority: " + priority +
                "\nDone: " + (done ? "Done" : "Not done")  +
                "\nDifficulty: " + difficulty;
    }

    @Override
    public String toString() {
        return description;
    }
}
