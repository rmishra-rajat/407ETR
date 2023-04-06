package com.etr.calculator.tripcalculator.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class InterChangesResourceRepository implements ResourceRepository {

    @Autowired
    ResourceLoader resourceLoader;

    List<String> locationList = new ArrayList<>();

    @Override
    public JsonNode getRepository() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:interchanges.json");
        File file = resource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(file);
    }

    @Override
    public List<String> listOfLocations() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:interchanges.json");
        File file = resource.getFile();
        ObjectMapper objectMapper = new ObjectMapper();
        buildLocationList(objectMapper.readTree(file));
        return locationList;
    }

    private void buildLocationList (JsonNode jsonNode) throws IOException {
        if(jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                if(entry.getKey() == "name"){
                    System.out.println("Location name in List:"+entry.getValue());
                    locationList.add(entry.getValue().asText());
                }
                buildLocationList(entry.getValue());
            }
        }
    }
}
