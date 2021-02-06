package com.pjatk.accountmanager.controller;

import com.pjatk.accountmanager.model.Bank;
import com.pjatk.accountmanager.model.Client;
import com.pjatk.accountmanager.service.BankService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/bank"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotFindBank() throws Exception {
        mockMvc.perform(get("/bank/Mbank"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
