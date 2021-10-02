package com.example.getinline.controller.api;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
@RequestMapping("/api")
public class APIEventController {

    @GetMapping(path = "/custom")
    @ResponseBody
    public String get() {
        return "custom controller get";
    }
}
