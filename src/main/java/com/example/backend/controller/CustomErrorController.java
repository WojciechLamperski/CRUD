package com.example.backend.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    // Return the custom error page
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 400) {
                logger.error("status 400 error request");
                return "400";  // Load 400.html from templates
            }
            if (statusCode == 404) {
                logger.error("status 404 error request");
                return "404";  // Load 404.html from templates
            }
            if (statusCode == 500) {
                logger.error("status 500 error request");
                return "500";  // Load 500.html from templates
            }
        }
        return "error";  // Default error page
    }
}