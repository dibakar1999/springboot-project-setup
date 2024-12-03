package com.setupproject.setup.controller;

import com.setupproject.setup.common.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApplicationResource {

    @GetMapping("ping")
    public Response<Object> ping(HttpServletRequest request) {
        try {
            return new Response<>("00", "Application is up and running.....");
        } catch (Exception e) {
            return new Response<>("01", e.getMessage());
        }
    }
}
