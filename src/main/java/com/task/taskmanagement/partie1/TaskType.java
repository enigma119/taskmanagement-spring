package com.task.taskmanagement.partie1;

public enum TaskType {
    SITE_PREPARATION(5, 18),
    FOUNDATION(9, 30),
    STRUCTURE_CONSTRUCTION(10, 40),
    PARTITION_AND_ROOFING(7, 25),
    TECHNICAL_INSTALLATION(8, 30),
    FINISHING_AND_SECURITY(6, 22),
    CLEANING_AND_INSPECTION(4, 15);

    private final int basicPoints;
    private final int professionalPoints;

    TaskType(int basicPoints, int professionalPoints) {
        this.basicPoints = basicPoints;
        this.professionalPoints = professionalPoints;
    }

    public int getPoints(boolean isProfessional) {
        return isProfessional ? professionalPoints : basicPoints;
    }
}