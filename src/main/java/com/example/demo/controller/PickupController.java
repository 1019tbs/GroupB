package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PickupController {

    @PostMapping("/pickup/start")
    public String startPickup() {

        return "adminOrder";
    }
}
