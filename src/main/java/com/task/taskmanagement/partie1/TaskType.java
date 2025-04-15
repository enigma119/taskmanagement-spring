package com.task.taskmanagement.partie1;

public enum TaskType {
    BASIC(10),
    MEDIUM(20),
    PROFESSIONEL(50);

    private final int points;

    TaskType(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}