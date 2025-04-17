package com.task.taskmanagement.security;

import com.task.taskmanagement.model.User;
import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static User getCurrentUser(HttpServletRequest request) {
        return (User) request.getAttribute("user");
    }
} 