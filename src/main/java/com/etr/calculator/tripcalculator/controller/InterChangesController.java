package com.etr.calculator.tripcalculator.controller;

import com.etr.calculator.tripcalculator.model.Charges;
import com.etr.calculator.tripcalculator.service.InterChargeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class InterChangesController {

    private InterChargeService interChargeService;

    public InterChangesController(InterChargeService interChargeService) {
        this.interChargeService = interChargeService;
    }

    @GetMapping(value = "calculate")
    public Charges calculateCharges (@RequestParam("startPoint") String startPoint, @RequestParam("endPoint") String endPoint) throws IOException {
        System.out.println("Calculate charges: startPoint {} "+ startPoint +" , endPoint "+endPoint);
        return interChargeService.calculateCharges(startPoint, endPoint);
    }

}
