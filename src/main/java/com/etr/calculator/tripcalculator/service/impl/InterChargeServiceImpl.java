package com.etr.calculator.tripcalculator.service.impl;

import com.etr.calculator.tripcalculator.model.Charges;
import com.etr.calculator.tripcalculator.repository.ResourceRepository;
import com.etr.calculator.tripcalculator.service.InterChargeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Service
public class InterChargeServiceImpl implements InterChargeService {

    private ResourceRepository repository;
    public InterChargeServiceImpl(ResourceRepository repository) {
        this.repository = repository;
    }

    double distance = 0;
    boolean startMeasuringDistance = false;

    @Override
    public Charges calculateCharges(String startPoint, String endPoint) throws IOException {
        System.out.println("calculateCharges" +startPoint +endPoint);
        Charges charges = new Charges();
        if( !checkIsLocationValid(startPoint) ||
                !checkIsLocationValid(endPoint)){
            charges.setMessage("Invalid Location");
            return charges;
        }

        JsonNode repositoryAsJson = repository.getRepository();

        distance = 0;
        distanceTravelled(repositoryAsJson, startPoint, endPoint);


        charges.setDistance(distance);
        charges.setPrice(computeTotalCost(distance));
        charges.setStartPoint(startPoint);
        charges.setEndPoint(endPoint);

        return charges;
    }

    private boolean checkIsLocationValid(String locationName) throws IOException {

        return repository.listOfLocations().contains(locationName);
    }

    private void distanceTravelled(JsonNode jsonNode, String startPoint, String endPoint){

        if(jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();

                if(entry.getValue().asText().equals(startPoint) && startMeasuringDistance == false){
                    startMeasuringDistance = true;
                }

                if(startMeasuringDistance == true && entry.getKey() == "distance"){
                    distance = Double.sum(distance, entry.getValue().asDouble());
                }
                if(entry.getValue().asText().equals(endPoint)){
                    startMeasuringDistance = false;
                    break;
                }
                distanceTravelled(entry.getValue(), startPoint, endPoint);
            }
        }else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;

            if(!arrayNode.isEmpty()){
                distanceTravelled(arrayNode.get(0), startPoint,endPoint);
            }
        }
    }


    private double computeTotalCost ( double distance){

        return distance * 0.25;
    }
}
