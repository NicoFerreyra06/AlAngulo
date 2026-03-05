package com.nicoferreyra.alangulo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/me")
    public Map<String, String> viewUser(@AuthenticationPrincipal OAuth2User principal) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", principal.getAttribute("name"));
        map.put("email", principal.getAttribute("email"));
        return map;
    }
}
