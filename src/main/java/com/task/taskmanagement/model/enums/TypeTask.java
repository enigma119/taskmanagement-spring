package com.task.taskmanagement.model.enums;

public enum TypeTask {
    BASIC(10),
    MEDIUM(20),
    PROFESSIONAL(50);

    private final int points;

    TypeTask(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

}
