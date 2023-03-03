package com.src.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
public class IndexController {
    private static Logger Log = Logger.getLogger(String.valueOf(IndexController.class));

    @RequestMapping("/index")
    public String index() {
        Log.info("Request received at index resource");
        return "Welcome! this is the sample micro service with database connection";
    }
}
