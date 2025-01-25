package com.example.backend.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    // Return the custom error page (404.html)
    @RequestMapping("/error")
    public String handleError() {
        // TODO: add Thymeleaf, so this can work
        // This will return the 404.html template
        return "error";  // This is the name of the 404 page in src/main/resources/templates
    }
}