package com.example.backend.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    // Return the custom error page (404.html)
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 400) {
                return "400";  // Load 400.html from templates
            }
            if (statusCode == 404) {
                return "404";  // Load 404.html from templates
            }
            if (statusCode == 500) {
                return "500";  // Load 500.html from templates
            }
        }
        return "error";  // Default error page
    }
}