package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.QuoteDto;
import org.example.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = QuoteController.class)
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private QuoteService quoteService;

    @Test
    void createQuote_success() throws Exception {
        String json = objectMapper.writeValueAsString(createQuoteDto(BigDecimal.valueOf(101.9), BigDecimal.valueOf(100.2), "RU000A0JX0J2"));
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());

    }

    @Test
    void createQuote_invalidIsin() throws Exception {
        String json = objectMapper.writeValueAsString(createQuoteDto(BigDecimal.valueOf(101.9), BigDecimal.valueOf(100.2), "RU"));
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createQuote_invalidBid() throws Exception {
        String json = objectMapper.writeValueAsString(createQuoteDto(BigDecimal.valueOf(101.9), BigDecimal.valueOf(110.2), "RU000A0JX0J2"));
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createQuote_askIsNull() throws Exception {
        String json = objectMapper.writeValueAsString(createQuoteDto(null, BigDecimal.valueOf(110.2), "RU000A0JX0J2"));
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());

    }

    @Test
    void createQuote_bidIsNull() throws Exception {
        String json = objectMapper.writeValueAsString(createQuoteDto(BigDecimal.valueOf(110.2), null, "RU000A0JX0J2"));
        mockMvc.perform(post("/quote")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk());

    }

    private QuoteDto createQuoteDto(BigDecimal ask, BigDecimal bid, String isin) {
        QuoteDto quoteDto = new QuoteDto();
        quoteDto.setAsk(ask);
        quoteDto.setBid(bid);
        quoteDto.setIsin(isin);
        return quoteDto;
    }
}