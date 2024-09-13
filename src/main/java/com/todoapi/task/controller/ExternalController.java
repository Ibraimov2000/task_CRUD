package com.todoapi.task.controller;

import com.todoapi.task.service.ExternalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/external")
public class ExternalController {

    private final ExternalService externalService;

    public ExternalController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @GetMapping("/objects")
    public String fetchDataFromExtApi() {
        return externalService.fetchDataFromExternalApi();
    }
}
