package com.terminius.assignment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by muhammad.junaid on 9/3/2021
 */

@Controller
public class SwaggerUIController {

    private String swaggerUiPath = "/terminus/swagger-ui/index.html?url=/terminus/v3/api-docs";

    @GetMapping("/swagger")
    public void swaggerUI(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", swaggerUiPath);
        httpServletResponse.setStatus(HttpStatus.FOUND.value());
    }
}
