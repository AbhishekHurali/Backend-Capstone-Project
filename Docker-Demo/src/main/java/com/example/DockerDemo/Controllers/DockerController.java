package com.example.DockerDemo.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DockerController {

    @GetMapping("{name}")
    public String print(@PathVariable String name){
        System.out.println("Hello "+name);
        return "Hello "+name;
    }
}
