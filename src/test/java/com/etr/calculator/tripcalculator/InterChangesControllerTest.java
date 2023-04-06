package com.etr.calculator.tripcalculator;

import com.etr.calculator.tripcalculator.controller.InterChangesController;
import com.etr.calculator.tripcalculator.model.Charges;
import com.etr.calculator.tripcalculator.service.InterChargeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.CoreMatchers.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InterChangesController.class)
public class InterChangesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterChargeService interChargeService;

    @Test
    public void given_startPoint_endPoint_thenReturnCost() throws Exception {

        String startPoint = "QEW";
        String endPoint = "Dundas Street";

        Charges charges = new Charges();
        charges.setStartPoint(startPoint);
        charges.setEndPoint(endPoint);
        charges.setPrice(9.75);

        given(interChargeService.calculateCharges(startPoint,endPoint)).willReturn(charges);

        ResultActions response = mockMvc.perform(get("/api/calculate")
                .param("startPoint", startPoint)
                .param("endPoint",endPoint));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.price",
                        is(charges.getPrice())));
    }

    @Test
    public void given_wrong_startPoint_endPoint_thenReturn_InvalidLocation() throws Exception {

        String startPoint = "MARS";
        String endPoint = "JUPITER";

        Charges charges = new Charges();
        charges.setMessage("Invalid Location");

        given(interChargeService.calculateCharges(startPoint,endPoint)).willReturn(charges);

        ResultActions response = mockMvc.perform(get("/api/calculate")
                .param("startPoint", startPoint)
                .param("endPoint",endPoint));

        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.message",
                        is(charges.getMessage())));
    }
}
