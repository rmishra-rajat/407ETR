package com.etr.calculator.tripcalculator.service;

import com.etr.calculator.tripcalculator.model.Charges;

import java.io.IOException;

public interface InterChargeService {

    Charges calculateCharges(String startPoint, String endPoint) throws IOException;
}
