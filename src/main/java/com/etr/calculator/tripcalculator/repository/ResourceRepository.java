package com.etr.calculator.tripcalculator.repository;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

public interface ResourceRepository {
    JsonNode getRepository() throws IOException;
    List<String> listOfLocations() throws IOException;
}
