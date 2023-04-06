package com.etr.calculator.tripcalculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Charges
{
private String startPoint;
private String endPoint;
private double distance;
private double price;
private String message;
}