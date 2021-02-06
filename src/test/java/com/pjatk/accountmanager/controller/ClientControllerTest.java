package com.pjatk.accountmanager.controller;

import com.pjatk.accountmanager.model.Client;
import com.pjatk.accountmanager.service.ClientService;
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
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Test
    void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/client"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotFindClient() throws Exception {
        mockMvc.perform(get("/client/100"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindClient() throws Exception {
        when(clientService.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(new Client(1L, "Adam", "Nowak", 83072778132L, 5000, 0, 0, 0)));
        mockMvc.perform(get("/client/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"Adam\",\"surname\":\"Nowak\",\"pesel\":83072778132,\"salary\":5000,\"accountSum\":0,\"toTransfer\":0,\"loan\":0}")));
    }
}
