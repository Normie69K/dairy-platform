package com.dairy.backend.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping("/me")
    public String me() {
        return "authenticated";
    }
}
