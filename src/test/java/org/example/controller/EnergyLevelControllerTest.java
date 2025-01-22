package org.example.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.AbstractIntegrationTest;
import org.example.dto.EnergyLevelDto;
import org.example.dto.QuoteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class EnergyLevelControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getEnergyLevels() throws Exception {
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createQuoteDto(BigDecimal.valueOf(101.9), BigDecimal.valueOf(100.20), "RU000A0JX0J2"))))
                .andExpect(status().isOk());
        Thread.sleep(1000);
         MvcResult result = mockMvc.perform(get("/elvl")
                        .contentType("application/json"))
                 .andExpect(status().isOk())
                 .andReturn();

        List<EnergyLevelDto> actualRes = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<EnergyLevelDto>>() {});

        assertEquals(0, actualRes.get(0).getValue().compareTo(BigDecimal.valueOf(100.2)));
        assertEquals("RU000A0JX0J2", actualRes.get(0).getIsin());
    }

    @Test
    void getEnergyLevelByIsin() throws Exception {
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createQuoteDto(BigDecimal.valueOf(101.9), BigDecimal.valueOf(100.2), "RU000A0JX0J2"))))
                .andExpect(status().isOk());
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createQuoteDto(BigDecimal.valueOf(101.9), BigDecimal.valueOf(100.5), "RU000A0JX0J2"))))
                .andExpect(status().isOk());
        Thread.sleep(10000);
        MvcResult result = mockMvc.perform(get("/elvl/RU000A0JX0J2")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        EnergyLevelDto actualRes = objectMapper.readValue(result.getResponse().getContentAsString(), EnergyLevelDto.class);

        assertEquals(0, actualRes.getValue().compareTo(BigDecimal.valueOf(100.5)));
        assertEquals("RU000A0JX0J2", actualRes.getIsin());
    }

    private QuoteDto createQuoteDto(BigDecimal ask, BigDecimal bid, String isin) {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setAsk(ask);
        quoteDto.setBid(bid);
        quoteDto.setIsin(isin);
        return quoteDto;
    }
}