package com.example.evently.swagger;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {
    @GetMapping("/swagger/{name}/{version}")
    public String homePage(@PathVariable("name") String name, @PathVariable("version") String version, Model model) {
        model.addAttribute("filePath", "/swagger/" + name + '/' + version + "/swagger.yaml");
        return "redoc";
    }
}
